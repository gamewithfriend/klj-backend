package klj.project.service;

import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component("userDetailsService")
@RequiredArgsConstructor
@Service
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String oauthId) throws UsernameNotFoundException {
        User user = userRepository.findByOauthId(oauthId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with oauthId: " + oauthId));

        return org.springframework.security.core.userdetails.User.builder()
                .username(user.getOauthId())
                .authorities(user.getAuthority().name()) // Assuming authority name is a string
                .build();
    }
}
