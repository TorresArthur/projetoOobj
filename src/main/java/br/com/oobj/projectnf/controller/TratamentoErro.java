package br.com.oobj.projectnf.controller;

import br.com.oobj.projectnf.NaoPodeSerLidoException;
import br.com.oobj.projectnf.PreImpressaoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class TratamentoErro {

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NaoPodeSerLidoException.class)
    public PreImpressaoResponse trataErro(NaoPodeSerLidoException e){
        PreImpressaoResponse response = new PreImpressaoResponse();
        response.setPreImpressaoSolicitada(false);
        return response;
    }

}
