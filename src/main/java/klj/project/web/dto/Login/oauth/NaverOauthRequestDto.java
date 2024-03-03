package klj.project.web.dto.Login.oauth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class NaverOauthRequestDto {

    @Schema(example = "Nxvw2NsMPEjDSHJJz5")
    private String code;

    @Schema(example = "false")
    private String state;
}
