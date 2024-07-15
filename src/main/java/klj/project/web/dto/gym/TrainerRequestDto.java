package klj.project.web.dto.gym;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
@JsonAutoDetect
public class TrainerRequestDto {

    private List<String> category;
    private String trainingArea;
    private int personCnt;
    private String trainingTime;

    public TrainerRequestDto(List<String> category, String trainingArea, int personCnt, String trainingTime){
        this.category = category;
        this.trainingArea = trainingArea;
        this.personCnt = personCnt;
        this.trainingTime = trainingTime;

    }

}
