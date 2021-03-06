/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miage.gestionnaires;

import java.util.Collection;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import miage.modeles.Miage;

/**
 *
 * @author perle
 */
@Stateless
public class GestionnaireMiage {

    @PersistenceContext
    private EntityManager em;

    /*public void listeMiage() {
        newMiage("Aix-Marseille");
        newMiage("Amiens");
        newMiage("Bordeaux");
        newMiage("Grenoble");
        newMiage("Guadeloupe");
        newMiage("Lille");
        newMiage("Mulhouse");
        newMiage("Nancy");
        newMiage("Nantes");
        newMiage("Nice");
        newMiage("Orléans");
        newMiage("Paris - Dauphine");
        newMiage("Paris - Descartes");
        newMiage("Paris - Evry");
        newMiage("Paris - Nanterre");
        newMiage("Paris - Orsay");
        newMiage("Paris - Sorbonne");
        newMiage("Rennes");
        newMiage("Toulouse");
    }*/

    public Miage newMiage(String miage) {
        Miage m = new Miage(miage);
        em.persist(m);
        return m;
    }

    public Collection<Miage> getAllMiage() {
        // Exécution d'une requête équivalente à un select *  
        Query q = em.createQuery("select m from Miage m order by m.miage");
        return q.getResultList();
    }
}
