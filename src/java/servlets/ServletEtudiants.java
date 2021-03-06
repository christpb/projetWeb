/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/* 
 * To change this template, choose Tools | Templates 
 * and open the template in the editor. 
 */
package servlets;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import participants.gestionnaires.GestionnaireEtudiants;
import participants.modeles.Etudiant;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import miage.gestionnaires.GestionnaireMiage;
import miage.modeles.Miage;
import participants.modeles.Entreprise;
import participants.modeles.Participants;
import vote.gestionnaire.GestionnaireVotes;
import vote.modele.Vote;

// chemin christian : C:\Users\Christian\Desktop\projet_web_pres\projetWeb\web\resources
// chemin perle : C:\Users\perle\Desktop\M1 MIAGE\S2\Web\Projet_Web\web\resources
@WebServlet(name = "ServletUsers", urlPatterns = {"/ServletUsers"})
@MultipartConfig(location = "C:\\Users\\Christian\\Desktop\\projet_web_pres\\projetWeb\\web\\resources", fileSizeThreshold = 1024 * 1024, maxFileSize = 1024 * 1024 * 5, maxRequestSize = 1024 * 1024 * 5 * 5)
//FICHIER D'UPLOAD GENERE A LA RACINE DU PROJET GLASSFISH !
public class ServletEtudiants extends HttpServlet {

    @EJB
    private GestionnaireMiage gestionnaireMiage;
    @EJB
    private GestionnaireEtudiants gestionnaireEtudiants;
    @EJB
    private GestionnaireVotes gestionnaireVotes;
    // ici injection de code ! On n'initialise pas ! 
    private final static Logger LOGGER
            = Logger.getLogger(ServletEtudiants.class.getCanonicalName());

    private boolean co = false;

    public boolean isCo() {
        return co;
    }

    public void setCo(boolean co) {
        this.co = co;
    }
    HttpServletRequest request;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Pratique pour décider de l'action à faire  
        String action = request.getParameter("action");
        response.setContentType("text/html;charset=UTF-8");

        String forwardTo = "";
        String message = "";
        HttpSession session = request.getSession(false);
        //System.out.println("Action : " + action);

        if (action != null) {

            if (action.equals("participer")) {
                // verification des parametres places dans la requete
                boolean diplome = false;
                // questions générales
                String prenom = request.getParameter("prenom");
                String nom = request.getParameter("nom");
                String email = request.getParameter("email");
                String etat = request.getParameter("group1");
                //System.out.println("Etat : " + etat);
                String password = request.getParameter("password");

                //étudiant
                if (etat.equals("miagiste")) {
                    String naissance = request.getParameter("date_naiss");
                    String miage = request.getParameter("choix_miage");
//                    String photo = request.getParameter("fichier");
//                    System.out.println("Contenu photo : " + photo);
                    Part photo = request.getPart("fichier");
                    String nomPhoto = photo.getSubmittedFileName();
                    String dip = request.getParameter("group2");
                    //System.out.println("dip : " + dip);

                    if (dip.equals("Ydiplome")) {
                        diplome = true;
                    }

                    
                    //Etudiant e1 = gestionnaireEtudiants.creeEtudiant(nom, prenom, email, password, naissance, photo, diplome);
                    boolean b = gestionnaireEtudiants.checkMail(email);
                    //System.out.println("Servlet boolean : " + b);
                    if (b == true) {
                        Etudiant e1 = gestionnaireEtudiants.creeEtudiant(nom, prenom, email, password, naissance, miage, nomPhoto, diplome, false);
                        //System.out.println("Compte étudiant créé : " + nom + prenom + email + password + ", naissance " + naissance + nomPhoto + diplome);
                        saveFile(request, response);
                        session.setAttribute("badLog", false);
                        session.setAttribute("connexionEtudiant", true);
                        session.setAttribute("objEtudiant", e1.getId());
                        session.setAttribute("user", true);

                        forwardTo = "index-form.jsp?";
                        message = "Vous êtes maintenant connecté(e)";
                        request.setAttribute("message", message);
                    } else {
                        session.setAttribute("badLog", true);
                        Collection<Miage> liste = gestionnaireMiage.getAllMiage();
                        request.setAttribute("listeDesMiages", liste);
                        forwardTo = "participer-form.jsp?";
                        message = "L'adresse mail est déjà utilisée";
                        request.setAttribute("message", message);
                    }

                } else if (etat.equals("entreprise")) {
                    String fonction = request.getParameter("fonction");
                    String tel = request.getParameter("tel");
                    String nomE = request.getParameter("nomE");
                    String rueE = request.getParameter("rueE");
                    String cp = request.getParameter("cpE");
                    //System.out.println("Code postal : " + cp);
                    //int codePostal = Integer.parseInt(cp);
                    String ville = request.getParameter("villeE");
                    String secteur = request.getParameter("sectE");
                    //System.out.println(ent.toString());

                    Entreprise ent1 = gestionnaireEtudiants.creeEntreprise(nom, prenom, email, password, fonction, tel, nomE, rueE, cp, ville, secteur);
                    session.setAttribute("user", true);
                    session.setAttribute("etuValide", true);

                    forwardTo = "index-form.jsp?";
                    message = "Vous êtes maintenant connecté(e)";
                    request.setAttribute("message", message);
                }

            } else if (action.equals("validerEtudiant")) {
                String[] valeurs = request.getParameterValues("check");

                for (String str : valeurs) {
                    int idEtu = Integer.parseInt(str.trim());
                    //System.out.println(idEtu);
                    gestionnaireEtudiants.valideEtudiant(idEtu);
                }

                forwardTo = "index-form.jsp?";
                message = "Etudiant(s) validé(s)";
                request.setAttribute("message", message);
            } else if (action.equals("valider_vote_shirt")) {
                Collection<Miage> liste = gestionnaireMiage.getAllMiage();
                request.setAttribute("listeDesMiages", liste);
                String[] votesShirt = request.getParameterValues("vote_shirt");
                int idEtudiant = (int) session.getAttribute("objEtudiant");
                //System.out.println("idEtudiant : " + idEtudiant);
                //System.out.println("votes : " + votesShirt);
                for (String str : votesShirt) {
                    int idMiage = Integer.parseInt(str.trim());
                    gestionnaireVotes.ajouterVotes(idEtudiant, idMiage, false);
                }
                session.setAttribute("grise_shirt", true);
                forwardTo = "vote-form.jsp?action=valider_vote_shirt";
                request.setAttribute("Vos votes ont été enregistrés", message);
            } else if (action.equals("valider_vote_video")) {
                Collection<Miage> liste = gestionnaireMiage.getAllMiage();
                request.setAttribute("listeDesMiages", liste);
                String[] votesVideo = request.getParameterValues("vote_video");
                int idEtudiant = (int) session.getAttribute("objEtudiant");
                //System.out.println("idEtudiant : " + idEtudiant);
                //System.out.println("votes : " + votesVideo);
                for (String str : votesVideo) {
                    int idMiage = Integer.parseInt(str.trim());
                    gestionnaireVotes.ajouterVotes(idEtudiant, idMiage, true);
                }
                session.setAttribute("grise_video", true);
                forwardTo = "vote-form.jsp?action=valider_vote_video";
                request.setAttribute("Vos votes ont été enregistrés", message);
            }
            //getServletContext().getRequestDispatcher("/index-form.jsp").forward(  
            //    request, response);
        }

        //System.out.println("forward : " + forwardTo);
        RequestDispatcher dp = request.getRequestDispatcher(forwardTo);
        dp.forward(request, response);
        // Après un forward, plus rien ne peut être exécuté après !  
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        processRequest(request, response);
    }

    private void saveFile(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");

        PrintWriter out = response.getWriter();
        Collection<Part> parts = request.getParts();
        out.write("<h2> Total parts : " + parts.size() + "</h2>");

        for (Part part : parts) {
            //System.out.println("Name de part : " + part.getName());
            if (part.getName().equals("fichier")) {
                //System.out.println("Fichier trouvé");

                //printPart(part, out);
                String str = "D:\\glassfish-4.1\\glassfish\\domains\\domain1\\generated\\jsp\\Projet_web\\Photo_\\" + request.getParameter("nom");
                File dir = new File(str);
                if (!dir.exists()) {
                    //System.out.println("creating directory: " + dir.getName());
                    boolean result = false;

                    try {
                        dir.mkdir();
                        result = true;
                    } catch (SecurityException se) {
                        //handle it
                        //System.out.println("Aborting..." + se);
                        this.doPost(request, response);
                    }
                    if (result) {
                        //System.out.println("DIR created");
                        //System.out.println("Chemin : " + dir.getAbsolutePath());
                    }
                } else {
                    //System.out.println("le file " + dir + " existe déjà");
                }
                part.write(request.getParameter("nomFichier"));
            }
        }

    }
//
//    private void printPart(Part part, PrintWriter pw) {
//        StringBuffer sb = new StringBuffer();
//        sb.append("<p>");
//        sb.append("Name : " + part.getName());
//        sb.append("<br>");
//        sb.append("Content Type : " + part.getContentType());
//        sb.append("<br>");
//        sb.append("Size : " + part.getSize());
//        sb.append("<br>");
//        for (String header : part.getHeaderNames()) {
//            sb.append(header + " : " + part.getHeader(header));
//            sb.append("<br>");
//        }
//        sb.append("</p>");
//        pw.write(sb.toString());
//    }
}
