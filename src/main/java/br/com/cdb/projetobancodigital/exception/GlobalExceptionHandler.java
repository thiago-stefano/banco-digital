package br.com.cdb.projetobancodigital.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private ResponseEntity<Map<String, Object>> buildErrorResponse(HttpStatus status, String erro, String codigo, String path) {
        return ResponseEntity.status(status).body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", status.value(),
                "erro", erro,
                "codigo", codigo,
                "path", path
        ));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Map<String, Object>> handleConstraintViolation(ConstraintViolationException e, HttpServletRequest request) {
        LOGGER.error("Erro de validação: {}", e.getMessage());

        String mensagem = e.getConstraintViolations()
                .stream()
                .map(v -> v.getMessage())
                .collect(Collectors.joining("; "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, mensagem, "VALIDACAO_CONSTRAINT", request.getRequestURI());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        LOGGER.error("Erro de validação: {}", e.getMessage());

        String mensagem = e.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getDefaultMessage())
                .collect(Collectors.joining("; "));

        return buildErrorResponse(HttpStatus.BAD_REQUEST, mensagem, "VALIDACAO_ARGUMENTO", request.getRequestURI());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleJsonParseError(HttpMessageNotReadableException ex, HttpServletRequest request) {
        LOGGER.error("Erro de leitura do corpo da requisição", ex);

        String message = ex.getMessage();
        String path = request.getRequestURI();

        if (message.contains("java.time.LocalDate")) {
            return buildErrorResponse(HttpStatus.BAD_REQUEST,
                    "Formato de data inválido. Use o formato dd-MM-yyyy. Ex: \"12-05-1996\"",
                    "FORMATO_DATA_INVALIDO", path);
        }

        return ResponseEntity.badRequest().body(Map.of(
                "timestamp", LocalDateTime.now(),
                "status", 400,
                "erro", "Formato de JSON inválido. Verifique a estrutura dos dados enviados.",
                "codigo", "JSON_MALFORMADO",
                "path", path
                ));
    }

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<Map<String, Object>> handleEntidadeNaoEncontrada(EntidadeNaoEncontradaException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getCodigo(), request.getRequestURI());
    }

    @ExceptionHandler(CepInvalidoException.class)
    public ResponseEntity<Map<String, Object>> handleCepInvalido(CepInvalidoException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "CEP_INVALIDO", request.getRequestURI());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception e, HttpServletRequest request) {
        LOGGER.error("Erro interno inesperado", e);
        return buildErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Erro interno do servidor. Tente novamente mais tarde.",
                "ERRO_INTERNO", request.getRequestURI());
    }
    
    @ExceptionHandler(RegraNegocioException.class)
    public ResponseEntity<Map<String, Object>> handleRegraNegocio(RegraNegocioException ex, HttpServletRequest request) {
        LOGGER.warn("Violação de regra de negócio: {}", ex.getMessage());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, ex.getMessage(), "REGRA_NEGOCIO", request.getRequestURI());
    }

    @ExceptionHandler(ClienteNaoEncontradoException.class)
    public ResponseEntity<Map<String, Object>> handleClienteNaoEncontrado(ClienteNaoEncontradoException ex, HttpServletRequest request) {
        return buildErrorResponse(HttpStatus.NOT_FOUND, ex.getMessage(), "CLIENTE_NAO_ENCONTRADO", request.getRequestURI());
    }
}
