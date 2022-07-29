package br.com.oobj.projectnf;

import br.com.oobj.projectnf.broker.ActiveMQService;
import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.IOException;
import java.util.List;

@Service
public class Enfileirador {
    @Value("${spring.active-mq.queue}")
    private String queue;

    @Value("${projectnf.diretorio.entrada}")
    private String DIR_ENTRADA;

    @Value("${projectnf.diretorio.processados}")
    private String DIR_PROCESSADOS;

    private final EditorNF editorNF;
    private final ArquivoService arquivoService;
    private final ActiveMQService activeMQService;




    public Enfileirador(EditorNF editorNF, ArquivoService arquivoService, ActiveMQService activeMQService) throws JMSException {
        this.editorNF = editorNF;
        this.arquivoService = arquivoService;
        this.activeMQService = activeMQService;
    }


    public synchronized void enviaNFParaFila(String nomeArquivo) throws IOException {

            List<String> dados = editorNF.dividirTextoNF(arquivoService.retornaArquivosDaPasta(DIR_ENTRADA, nomeArquivo));

            editorNF.adicionaFinalizadorDeNF(dados, nomeArquivo);

            activeMQService.enviaListaDeMensagens(dados, queue);

            arquivoService.moveArquivo(nomeArquivo, DIR_ENTRADA, DIR_PROCESSADOS);

    }

}
