package klj.project.web.dto.user;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoResponseDto {

    private Long id;
    private String nickName;
    private String profilePath;
}
