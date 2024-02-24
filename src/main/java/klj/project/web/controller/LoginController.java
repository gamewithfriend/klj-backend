package klj.project.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class LoginController {
    @GetMapping("/login/naver/callback")
    public void getNaverAuth() {
        
        System.out.println("테스트 성공");
    }
}
