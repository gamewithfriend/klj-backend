package klj.project.repository;

import klj.project.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    long count();


    Optional<User> findByOauthId(String oauthId);
}
