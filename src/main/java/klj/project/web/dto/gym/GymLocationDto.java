package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;

@Data
public class GymLocationDto {

    private String address;
    private String gymName;

}
