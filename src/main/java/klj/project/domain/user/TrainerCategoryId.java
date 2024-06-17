package klj.project.domain.user;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TrainerCategoryId implements Serializable {


    private Long trainerId;

    private String trainerCategoryCode;

}
