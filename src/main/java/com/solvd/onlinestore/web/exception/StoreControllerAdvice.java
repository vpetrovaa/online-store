package com.solvd.onlinestore.web.exception;

import com.solvd.onlinestore.domain.exception.ResourceAlreadyExistsException;
import com.solvd.onlinestore.domain.exception.ResourceDoesNotExistException;
import com.solvd.onlinestore.domain.exception.SqlException;
import com.solvd.onlinestore.domain.exception.WrongOrderingException;
import com.solvd.onlinestore.web.dto.ResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class StoreControllerAdvice{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
        return new ResponseDto(HttpStatus.BAD_REQUEST, errors);
    }

    @ExceptionHandler(ResourceDoesNotExistException.class)
    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
    public ResponseDto handleResourceDoesNotExist(ResourceDoesNotExistException ex){
        return new ResponseDto(HttpStatus.NOT_ACCEPTABLE, List.of(ex.getMessage()));
    }

    @ExceptionHandler(WrongOrderingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleWrongOrder(WrongOrderingException ex){
        return new ResponseDto(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseDto handleResourceAlreadyExists(ResourceAlreadyExistsException ex){
        return new ResponseDto(HttpStatus.NOT_FOUND, List.of(ex.getMessage()));
    }

    @ExceptionHandler(SqlException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleSqlException(SqlException ex){
        return new ResponseDto(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseDto handleHttpMessageNotReadableException(HttpMessageNotReadableException ex){
        return new ResponseDto(HttpStatus.BAD_REQUEST, List.of(ex.getMessage()));
    }

}
