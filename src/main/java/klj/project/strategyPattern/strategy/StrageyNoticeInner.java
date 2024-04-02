package klj.project.strategyPattern.strategy;

import klj.project.domain.user.User;

public interface StrageyNoticeInner {

    void sendInnerNotice(User receiveUser, User sendUser, String noticeType);
}
