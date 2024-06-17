package klj.project.domain.user;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class TrainerMemberNumber {

    @EmbeddedId
    private TrainerMemberNumberId trainerMemberNumberId;
}
