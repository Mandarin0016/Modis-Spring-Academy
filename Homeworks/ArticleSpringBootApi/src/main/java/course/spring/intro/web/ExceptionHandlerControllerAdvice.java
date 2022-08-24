package course.spring.intro.web;

import course.spring.intro.dto.ExceptionResponseDTO;
import course.spring.intro.exception.InvalidEntityDataException;
import course.spring.intro.exception.NonexistentEntityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handlerNonexistentEntityException(NonexistentEntityException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponseDTO(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<ExceptionResponseDTO> handlerInvalidEntityDataException(InvalidEntityDataException ex) {
        return ResponseEntity.badRequest()
                .body(new ExceptionResponseDTO(HttpStatus.BAD_REQUEST.value(), ex.getMessage()));
    }
}
