package klj.project.repository;

import klj.project.domain.file.Files;
import klj.project.domain.notice.Notice;
import klj.project.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FileRepository extends JpaRepository<Files, Long> {

    Optional<Files> findByFileGroupId(Long fileGroupId);
}
