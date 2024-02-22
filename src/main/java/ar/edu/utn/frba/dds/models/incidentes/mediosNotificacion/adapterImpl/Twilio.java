package ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapterImpl;

import ar.edu.utn.frba.dds.models.comunidades.Miembro;
import ar.edu.utn.frba.dds.models.incidentes.mediosNotificacion.adapter.AdapterCelular;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

public class Twilio implements AdapterCelular {
    public static final String ACCOUNT_SID = "ACf264a2207106fa392aad7c05bfe0888e";
    public static final String AUTH_TOKEN = "8407e353de89188c3afe36f2258d8d0b";
    public static final String MY_SENDER_NUMBER = "+14155238886";

    @Override
    public void enviarNotificacion(Miembro miembro, String notificacion) {

        com.twilio.Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new PhoneNumber("whatsapp:+54"+miembro.getCelular()),
                        new PhoneNumber("whatsapp:"+MY_SENDER_NUMBER),
                        notificacion)
                .create();


        System.out.println("Notificacion por whatsapp enviada a :" + miembro.getCelular());
    }
}

/*
 *Send a WhatsApp message
 *Use WhatsApp and send a message from your device to
 *WhatsApp logo+1 415 523 8886
 *with code join individual-lungs
 **/