package klj.project.strategyPattern.strategy;

import klj.project.domain.user.User;

public interface StrageyNoticeOuter {
    void sendOuterNotice(User receiveUser, String htmlString, String htmlTitleString);
}
