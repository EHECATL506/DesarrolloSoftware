package ControladorBD;

import Modelo.Egreso;
import ControladorBD.exceptions.NonexistentEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Mauricio
 */
public class EgresoJpaController implements Serializable {

    public EgresoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Egreso egreso) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(egreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Egreso egreso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            egreso = em.merge(egreso);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = egreso.getIdEgreso();
                if (findEgreso(id) == null) {
                    throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Egreso egreso;
            try {
                egreso = em.getReference(Egreso.class, id);
                egreso.getIdEgreso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The egreso with id " + id + " no longer exists.", enfe);
            }
            em.remove(egreso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Egreso> findEgresoEntities() {
        return findEgresoEntities(true, -1, -1);
    }

    public List<Egreso> findEgresoEntities(int maxResults, int firstResult) {
        return findEgresoEntities(false, maxResults, firstResult);
    }

    private List<Egreso> findEgresoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Egreso.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Egreso findEgreso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Egreso.class, id);
        } finally {
            em.close();
        }
    }

    public int getEgresoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Egreso> rt = cq.from(Egreso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
