package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.comunidades.RolesUsuario;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import javax.persistence.EntityTransaction;
import java.util.List;

public class RepositorioDeRoles implements WithSimplePersistenceUnit, ICrudRepository {

    @Override
    public List buscarTodos() {

        return entityManager().createQuery("from " + RolesUsuario.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id) {

        return entityManager().find(RolesUsuario.class, id);
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
