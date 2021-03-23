package br.com.api.ml.exception;

import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(GlobalException.class)
    public @ResponseBody ErrorDTO handleGenericException(GlobalException e) {
        e.printStackTrace();
        return new ErrorDTO(e.getMessage());
    }



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody List<ErrorDTO> handleValidationError(MethodArgumentNotValidException e) {

        List<ErrorDTO> errosDeValidacao = new ArrayList<>();

        for (ObjectError erro : e.getBindingResult().getAllErrors()) {

            if (nonNull(erro.getCodes())) {
                String mensagemASerExibida = erro.getDefaultMessage();
                errosDeValidacao.add(new ErrorDTO(mensagemASerExibida.toString()));
            }
        }

        return errosDeValidacao;
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public @ResponseBody ErrorDTO handleGenericException(Exception e) {
        e.printStackTrace() ;
        return new ErrorDTO(e.getLocalizedMessage());
    }

}
