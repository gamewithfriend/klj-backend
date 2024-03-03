package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Parameter;
import klj.project.service.LoginService;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.Login.oauth.NaverOauthRequestDto;
import klj.project.web.dto.Login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/callback/naver")
    @CrossOrigin("http://localhost:3000")
    public KljResponse<String> getNaverAuth(@RequestBody NaverOauthRequestDto naverOauthRequestDto) {
        String code =naverOauthRequestDto.getCode();
        String state =naverOauthRequestDto.getState();
        String url = "";
        OauthTokenDto oauthTokenDto = loginService.getNaverAccessToken(code,state);
        String oauthId = loginService.getNaverInfo(oauthTokenDto);

        KljResponse<String> buildWith = null;
        buildWith = KljResponse.create().succeed().buildWith(oauthId);

        return buildWith;
    }
}
