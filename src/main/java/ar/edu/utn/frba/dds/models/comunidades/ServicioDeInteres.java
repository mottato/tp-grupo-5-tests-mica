package ar.edu.utn.frba.dds.models.comunidades;

import javax.persistence.*;

@Entity
@Table(name = "ServicioDeInteres")
public class ServicioDeInteres {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    @Column(name = "servicio_id")
    private int servicio_id;
    @Column(name = "esAfectado")
    private Boolean esAfectado;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="miembro_id", nullable=false)
    private Miembro miembro;

}
