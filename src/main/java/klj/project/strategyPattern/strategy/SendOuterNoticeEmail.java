package klj.project.strategyPattern.strategy;

import klj.project.domain.user.User;
import klj.project.service.EmailService;
import klj.project.service.NoticeService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SendOuterNoticeEmail implements StrageyNoticeOuter{

    private final EmailService emailService;

    @Override
    public void sendOuterNotice(User receiveUser, String htmlString, String htmlTitleString){
        emailService.sendMail(receiveUser, htmlString, htmlTitleString);
    }
}
