package klj.project.repository.trainer;

import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    long count();

}
