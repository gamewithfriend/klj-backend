package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class TrainerDto {

    private String trainerName;
    private String gymName;

}
