package klj.project.web.dto.user;

import klj.project.web.dto.gym.TrainerCategoryDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserTrainerAppplyDto {

    private String employmentHistoryPeriod;
    private String phoneNumber;
    private String trainPlace;
    private String trainPlaceDetail;
    private String trainPlacePostcode;
    private String trainPlaceName;
    private List<TrainerCategoryDto> trainCategoryCodeList;
    private String email;



}
