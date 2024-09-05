package klj.project.repository;

import klj.project.domain.file.FileGroup;
import klj.project.domain.notice.Notice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FileGroupRepository extends JpaRepository<FileGroup, Long> {
}
