package br.com.oobj.projectnf.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class ArquivoService {

    public void salvaNaPasta(String arquivo, String nomeArquivo, String diretorio){

        try {
            Files.write(geraPath(diretorio, nomeArquivo), arquivo.getBytes());
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

    private Path geraPath(String diretorio, String nomeArquivo){
        return Paths.get(diretorio + nomeArquivo);
    }
}
