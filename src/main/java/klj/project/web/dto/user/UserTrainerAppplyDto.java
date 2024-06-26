package klj.project.web.dto.user;

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
    private List<String> trainCategoryCodeList;
    private String email;



}
