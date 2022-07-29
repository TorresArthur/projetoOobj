package br.com.oobj.projectnf.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArquivoTextoService {

    public void salvaTextoNaPasta(String arquivoTexto, String nomeArquivo, String diretorio){

        try {
            Files.write(geraPath(diretorio, nomeArquivo), arquivoTexto.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void moveArquivo(String nomeArquivo, String pastaOrigem, String pastaDestino){
            try {
                Files.move(geraPath(pastaOrigem, nomeArquivo), geraPath(pastaDestino, nomeArquivo), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
    }

    public String retornaArquivosTextoDaPasta(String diretorio, String nomeArquivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(diretorio + nomeArquivo)));

    }

    private Path geraPath(String diretorio, String nomeArquivo){
        return Paths.get(diretorio + nomeArquivo);
    }
}
