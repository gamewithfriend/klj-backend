package klj.project.web.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoRequestDto {

    private Long id;
    private String nickName;
}
