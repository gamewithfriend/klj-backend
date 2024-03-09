package klj.project.web.dto.login.oauth;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OauthTokenDto {
    private String access_token;
    private String refresh_token;
    private String token_type;
    private Long expires_in;
}
