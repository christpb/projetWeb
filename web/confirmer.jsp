<%-- 
    Document   : confirmer
    Created on : 27 mai 2017, 16:11:36
    Author     : perle
--%>

<%@page import="miage.gestionnaires.GestionnaireMiage"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>  

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Confirmer la participation des étudiants</title>
    </head>
    <body> 
        <div class="marge">
            <c:choose>
                <c:when test="${not empty requestScope['listeDesEtudiants']}">
                    <h5 class="center titre">Confirmer la participation des étudiants pour la Miage de ${requestScope['miage']}</h5> 
                    </br></br>
                    <form class="col s12" action="ServletEnseignants" method="post">
                        <table border="10" class="centered responsive-table">  
                            <!-- La ligne de titre du tableau des comptes -->
                            <thead>
                                <tr>
                                    <th>Nom</th>
                                    <th>Prenom</th>
                                    <th>Confirmer</th>
                                </tr>
                            </thead>

                            <!-- Ici on affiche les lignes, une par utilisateur -->  
                            <!-- cette variable montre comment on peut utiliser JSTL et EL pour calculer -->  
                            <tbody> 
                                <c:forEach var="u" items="${requestScope['listeDesEtudiants']}">  
                                    <tr>  
                                        <td>${u.nom}</td>  
                                        <td>${u.prenom}</td>
                                        <td><input type="checkbox" name="check" id="${u.id}" value="${u.id}"/><label for="${u.id}"></label></td>
                                    </tr>  
                                </c:forEach>
                            </tbody>
                        </table>
                        </br></br>
                        <input type="hidden" name="action" value="validerEtudiant"/>  
                        <input class="btn waves-effect waves-light light-blue accent-3" type="submit" value="Confirmer" name="submit"/>
                    </form>
                </c:when>
                <c:otherwise>
                    <h5 class="center red-text">Aucun étudiant à confirmer pour l'instant pour la Miage de ${requestScope['miage']}</h5> 
                </c:otherwise>
            </c:choose>

        </div>
    </body>
</html>
