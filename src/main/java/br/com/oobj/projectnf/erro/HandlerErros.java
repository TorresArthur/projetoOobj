package br.com.oobj.projectnf.erro;

import org.springframework.stereotype.Service;
import org.springframework.util.ErrorHandler;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class HandlerErros implements ErrorHandler {

    @Override
    @ExceptionHandler(Exception.class)
    public void handleError(Throwable t) {

    }

}
