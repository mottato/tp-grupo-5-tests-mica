package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import ar.edu.utn.frba.dds.models.servicios.Servicio;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;


import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class RepositorioDeUsuarios implements WithSimplePersistenceUnit, ICrudRepository {

    @Override
    public List buscarTodos() {

        return entityManager().createQuery("from " + Usuario.class.getName()).getResultList();
    }

    public List login(String email, String password) {

        System.out.println("usando login");

        String hql = "FROM " + Usuario.class.getName() + " WHERE email = :email AND contrasenia = :password";
        Query query = entityManager().createQuery(hql);
        query.setParameter("email", email);
        query.setParameter("password", password);

        System.out.println("usando login");

        return query.getResultList();

   }


    @Override
    public Object buscar(Long id) {

        return entityManager().find(Usuario.class, id);
    }

    public Object buscarXRol(Long rol_id) {

        String hql = "FROM " + Usuario.class.getName() + " WHERE rol_id = :rol_id";
        Query query = entityManager().createQuery(hql);
        query.setParameter("rol_id", rol_id);
        return query.getResultList();

    }


    public Usuario buscarXEmail(String email) {
        return entityManager().find(Usuario.class, email);
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
