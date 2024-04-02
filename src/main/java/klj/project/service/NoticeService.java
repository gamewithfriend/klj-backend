package klj.project.service;

import klj.project.domain.notice.Notice;
import klj.project.domain.notice.NoticeType;
import klj.project.domain.user.User;
import klj.project.repository.NoticeRepository;
import klj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Service
@Slf4j
public class NoticeService {

    private final NoticeRepository noticeRepository;

    private final UserRepository userRepository;

    public void sendNoticeInner(User receiveUser, User sendUser, String noticeType){
        LocalDateTime currentDateTime = LocalDateTime.now();
        Notice notice = Notice.createNotice(receiveUser, sendUser, NoticeType.valueOf(noticeType), false, currentDateTime);
        noticeRepository.save(notice);
    }
}
