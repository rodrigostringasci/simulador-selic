package br.com.testevv.simuladorselic;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class SimuladorSelicExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(ConstraintViolationException.class)
	public void constraintViolationException(HttpServletResponse response) throws IOException {
		response.sendError(HttpStatus.BAD_REQUEST.value());
	}

	@ExceptionHandler({ TaxaSelicNotFoundException.class })
	protected ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, "Taxa Selic não encontrada.", new HttpHeaders(), HttpStatus.NOT_FOUND,
				request);
	}

	@ExceptionHandler({ TaxaSelicFailException.class })
	protected ResponseEntity<Object> handleFail(Exception ex, WebRequest request) {
		return handleExceptionInternal(ex, "Erro ao obter a Taxa Selic.", new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
}
