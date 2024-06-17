package klj.project.domain.user;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TrainerMemberNumberId implements Serializable {

    private Long trainerId;

    private Long trainerMemberNumber;
}
