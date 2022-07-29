package br.com.oobj.projectnf.NF;

import br.com.oobj.projectnf.NF.EditorNF;
import br.com.oobj.projectnf.broker.JmsService;
import br.com.oobj.projectnf.service.ArquivoTextoService;
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
    private final ArquivoTextoService arquivoTextoService;
    private final JmsService jmsService;




    public Enfileirador(EditorNF editorNF, ArquivoTextoService arquivoTextoService, JmsService jmsService) throws JMSException {
        this.editorNF = editorNF;
        this.arquivoTextoService = arquivoTextoService;
        this.jmsService = jmsService;
    }


    public synchronized void enviaNFParaFila(String nomeArquivo) throws IOException {

            List<String> dados = editorNF.dividirTextoNF(arquivoTextoService.retornaArquivosTextoDaPasta(DIR_ENTRADA, nomeArquivo));

            editorNF.adicionaFinalizadorDeNF(dados, nomeArquivo);

            jmsService.enviaListaDeMensagens(dados, queue);

            arquivoTextoService.moveArquivo(nomeArquivo, DIR_ENTRADA, DIR_PROCESSADOS);

    }

}
