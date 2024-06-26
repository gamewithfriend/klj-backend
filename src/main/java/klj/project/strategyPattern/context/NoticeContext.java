package klj.project.strategyPattern.context;

import io.swagger.v3.oas.annotations.media.Schema;
import klj.project.domain.user.User;
import klj.project.strategyPattern.strategy.StrageyNoticeInner;
import klj.project.strategyPattern.strategy.StrageyNoticeOuter;
import lombok.Setter;

@Setter
public class NoticeContext {

    private StrageyNoticeInner strageyNoticeInner;

    private StrageyNoticeOuter strageyNoticeOuter;


    // 알림 보낸 유저가 관리자 일경우 sendUser에 널주입
    public void sendInnerNotice(User receiveUser, User sendUser, String noticeType){
        this.strageyNoticeInner.sendInnerNotice(receiveUser, sendUser, noticeType);
    }

    public void sendOuterNotice(User receiveUser, String htmlString, String htmlTitleString){
        this.strageyNoticeOuter.sendOuterNotice(receiveUser, htmlString, htmlTitleString);
    }

}
