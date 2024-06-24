package klj.project.domain.user;

import jakarta.persistence.*;
import klj.project.domain.code.Code;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class TrainerCategoryId implements Serializable {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_category_code")
    private Code trainerCategoryCode;

}
