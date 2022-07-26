package br.com.oobj.projectnf;

import br.com.oobj.projectnf.service.ArquivoService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class Receiver {

    @Value("${projectnf.diretorio.arquivosFinais}")
    private String DIR_ARQUIVOS_FINAIS;

    private  String nomeArquivo;
    private final AtomicInteger i = new AtomicInteger(0);
    private final List<String> mensagens = new ArrayList<>();
    private final List<String> dadosSaida = new ArrayList<>();



    private final ArquivoService arquivoService;

    private final ActiveMQService activeMQService;

    public Receiver(ArquivoService arquivoService, ActiveMQService activeMQService){
        this.arquivoService = arquivoService;
        this.activeMQService = activeMQService;
    }

    public void recebeInformacoesMensagem (String nomeArquivo){
        this.nomeArquivo = nomeArquivo;
    }


    @JmsListener(destination = "pre_impressao", concurrency = "4")
    public void onReceiverQueue(String str) {
        mensagens.add(str);
        if(activeMQService.getQuantidadeMensagem() == i.addAndGet(1)){
            escreveArquivoFinal();
        }
    }

    private void escreveArquivoFinal(){
        List<String> dadosSaida = formataMensagens(mensagens);
        String dados = dadosSaida.toString().replace(" ", "")
                .replace(",", "")
                .replace("[", "")
                .replace("]", "");
        arquivoService.salvaNaPasta(dados, nomeArquivo, DIR_ARQUIVOS_FINAIS);
    }


    private List<String> formataMensagens(List<String> mensagens){
        for(String mensagem : mensagens) {
            String str1 = removeEspacos(mensagem);
            dadosSaida.add(retornaSubItinerario(str1) + "|" + retornaSEQ(str1)+"\n");
        }
        return dadosSaida;
    }


    private String retornaSubItinerario(String str){
        try {
            return str.substring(str.indexOf("SUB-ITINERÁRIO") + 15
                    , str.indexOf("22003"
                            , str.indexOf("SUB-ITINERÁRIO")));
        }catch (Exception e){
            throw new NaoPodeSerLidoException("Arquivo não segue os requisitos pré-estabelecidos.");
        }
    }

    private String retornaSEQ(String str){
        return str.substring(str.trim().indexOf("SEQ")+4
                ,str.indexOf("22008"
                        ,str.indexOf("SEQ")));
    }

    private String removeEspacos(String str){
        return str.replaceAll("\\s","");
    }
}
