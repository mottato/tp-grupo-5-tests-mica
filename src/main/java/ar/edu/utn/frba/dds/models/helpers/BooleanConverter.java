package ar.edu.utn.frba.dds.models.helpers;

public class BooleanConverter {

    public static Boolean StringToBooleanConverter(String atributo){
        return atributo.equals("Si");
    }
}
