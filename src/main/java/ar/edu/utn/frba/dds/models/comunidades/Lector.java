package ar.edu.utn.frba.dds.models.comunidades;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;


@Getter
@Setter
@Entity
public class Lector extends RolesUsuario {
    private void enviarComentario(String comentario){
        System.out.print(comentario);
    }
}
