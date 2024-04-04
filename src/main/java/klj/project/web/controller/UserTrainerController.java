package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.service.UserTrainerService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.user.UserTrainerAppplyDto;
import klj.project.web.dto.user.UserTrainerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserTrainerController {


    private final UserRepository userRepository;
    private final UserTrainerService userTrainerService;

    @Operation(summary = "유저 Trainer 신청", description = "todo: implementation")
    @PostMapping(path = "/user/trainer/apply", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserTrainerResponseDto> userApplyTrainer(@RequestBody UserTrainerAppplyDto userTrainerAppplyDto, Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            log.info("user.getOauthId(): String", user.getOauthId());

            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            UserTrainerResponseDto userTrainerResponseDto = userTrainerService.userTrainerApply(loginUser, userTrainerAppplyDto);
            log.info("UserTrainerResponseDto: {}", userTrainerResponseDto);
            return KljResponse.create()
                    .succeed()
                    .buildWith(userTrainerResponseDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "유저 Trainer 신청정보", description = "todo: implementation")
    @GetMapping(path = "/user/trainer/applyInfo", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserTrainerResponseDto> userApplyInfo(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            log.info("loginUser.getTrainer(): {}", loginUser.getTrainer());
            if(loginUser.getTrainer() !=null){
                Trainer trainer = loginUser.getTrainer();
                UserTrainerResponseDto userTrainerResponseDto = new UserTrainerResponseDto(trainer.getId(), trainer.getEmploymentHistoryPeriod(), trainer.getPhoneNumber(),trainer.getEmail(), trainer.getTrainPlace(), trainer.getTrainerApplyFlag());
                return KljResponse.create()
                        .succeed()
                        .buildWith(userTrainerResponseDto);
            }else{
                return KljResponse.create()
                        .succeed()
                        .buildWith(null);
            }
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
}
