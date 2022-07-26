package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class Enfileirador {
    @Value("${spring.active-mq.queue}")
    private String queue;

    @Value("${projectnf.diretorio.entrada}")
    private String DIR_ENTRADA;

    @Value("${projectnf.diretorio.processados}")
    private String DIR_PROCESSADOS;

    private final JmsTemplate jmsTemplate;
    private final ArquivoService arquivoService;
    private final ActiveMQService activeMQService;
    private final Receiver receiver;

    public Enfileirador(ArquivoService arquivoService, JmsTemplate jmsTemplate, ActiveMQService activeMQService, Receiver receiver) throws JMSException {
        this.arquivoService = arquivoService;
        this.jmsTemplate = jmsTemplate;
        this.activeMQService = activeMQService;
        this.receiver = receiver;
    }

    public void processaMensagem(){}

    public void enviaParaFila(String nomeArquivo) throws IOException{
    try {
        List<String> dados = dividirArquivo(arquivoService.retornaArquivosDaPasta(DIR_ENTRADA, nomeArquivo));

        receiver.recebeInformacoesMensagem(nomeArquivo);

        activeMQService.enviaListaDeMensagens(dados,queue);
        arquivoService.moveArquivo(nomeArquivo, DIR_ENTRADA, DIR_PROCESSADOS);

    }catch (Exception e){
        throw new NaoPodeSerLidoException("n pode ser lido");
    }
    }

    public List<String> dividirArquivo(String arquivo) {
        List<String> dados = new ArrayList<>();
        AtomicReference<Integer> i = new AtomicReference<>(1);
        if(arquivo.contains("25000;STAPLE_TOP_LEFT")) {
            Arrays.asList(arquivo.split("25000;STAPLE_TOP_LEFT")).forEach(a -> {
                String dado = a + "25000;STAPLE_TOP_LEFT";
                dados.add(dado);
                i.getAndSet(i.get() + 1);
            });
            return dados;
        }
        throw new NaoPodeSerLidoException("Arquivo não segue requisitos pré-estabelecidos. Por favor, verifique o arquivo e o envie novamente!");
    }
}
