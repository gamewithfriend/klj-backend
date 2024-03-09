package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.user.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {
    @Operation(summary = "개인정보 확인", description = "todo: implementation")
    @GetMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> userLoginDto(Authentication authentication) {
        try {
            String string = authentication.getPrincipal().toString();
            log.info(string);
            //UserLoginDto userDto = new UserLoginDto(null);

            return KljResponse.create()
                    .succeed()
                    .buildWith(null);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse.create().fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러")).buildWith(null);
        }
    }
}
