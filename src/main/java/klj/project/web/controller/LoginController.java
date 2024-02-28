package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Parameter;
import klj.project.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/login")
public class LoginController {

    private final LoginService loginService;

    @Parameter(name = "code")
    @PostMapping("/callback/naver")
    @CrossOrigin("http://localhost:3000")
    public String getNaverAuth(@RequestParam(value = "code") String code,@RequestParam(value = "state") String state) {
        String url = "";
        loginService.getNaverAccessToken(code,state);
        return url;
    }
}
