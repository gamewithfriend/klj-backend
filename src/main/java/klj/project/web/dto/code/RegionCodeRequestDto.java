package klj.project.web.dto.code;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;

@Getter
@JsonAutoDetect
public class RegionCodeRequestDto {

    private String area;
    private String region;

    public RegionCodeRequestDto(String area, String region){
        this.area = area;
        this.region = region;
    }

}
