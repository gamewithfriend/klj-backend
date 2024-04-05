package klj.project.strategyPattern.strategy;

import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.service.NoticeService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SendInnerNoticeAdmin implements StrageyNoticeInner{

    private final NoticeService noticeService;

    private final UserRepository userRepository;

    @Override
    public void sendInnerNotice(User receiveUser, User sendUser, String noticeType){

        sendUser = userRepository.getReferenceById(9L);
        noticeService.sendNoticeInner(receiveUser, sendUser, noticeType);

    }
}
