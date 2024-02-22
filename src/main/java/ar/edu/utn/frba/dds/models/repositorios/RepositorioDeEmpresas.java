package ar.edu.utn.frba.dds.models.repositorios;

import ar.edu.utn.frba.dds.models.comunidades.Usuario;
import ar.edu.utn.frba.dds.models.entidades.Empresa;
import io.github.flbulgarelli.jpa.extras.simple.WithSimplePersistenceUnit;

import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.util.List;

public class RepositorioDeEmpresas  implements WithSimplePersistenceUnit, ICrudRepository {


    @Override
    public List buscarTodos() {

        return entityManager().createQuery("from " + Empresa.class.getName()).getResultList();
    }

    @Override
    public Object buscar(Long id)
    {
        return entityManager().find(Empresa.class, id);
    }

    public List<Object> buscarEmpresaXCodigo(Integer codigoEmpresa) {

        String hql = "FROM " + Empresa.class.getName() + " WHERE codigoEmpresa = :codigoEmpresa";
        Query query = entityManager().createQuery(hql);
        query.setParameter("codigoEmpresa", codigoEmpresa);
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

