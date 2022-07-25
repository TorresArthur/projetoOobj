package br.com.oobj.projectnf;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Receiver {
    private final List<String> dadosSaida = new ArrayList<>();


    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void onReceiverQueue(String str) {
        String str1 = removeEspacos(str);
        System.out.println(retornaSubItinerario(str1) + "|" + retornaSEQ(str1));

    }


    private String retornaSubItinerario(String str){
        try {
            return str.substring(str.indexOf("SUB-ITINERÁRIO") + 15
                    , str.indexOf("22003"
                            , str.indexOf("SUB-ITINERÁRIO")));
        }catch (Exception e){
            throw new NaoPodeSerLidoException("Arquivo não segue os requisitos pré-estabelecidos.");
        }
    }

    private String retornaSEQ(String str){
        return str.substring(str.trim().indexOf("SEQ")+4
                ,str.indexOf("22008"
                        ,str.indexOf("SEQ")));
    }

    private String removeEspacos(String str){
        return str.replaceAll("\\s","");
    }
}
