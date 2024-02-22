package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.incidentes.IncidenteXComunidad;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;


public class RepositorioDeIncidentesXComunidad implements WithSimplePersistenceUnit, ICrudRepository {

    @Override
    public List buscarTodos() {

        return entityManager().createQuery("from " + IncidenteXComunidad.class.getName()).getResultList();
    }

    public Object buscarXIncidente(Long incidente_id) {

        String hql = "FROM " + IncidenteXComunidad.class.getName() + " WHERE incidente_id = :incidente_id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("incidente_id", incidente_id);
        return query.getResultList();
    }

    public Object buscaPrimerCierre(Long incidente_id) {

        String hql = "FROM " + IncidenteXComunidad.class.getName() + " WHERE incidente_id = :incidente_id ORDER BY fechaCierre ASC";
        Query query = entityManager().createQuery(hql);
        query.setParameter("incidente_id", incidente_id);
        return query.getResultList();
    }

    public Object buscarIncidenteXComunidad(Long incidente_id, Long comunidad_id) {

        String hql = "FROM " + IncidenteXComunidad.class.getName() + " WHERE incidente_id = :incidente_id AND comunidad_id = :comunidad_id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("incidente_id", incidente_id);
        query.setParameter("comunidad_id", comunidad_id);
        return query.getResultList();
    }

    @Override
    public Object buscar(Long id) {

        return entityManager().find(IncidenteXComunidad.class, id);
    }

    @Override
    public void guardar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().persist(o);
        tx.commit();

    }

    @Override
    public void actualizar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().merge(o);
        tx.commit();
    }

    @Override
    public void eliminar(Object o) {
        EntityTransaction tx = entityManager().getTransaction();
        if(!tx.isActive())
            tx.begin();

        entityManager().remove(o);
        tx.commit();
    }

    @Override
    public void limpiarCache(){
        entityManager().clear();
    }
}
