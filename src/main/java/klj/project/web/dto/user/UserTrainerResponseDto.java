package klj.project.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class UserTrainerResponseDto {
    private Long id;
    private String employmentHistoryPeriod;
    private String phoneNumber;
    private String email;
    private String trainPlace;
    private String trainPlaceDetail;
    private String trainPlacePostcode;
    private String trainPlaceName;
    private Boolean trainerApplyFlag;
    private Boolean trainerAcceptFlag;
}
