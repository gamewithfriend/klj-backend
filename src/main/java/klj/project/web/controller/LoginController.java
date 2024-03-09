package klj.project.web.controller;

import klj.project.domain.user.User;
import klj.project.jwt.JwtFilter;
import klj.project.service.LoginService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.login.jwt.TokenDto;
import klj.project.web.dto.login.oauth.NaverOauthRequestDto;
import klj.project.web.dto.login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login/callback/naver")
    @CrossOrigin("http://localhost:3000")
    public KljResponse<TokenDto> getNaverAuth(@RequestBody NaverOauthRequestDto naverOauthRequestDto) {

            try {
                String code =naverOauthRequestDto.getCode();
                String state =naverOauthRequestDto.getState();

                // 네이버 AccessToken 받아오기
                OauthTokenDto oauthTokenDto = loginService.getNaverAccessToken(code,state);
                // 네이버 AccessToken으로 네이버 ID값 (순수 pk값 ID 이메일주소 아님) 받아오기
                String oauthId = loginService.getNaverInfo(oauthTokenDto);
                // 네이버 로그인 로직 타기
                User user = loginService.userNaverLogin(oauthId);
                // 내부 JwtToken 발급
                TokenDto tokenDto = loginService.authorize(user);

                HttpHeaders httpHeaders = new HttpHeaders();
                httpHeaders.add(JwtFilter.AUTHORIZATION_HEADER, "Bearer " + tokenDto.getAccessToken());

                return KljResponse
                        .create()
                        .succeed()
                        .buildWith(tokenDto);

            }catch (Exception e){
                log.info(e.toString());
                return KljResponse
                        .create()
                        .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                        .buildWith(null);
            }

    }
}
