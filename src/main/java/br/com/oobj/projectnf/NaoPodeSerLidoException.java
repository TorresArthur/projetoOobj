package br.com.oobj.projectnf;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NaoPodeSerLidoException extends RuntimeException {

    public NaoPodeSerLidoException(String errorMessage){
        super(errorMessage);
    }
}
