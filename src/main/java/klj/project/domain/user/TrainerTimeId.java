package klj.project.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalTime;

@Data
@Embeddable
public class TrainerTimeId implements Serializable  {

    private Long trainerId;

    private LocalTime trainerTime;
}
