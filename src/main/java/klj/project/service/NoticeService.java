package klj.project.service;

import klj.project.domain.notice.Notice;
import klj.project.domain.notice.NoticeType;
import klj.project.domain.user.User;
import klj.project.repository.NoticeQuerydslRepository;
import klj.project.repository.NoticeRepository;
import klj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    private final NoticeQuerydslRepository noticeQuerydslRepository;

    public void sendNoticeInner(User receiveUser, User sendUser, String noticeType){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Notice notice = Notice.createNotice(receiveUser, sendUser, NoticeType.valueOf(noticeType), false, false,currentDateTime);
        noticeRepository.save(notice);
    }

    public List<Notice> getUserNoticeList(Long userId){
        List<Notice> userNoticeList = noticeQuerydslRepository.findByUserIdGetNoticeList(userId);
        return userNoticeList;
    }
    
    public Long getUserUnReadNoticeCount(Long userId){
        Long userUnReadNoticeCount = noticeQuerydslRepository.findByUserIdGetUnReadNoticeCount(userId);
        return userUnReadNoticeCount;
    }

    public void deleteNotice(List<Long> noticeList){
        noticeQuerydslRepository.deleteNotice(noticeList);
    }

    public void readNotice(List<Long> noticeList){
        noticeQuerydslRepository.readNotice(noticeList);
    }

}
