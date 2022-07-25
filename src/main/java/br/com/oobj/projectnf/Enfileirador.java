package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    public Enfileirador(ArquivoService arquivoService, JmsTemplate jmsTemplate) throws JMSException {
        this.arquivoService = arquivoService;
        this.jmsTemplate = jmsTemplate;
    }

    public void enviaParaFila(String nomeArquivo) throws IOException{
    try {
        String arquivo = new String(Files.readAllBytes(Paths.get(DIR_ENTRADA + nomeArquivo)));
        List<String> dados = dividirArquivo(arquivo);
        for (String dado : dados) {
            jmsTemplate.convertAndSend(queue, dado);
        }
        arquivoService.moveArquivo(nomeArquivo, DIR_ENTRADA, DIR_PROCESSADOS);
    }catch (Exception e){
        throw new NaoPodeSerLidoException("n pode ser lido");
    }
    }

    public List<String> dividirArquivo(@NotNull String arquivo) {
        List<String> dados = new ArrayList<>();

        if(arquivo.contains("25000;STAPLE_TOP_LEFT")) {
            Arrays.asList(arquivo.split("25000;STAPLE_TOP_LEFT")).forEach(a -> {
                String dado = a + "25000;STAPLE_TOP_LEFT";
                dados.add(dado);
            });
            return dados;
        }
        throw new NaoPodeSerLidoException("Arquivo não segue requisitos pré-estabelecidos. Por favor, verifique o arquivo e o envie novamente!");
    }
}
