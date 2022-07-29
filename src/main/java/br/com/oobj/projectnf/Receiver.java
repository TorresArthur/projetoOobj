package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class Receiver {

    @Value("${projectnf.diretorio.arquivosFinais}")
    private String DIR_ARQUIVOS_FINAIS;

    private  String nomeArquivo;

    private final List<String> mensagens = new ArrayList<>();


    private final EditorNF editorNF;

    private final ArquivoService arquivoService;



    public Receiver(EditorNF editorNF, ArquivoService arquivoService){
        this.editorNF = editorNF;
        this.arquivoService = arquivoService;
    }



    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void recebeESalvaNF (String str) {

        mensagens.add(str);

        if(Objects.equals(str, "EOF")) {
            String nomeArquivoFinal = editorNF.geraNomeArquivoFinal(mensagens.get(mensagens.size() - 2));
            arquivoService.salvaTextoNaPasta(editorNF.geraArquivoFinal(mensagens), nomeArquivoFinal, DIR_ARQUIVOS_FINAIS);
            mensagens.clear();

        }
    }
}
