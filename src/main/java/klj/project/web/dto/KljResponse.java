package klj.project.web.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

@NoArgsConstructor
@Getter
public class KljResponse<T> {
    private static String RESULT_SUCCESS = "SUCCESS";
    private static String RESULT_FAIL = "ERROR";

    private Error error;

    @Schema(subTypes = Object.class)
    private T data;

    @Schema(example = "SUCCESS")
    private String result; //SUCCESS, ERROR

    public KljResponse(Error error, T data, String result) {
        this.error = error;
        this.data = data;
        this.result = result;
    }

    public static <T> BodyBuilder<T> create() {
        return new BodyBuilder<>();
    }

    public static class BodyBuilder<T> {
        private Error error;
        private String result;

        public BodyBuilder<T> succeed() {
            this.result = RESULT_SUCCESS;
            return this;
        }

        public BodyBuilder<T> fail(Error error) {
            this.result = RESULT_FAIL;
            this.error = error;
            return this;
        }

        public <T> KljResponse<T> buildWith(@Nullable T data) {
            return new KljResponse<T>(error, data, result);
        }
    }
}
