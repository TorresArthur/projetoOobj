package br.com.oobj.projectnf.io;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Escritor {
    private String arquivo;
    private LocalDateTime horaRequisicao;

    public Escritor(String arquivo, LocalDateTime horaRequisicao){
        this.arquivo = arquivo;
        this.horaRequisicao = horaRequisicao;
    }

   public void esctreveTXT () throws FileNotFoundException {
       PrintWriter out = new PrintWriter("pre-impressao-" + formataDataParaString() + ".txt");
       out.println(arquivo);
       out.close();
   }

   private String formataDataParaString(){
       DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
       return horaRequisicao.format(formatter);
   }
}
