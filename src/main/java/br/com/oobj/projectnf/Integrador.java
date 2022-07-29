package br.com.oobj.projectnf;

import br.com.oobj.projectnf.NF.EditorNF;
import br.com.oobj.projectnf.NF.Enfileirador;
import br.com.oobj.projectnf.service.ArquivoTextoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class Integrador {
    private final EditorNF editorNF;
    private final Enfileirador enfileirador;
    private final ArquivoTextoService arquivoTextoService;

    @Value("${projectnf.diretorio.entrada}")
    private String DIR_ENTRADA;

    @Value("${projectnf.diretorio.processados}")
    private String DIR_PROCESSADOS;


    public Integrador(EditorNF editorNF, ArquivoTextoService arquivoService, Enfileirador enfileirador){
        this.editorNF = editorNF;
        this.enfileirador = enfileirador;
        this.arquivoTextoService = arquivoService;

    }
    public void processaNF(String textoArquivo, LocalDateTime horaRequisicao) throws IOException {

        arquivoTextoService.salvaTextoNaPasta(textoArquivo, editorNF.geraNomeArquivo(horaRequisicao), DIR_ENTRADA);

        enfileirador.enviaNFParaFila(editorNF.geraNomeArquivo(horaRequisicao));


    }

}
