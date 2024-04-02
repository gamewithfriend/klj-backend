package klj.project.repository;

import klj.project.domain.notice.Notice;
import klj.project.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
}
