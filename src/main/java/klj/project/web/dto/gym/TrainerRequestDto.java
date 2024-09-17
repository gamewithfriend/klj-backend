package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import klj.project.domain.code.Code;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@JsonAutoDetect
public class TrainerRequestDto {

    private List<String> category;
    private String trainingArea;
    private int personCnt;
    private String startTime;
    private String endTime;

    public TrainerRequestDto(List<String> category, String trainingArea, int personCnt, String startTime, String endTime){
        this.category = category;
        this.trainingArea = trainingArea;
        this.personCnt = personCnt;
        this.startTime = startTime;
        this.endTime = endTime;

    }

}
