package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LoginController {
    @Parameter
    @Operation(summary = "유저 알람 리스트 조회 (readFlag 파라미터로 읽은 안읽은 리스트 구분조회) 총 갯수 포함", description = "Get the User Alarm List and Count")

    @GetMapping("/login/callback/naver")
    @CrossOrigin("http://localhost:3000")
    public void getNaverAuth() {
        
        System.out.println("테스트 성공");
    }
}
