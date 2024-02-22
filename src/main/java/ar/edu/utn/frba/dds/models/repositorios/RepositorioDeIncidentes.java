package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.entidades.Entidad;
import ar.edu.utn.frba.dds.models.incidentes.Incidente;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import com.twilio.rest.api.v2010.account.incomingphonenumber.Local;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

public class RepositorioDeIncidentes implements WithSimplePersistenceUnit, ICrudRepository {

    @Override
    public List buscarTodos() {

        return entityManager().createQuery("from " + Incidente.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {

        return entityManager().find(Incidente.class, id);
    }

    public List buscarCerrados(LocalDateTime desde, LocalDateTime hasta){
        String hql = "SELECT id FROM " + Incidente.class.getName() + " WHERE estado = False AND fechaApertura" +
                " BETWEEN :desde AND :hasta GROUP BY id, date_format(fechaApertura, 'Y-m-d HH')";
        Query query = entityManager().createQuery(hql);
        query.setParameter("desde", desde);
        query.setParameter("hasta", hasta);
        return query.getResultList();
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
