package nextstep.subway.ui;

import nextstep.auth.UnAuthorizedStateException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Void> handleIllegalArgsException(DataIntegrityViolationException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Void> handleIllegalArgsException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(UnAuthorizedStateException.class)
    public ResponseEntity<Void> unAuthorizedStateException(UnAuthorizedStateException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .header("WWW-Authenticate", "Bearer realm=\"Nextstep\"").build();
    }
}
