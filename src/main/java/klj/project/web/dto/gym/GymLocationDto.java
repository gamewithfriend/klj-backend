package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;

@Data
public class GymLocationDto {

    private Long trainerId;
    private String address;
    private String gymName;
    private String trainerName;
    private Long userId;

}
