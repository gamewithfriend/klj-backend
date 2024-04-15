package klj.project.service;

import klj.project.domain.notice.Notice;
import klj.project.domain.notice.NoticeType;
import klj.project.domain.user.User;
import klj.project.repository.NoticeQuerydslRepository;
import klj.project.repository.NoticeRepository;
import klj.project.repository.UserRepository;
import klj.project.web.dto.notice.NoticeListDto;
import klj.project.web.dto.notice.NoticeListResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    private final NoticeQuerydslRepository noticeQuerydslRepository;

    public void sendNoticeInner(User receiveUser, User sendUser, String noticeType){
        LocalDateTime currentDateTime = LocalDateTime.now(ZoneId.of("Asia/Seoul"));
        Notice notice = Notice.createNotice(receiveUser, sendUser, NoticeType.valueOf(noticeType), false, false,currentDateTime);
        noticeRepository.save(notice);
    }

    public List<NoticeListDto> getUserNoticeList(Long userId){
        List<NoticeListDto> userNoticeList = noticeQuerydslRepository.findByUserIdGetNoticeList(userId);
        return userNoticeList;
    }

    public List<NoticeListResponseDto> settingNoticeResponseDto(List<NoticeListDto> userNoticeDtoList){
        List<NoticeListResponseDto> noticeListResponseDtoList = new ArrayList<>();
        for (int i=0; i<userNoticeDtoList.size(); i++){
            NoticeListResponseDto noticeListResponseDto = new NoticeListResponseDto(userNoticeDtoList.get(i).getId()
                    , userNoticeDtoList.get(i).getReceiverNickName()
                    , userNoticeDtoList.get(i).getSenderNickName()
                    , userNoticeDtoList.get(i).getName()
                    , userNoticeDtoList.get(i).getReceiverNickName() + userNoticeDtoList.get(i).getDescription()
                    , userNoticeDtoList.get(i).getCreatedDate().format(
                        DateTimeFormatter.ofPattern("MM월dd일HH시mm분")
                    )
                    , userNoticeDtoList.get(i).getReceiverId()
                    , userNoticeDtoList.get(i).getSenderId()
            );
            noticeListResponseDtoList.add(noticeListResponseDto);
        }
        return noticeListResponseDtoList;
    }
    
    public Long getUserUnReadNoticeCount(Long userId){
        Long userUnReadNoticeCount = noticeQuerydslRepository.findByUserIdGetUnReadNoticeCount(userId);
        return userUnReadNoticeCount;
    }

    public void deleteNotice(List<Long> noticeList){
        noticeQuerydslRepository.deleteNotice(noticeList);
    }

    public void readNotice(Long userId){
        List<Long> unReadNoticeList = noticeQuerydslRepository.findByUserIdGetUnReadNoticeList(userId);
        noticeQuerydslRepository.readNotice(unReadNoticeList);
    }

}
