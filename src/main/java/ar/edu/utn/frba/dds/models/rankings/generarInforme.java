package ar.edu.utn.frba.dds.models.rankings;

import ar.edu.utn.frba.dds.models.entidades.Entidad;
import org.apache.commons.mail.EmailException;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class generarInforme {

    private IRankeable criterio;

    private List<Entidad> entidades;

    private void inicializar(){
        //query de entidades
        entidades = new ArrayList<>();

        criterio = new RankingXCantidad();

    }

    public void ejecutar(){
        Timer timer;
        timer = new Timer();
        this.inicializar();
        TimerTask task = new TimerTask() {
            @Override
            public void run()
            {
                //primero por cantidad
                criterio.rankear(entidades);
                // por tiempo
                criterio = new RankingXTiempo();
                criterio.rankear(entidades);
                //por impacto
                criterio = new RankingXImpacto();
                criterio.rankear(entidades);
                    //persistir los listados en repo
            }
        };

        timer.schedule(task, 604800000, 604800000);
        //una vez por semana
    }

}
