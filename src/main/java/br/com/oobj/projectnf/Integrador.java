package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class Integrador {
    private final EditorNF editorNF;
    private final Enfileirador enfileirador;
    private final ArquivoService arquivoService;

    @Value("${projectnf.diretorio.entrada}")
    private String DIR_ENTRADA;

    @Value("${projectnf.diretorio.processados}")
    private String DIR_PROCESSADOS;


    public Integrador(EditorNF editorNF, ArquivoService arquivoService, Enfileirador enfileirador){
        this.editorNF = editorNF;
        this.enfileirador = enfileirador;
        this.arquivoService = arquivoService;

    }
    public void processaNF(String textoArquivo, LocalDateTime horaRequisicao) throws IOException {

        arquivoService.salvaTextoNaPasta(textoArquivo, editorNF.geraNomeArquivo(horaRequisicao), DIR_ENTRADA);

        enfileirador.enviaNFParaFila(editorNF.geraNomeArquivo(horaRequisicao));



    }

}
