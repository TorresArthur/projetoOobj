package br.com.oobj.projectnf.controller;

import br.com.oobj.projectnf.Integrador;
import br.com.oobj.projectnf.dto.PreImpressaoResponse;
import br.com.oobj.projectnf.service.TokenService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDateTime;

@RestController
public class Controller {
    private final Integrador integrador;
    private final TokenService tokenService;

    public Controller(Integrador integrador, TokenService tokenService){

        this.integrador = integrador;
        this.tokenService = tokenService;
    }

    @PostMapping("/api/pre-impressao")
    public ResponseEntity<PreImpressaoResponse> preImpressao(@RequestBody(required = false) String textoArquivo) throws IOException {
        PreImpressaoResponse preImpressaoResponse = new PreImpressaoResponse();

        try {
            integrador.processaNF(textoArquivo, LocalDateTime.now());

            preImpressaoResponse.setPreImpressaoSolicitada(true);
            return ResponseEntity.ok(preImpressaoResponse);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(preImpressaoResponse);
        }
    }

}
