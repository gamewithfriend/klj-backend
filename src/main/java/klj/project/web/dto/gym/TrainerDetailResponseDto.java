package klj.project.web.dto.gym;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TrainerDetailResponseDto {
    private Long trainerId;
    private String address;
    private String gymName;
    private String trainerName;
}
