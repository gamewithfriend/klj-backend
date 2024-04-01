package klj.project.repository;

import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTrainerRepository extends JpaRepository<Trainer, Long> {
}
