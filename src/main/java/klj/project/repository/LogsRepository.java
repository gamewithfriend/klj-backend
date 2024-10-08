package klj.project.repository;

import klj.project.domain.file.Files;
import klj.project.domain.util.Logs;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogsRepository extends JpaRepository<Logs, Long> {
}
