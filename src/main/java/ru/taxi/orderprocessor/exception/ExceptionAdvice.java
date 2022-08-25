package ru.taxi.orderprocessor.exception;


import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice //глобальный обработчик исключений
public class ExceptionAdvice {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConstraintViolationException.class, DataIntegrityViolationException.class, InvalidDataAccessApiUsageException.class // class показывет что мы получаем доступ к этому классу
             })
    @ResponseBody
    public ApiError handleConstaintException(Exception exception) {

        log.error("exception caught by advice {}", exception.getMessage());
        if (Objects.nonNull(exception.getCause())) {
            return wrapBusinessException(exception.getCause(), HttpStatus.BAD_REQUEST);
        }

        return wrapBusinessException(exception, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({EntityNotFoundException.class})
    @ResponseBody
    public ApiError handleConstaintException(EntityNotFoundException exception) {

        log.error("exception caught by advice {}", exception.getMessage());


        return wrapBusinessException(exception, HttpStatus.NOT_FOUND);

    }


    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ApiError handleException(Exception exception) {

        log.error("exception caught by advice {}", exception.getMessage());
//        if (Objects.nonNull(exception.getCause())) {
//            return wrapSystemException(exception.getCause(),HttpStatus.INTERNAL_SERVER_ERROR);
//        }

        return wrapSystemException(HttpStatus.INTERNAL_SERVER_ERROR);

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    @ResponseBody
    public ApiError test(MethodArgumentNotValidException methodArgumentNotValidException) {

//        log.error("exception caught by advice {}", methodArgumentNotValidException.getMessage());
//
//
//        return wrapValidException(methodArgumentNotValidException,HttpStatus.BAD_REQUEST);

        BindingResult exceptions = methodArgumentNotValidException.getBindingResult();
        if (methodArgumentNotValidException.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                String violationsMerged=errors.stream().map(error->(FieldError) error)
                        .map(error-> error.getDefaultMessage() + ": <" + error.getField()+">")
                        .collect(Collectors.joining("; "));

                return wrapValidException(violationsMerged, HttpStatus.BAD_REQUEST);
            }
        }
        return wrapValidException("Validation error", HttpStatus.BAD_REQUEST);
    }

    private ApiError wrapValidException(String message, HttpStatus status) {
        return ApiError.builder().message(message).status(status).type(ApiErrorType.VALIDATION).build();
    }


    private ApiError wrapBusinessException(Throwable throwable, HttpStatus status) {
        return ApiError.builder().message(throwable.getMessage()).status(status).type(ApiErrorType.BUSINESS).build();
    }

    private ApiError wrapSystemException(HttpStatus status) {
        return ApiError.builder().status(status).type(ApiErrorType.SYSTEM).build();
    }

}


