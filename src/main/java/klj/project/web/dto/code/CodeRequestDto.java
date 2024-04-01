package klj.project.web.dto.code;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@JsonAutoDetect
public class CodeRequestDto {

    private String id;
    private String name;

    public CodeRequestDto(String id, String name){
        this.id = id;
        this.name = name;
    }
}
