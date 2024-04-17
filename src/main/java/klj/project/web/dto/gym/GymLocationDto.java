package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class GymLocationDto {

    private String address;
    private String gymName;

}
