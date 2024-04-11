package com.nwt.tim2.AppointmentManagement.Exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Date;

@ControllerAdvice
public class GlobalRestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ValidationErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        ValidationErrorResponse error = new ValidationErrorResponse("Validation failed");
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
            error.addToList(new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
        });
       return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Error> handleConstraintViolationException(ConstraintViolationException ex) {
        Error errorResponse = new Error();
        errorResponse.setMessage("Validation failed");
        errorResponse.setCode(HttpStatus.BAD_REQUEST.value());
        errorResponse.setTimeStamp(new Date());
        StringBuilder errorMessage = new StringBuilder();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errorMessage.append(violation.getMessage()).append(", ");
        }
        String message = errorMessage.length() > 2 ? errorMessage.substring(0, errorMessage.length() - 2) : "";
        errorResponse.setMessage(message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @ExceptionHandler(InvalidFormat.class)
    public ResponseEntity<Error> handleInvalidFormatException(InvalidFormat ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObject);
    }
    @ExceptionHandler(IdNotUUID.class)
    public ResponseEntity<Error> handleUserIdNotUUIDException(IdNotUUID ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.BAD_REQUEST.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorObject);
    }
    @ExceptionHandler(UserNotFound.class)
    public ResponseEntity<Error> handleUserNotFoundException(UserNotFound ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }

    @ExceptionHandler(MessageNotFound.class)
    public ResponseEntity<Error> handleMessageNotFoundException(MessageNotFound ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }

    @ExceptionHandler(SessionNotFound.class)
    public ResponseEntity<Error> handleSessionNotFoundException(SessionNotFound ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }
@ExceptionHandler(UserExists.class)
    public ResponseEntity<Error> handleUserExists(UserExists ex){
        Error errorObject = new Error();
    errorObject.setCode(HttpStatus.NOT_ACCEPTABLE.value());
    errorObject.setMessage(ex.getMessage());
    errorObject.setTimeStamp(new Date());
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorObject);
}
@ExceptionHandler(FailedToAdd.class)
    public ResponseEntity<Error> handleFailedToAdd(FailedToAdd ex){
    Error errorObject = new Error();
    errorObject.setCode(HttpStatus.NOT_ACCEPTABLE.value());
    errorObject.setMessage(ex.getMessage());
    errorObject.setTimeStamp(new Date());
    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(errorObject);
}
    @ExceptionHandler(WeeklyReportNotFound.class)
    public ResponseEntity<Error> handleWeeklyReportNotFound(WeeklyReportNotFound ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }
    @ExceptionHandler(DailyReportNotFound.class)
    public ResponseEntity<Error> handleDailyReportNotFound(DailyReportNotFound ex) {
        Error errorObject = new Error();
        errorObject.setCode(HttpStatus.NOT_FOUND.value());
        errorObject.setMessage(ex.getMessage());
        errorObject.setTimeStamp(new Date());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorObject);
    }
}
