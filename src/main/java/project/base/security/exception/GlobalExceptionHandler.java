package project.base.security.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNullApi;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import project.base.security.dto.ResponseDTO;

import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice @Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseDTO handleException(Exception e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseDTO handleException(NullPointerException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseDTO handleException(IllegalArgumentException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ResponseDTO handleException(IllegalStateException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseDTO handleException(UnsupportedOperationException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NoSuchFieldException.class)
    public ResponseDTO handleException(NoSuchFieldException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NoSuchMethodException.class)
    public ResponseDTO handleException(NoSuchMethodException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NoSuchFieldError.class)
    public ResponseDTO handleException(NoSuchFieldError e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NoSuchMethodError.class)
    public ResponseDTO handleException(NoSuchMethodError e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(NoClassDefFoundError.class)
    public ResponseDTO handleException(NoClassDefFoundError e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ClassNotFoundException.class)
    public ResponseDTO handleException(ClassNotFoundException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ClassCastException.class)
    public ResponseDTO handleException(ClassCastException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ArithmeticException.class)
    public ResponseDTO handleException(ArithmeticException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ArrayIndexOutOfBoundsException.class)
    public ResponseDTO handleException(ArrayIndexOutOfBoundsException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(ArrayStoreException.class)
    public ResponseDTO handleException(ArrayStoreException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseDTO handleException(InterruptedException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(InstantiationException.class)
    public ResponseDTO handleException(InstantiationException e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @ExceptionHandler(InternalError.class)
    public ResponseDTO handleException(InternalError e) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(e.getMessage());
        return responseDTO;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        String comentario = ex.getBindingResult().getFieldErrors().stream()
                .map(x -> "FATAL ERROR: Column '" + x.getField() + "' => " + x.getDefaultMessage().toLowerCase(Locale.ROOT))
                .collect(Collectors.joining(", "));

        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(comentario);
        return ResponseEntity.ok(responseDTO);
    }

    @Override
    public ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO responseDTO = new ResponseDTO();
        responseDTO.setStatus("400");
        responseDTO.setMessage("error");
        responseDTO.setComment(ex.getMessage());
        return ResponseEntity.ok(responseDTO);
    }

    protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ResponseDTO apiError = new ResponseDTO();
        apiError.setStatus("400");
        apiError.setMessage("error");
        apiError.setComment("El método " + ex.getMethod() + " no es sportado ... use el método " + ex.getSupportedHttpMethods());
        return ResponseEntity.ok(apiError);
    }

    // AccessDeniedException
    @ExceptionHandler(AccessDeniedException.class)
    public final ResponseEntity<Map<String, Object>> handleAccessDeniedException(Exception ex, WebRequest request) {
        //code here
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 403);
        body.put("message", ex.getMessage());
        body.put("comment", "Unauthorized");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // UsernameNotFoundException
    @ExceptionHandler(UsernameNotFoundException.class)
    public final ResponseEntity<Map<String, Object>> handleUsernameNotFoundException(Exception ex, WebRequest request) {
        log.error("UsernameNotFoundException: " + ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 404);
        body.put("message", "error");
        body.put("comment", "User not found");
        return new ResponseEntity<>(body, HttpStatus.OK);
    }

    // DataIntegrityViolationException
    @ExceptionHandler(DataIntegrityViolationException.class)
    public final ResponseEntity<Map<String, Object>> handleDataIntegrityViolationException(Exception ex, WebRequest request) {
        log.error("DataIntegrityViolationException: " + ex.getMessage());
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("status", 400);
        body.put("message", "error");
        String message = ex.getCause().getCause().getMessage();
        message = message.substring(message.indexOf("Detail:"));
        log.error("DataIntegrityViolationException: " + message);
        body.put("comment", "Error " + message);
        return ResponseEntity.status(HttpStatus.OK).body(body);
    }
}
