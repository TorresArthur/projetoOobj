package br.com.oobj.projectnf.broker;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JmsService {

    private final JmsTemplate jmsTemplate;

    public JmsService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
    }

    public void enviaMensgem(String mensagem, String fila){
        jmsTemplate.convertAndSend(fila, mensagem);
    }

    public synchronized void enviaListaDeMensagens(List<String> mensagens, String fila)  {
            for (String mensagem : mensagens) {
                jmsTemplate.convertAndSend(fila, mensagem);
        }
    }

}
