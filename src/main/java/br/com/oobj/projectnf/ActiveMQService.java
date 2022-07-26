package br.com.oobj.projectnf;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ActiveMQService {

    private final JmsTemplate jmsTemplate;

    private List<String> mensagens = new ArrayList<>();

    public ActiveMQService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void enviaMensgem(String mensagem, String fila){
        jmsTemplate.convertAndSend(fila, mensagem);
    }

    public void enviaListaDeMensagens(List<String> mensagens, String fila){

        this.mensagens = mensagens;

        for(String mensagem : mensagens){
            jmsTemplate.convertAndSend(fila, mensagem);
        }
    }

    public Integer getQuantidadeMensagem(){
       return mensagens.size();
    }

}
