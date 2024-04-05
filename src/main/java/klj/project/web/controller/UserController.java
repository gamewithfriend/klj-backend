package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.user.UserInfoUpdateDto;
import klj.project.web.dto.user.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    @Operation(summary = "로그인 유저 개인정보 확인", description = "todo: implementation")
    @GetMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> userLoginInfo(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            log.info(user.getOauthId());
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();

            UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName());


            return KljResponse.create()
                    .succeed()
                    .buildWith(userDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "로그인 유저 개인정보 업데이트", description = "todo: implementation")
    @PutMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> userInfoUpdate(@RequestBody UserInfoUpdateDto userInfoUpdateDto, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            loginUser.changeNickName(userInfoUpdateDto.getNickName());
            userRepository.save(loginUser);
            UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName());


            return KljResponse.create()
                    .succeed()
                    .buildWith(userDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }


}
