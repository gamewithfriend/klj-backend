package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.notice.Notice;
import klj.project.domain.user.User;
import klj.project.service.LoginService;
import klj.project.service.NoticeService;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class NoticeController {

    private final NoticeService noticeService;
    @Operation(summary = "로그인 유저 알림 리스트", description = "todo: implementation")
    @GetMapping(path = "/notice/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> getUserNoticeList(Authentication authentication) {
        try {

            User user = (User) authentication.getPrincipal();
            Long loginUserId = user.getId();
            List<Notice> userNoticeList = noticeService.getUserNoticeList(loginUserId);

            return KljResponse.create()
                    .succeed()
                    .buildWith(null);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "로그인 유저 안 읽은 알림 수 ", description = "todo: implementation")
    @GetMapping(path = "/notice/count", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Long> getUserNoticeUnReadCount(Authentication authentication) {
        try {

            User user = (User) authentication.getPrincipal();
            Long loginUserId = user.getId();
            Long userUnReadNoticeCount = noticeService.getUserUnReadNoticeCount(loginUserId);

            return KljResponse.create()
                    .succeed()
                    .buildWith(userUnReadNoticeCount);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
}
