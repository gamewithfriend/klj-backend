package klj.project.domain.user;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Getter
@NoArgsConstructor
public class TrainerCategory {

    @EmbeddedId
    private TrainerCategoryId trainerCategoryId;
}
