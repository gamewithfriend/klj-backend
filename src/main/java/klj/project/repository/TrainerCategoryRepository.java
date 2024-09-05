package klj.project.repository;

import klj.project.domain.user.TrainerCategory;
import klj.project.domain.user.TrainerCategoryId;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TrainerCategoryRepository extends JpaRepository<TrainerCategory, TrainerCategoryId> {
}
