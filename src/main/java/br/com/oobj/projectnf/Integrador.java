package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class Integrador {
    private final Enfileirador enfileirador;
    private final ArquivoService arquivoService;

    @Value("${projectnf.diretorio.entrada}")
    private String DIR_ENTRADA;

    @Value("${projectnf.diretorio.processados}")
    private String DIR_PROCESSADOS;


    public Integrador(ArquivoService arquivoService, Enfileirador enfileirador){
        this.enfileirador = enfileirador;
        this.arquivoService = arquivoService;

    }
    public void processa(String arquivo, LocalDateTime horaRequisicao) throws IOException {

        arquivoService.salvaNaPasta(arquivo, geraNomeArquivo(horaRequisicao), DIR_ENTRADA);
        enfileirador.enviaParaFila(geraNomeArquivo(horaRequisicao));

    }

    private String geraNomeArquivo(LocalDateTime horaRequisicao){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "pre-impressao-"+horaRequisicao.format(formatter)+".txt";
    }
}
