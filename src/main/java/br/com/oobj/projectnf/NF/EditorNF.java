package br.com.oobj.projectnf.NF;

import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class EditorNF {

    public List<String> adicionaFinalizadorDeNF(List<String> dados, String nomeArquivo){
        dados.add(nomeArquivo);
        dados.add("EOF");
        return dados;
    }

    public List<String> dividirTextoNF(String textoNF) {
        List<String> dados = new LinkedList<>();
        Arrays.asList(textoNF.split("25000;STAPLE_TOP_LEFT")).forEach(a -> {
            String dado = a + "25000;STAPLE_TOP_LEFT";
            dados.add(dado);
        });
        return dados;
    }

    public String geraNomeArquivo(LocalDateTime horaRequisicao){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS");
        return "pre-impressao-"+horaRequisicao.format(formatter)+".txt";
    }

    public String retornaSubItinerario(String str){
            return str.substring(str.indexOf("SUB-ITINERÁRIO") + 15
                    , str.indexOf("22003"
                            , str.indexOf("SUB-ITINERÁRIO")));
    }

    public String retornaSEQ(String str){
        return str.substring(str.trim().indexOf("SEQ")+4
                ,str.indexOf("22008"
                        ,str.indexOf("SEQ")));
    }

    public String geraArquivoFinal(List<String> mensagens){
        mensagens.remove(mensagens.size() - 2);
        mensagens.remove("EOF");
        List<String> dados = new ArrayList<>();
        for(String mensagem : mensagens) {
            String str1 = removeEspacos(mensagem);
            dados.add(retornaSubItinerario(str1) + "|" + retornaSEQ(str1)+"\n");
        }
        return ajustaArquivoFinal(dados);
    }

    private String ajustaArquivoFinal(List<String> dados){
       return dados.toString().replace(" ", "")
                .replace(",", "")
                .replace("[", "")
                .replace("]", "");
    }
    private String removeEspacos(String str){
        return str.replaceAll("\\s","");
    }


    public String geraNomeArquivoFinal(String s) {
        return s.substring(0, 31) + "-retorno.txt";
    }
}
