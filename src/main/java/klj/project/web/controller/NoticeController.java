package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.notice.Notice;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.service.NoticeService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.notice.NoticeListDeleteRequestDto;
import klj.project.web.dto.notice.NoticeListDto;
import klj.project.web.dto.notice.NoticeListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
@Transactional
public class NoticeController {

    private final NoticeService noticeService;
    private final UserRepository userRepository;


    @Operation(summary = "로그인 유저 알림 리스트", description = "todo: implementation")
    @GetMapping(path = "/notice/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<NoticeListResponseDto>> getUserNoticeList(Authentication authentication) {
        try {

            User user = (User) authentication.getPrincipal();
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            Long loginUserId = loginUser.getId();
            List<NoticeListDto> userNoticeDtoList = noticeService.getUserNoticeList(loginUserId);
            List<NoticeListResponseDto> noticeListResponseDtoList = noticeService.settingNoticeResponseDto(userNoticeDtoList);

            return KljResponse.create()
                    .succeed()
                    .buildWith(noticeListResponseDtoList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "로그인 유저 알림 삭제 ", description = "todo: implementation")
    @DeleteMapping(path = "/notice/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<Long>> deleteUserNotice(@RequestParam(required = false) List<Long> noticeList) {
        try {

            noticeService.deleteNotice(noticeList);

            return KljResponse.create()
                    .succeed()
                    .buildWith(noticeList);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }
    @Operation(summary = "유저 알림 읽기처리", description = "User read Notice ")
    @PutMapping(path = "/notice/user", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<Long> readAlarm(Authentication authentication) {
        try {

            User user = (User) authentication.getPrincipal();
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            Long loginUserId = loginUser.getId();
            noticeService.readNotice(loginUserId);

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
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            Long loginUserId = loginUser.getId();
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
