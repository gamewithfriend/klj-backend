package klj.project.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.Objects;

@Getter
@NoArgsConstructor
@Schema(example = "null")
public class Error {

    private HttpStatus code;
    private String message;

    @Builder
    public Error(HttpStatus code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Error error = (Error) o;
        return code == error.code && Objects.equals(message, error.message);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, message);
    }
}

