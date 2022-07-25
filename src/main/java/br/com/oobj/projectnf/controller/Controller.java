package br.com.oobj.projectnf.controller;

import br.com.oobj.projectnf.Integrador;
import br.com.oobj.projectnf.PreImpressaoResponse;
import br.com.oobj.projectnf.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

@RestController
public class Controller {
    private final Integrador integrador;
    private final TokenService tokenService;

    public Controller(Integrador integrador, TokenService tokenService){

        this.integrador = integrador;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/pre-impressao")
    public ResponseEntity<PreImpressaoResponse> preImpressao(@RequestBody String arquivo) throws IOException {

        PreImpressaoResponse response = new PreImpressaoResponse();

        if(Objects.equals(arquivo, "")){
            response.setPreImpressaoSolicitada(false);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);

        }else{
            integrador.processa(arquivo, LocalDateTime.now());
            response.setPreImpressaoSolicitada(true);
            return ResponseEntity.ok(response);
        }
    }

    @PostMapping("apikey")
    public void geraApiKey(){
        String s = tokenService.geraToken();
       System.out.println("O token é: " + s);
    }
}
