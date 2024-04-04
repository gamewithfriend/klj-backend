package klj.project.domain.notice;

import jakarta.persistence.*;
import klj.project.domain.user.Authority;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receive_user_id")
    private User receiveUser;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "send_user_id")
    private User sendUser;

    @Enumerated(EnumType.STRING)
    private NoticeType noticeType;

    private boolean readFlag;

    private boolean deleteFlag;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Notice(User receiveUser, User sendUser, NoticeType noticeType, Boolean readFlag, Boolean deleteFlag, LocalDateTime createdDate) {
        this.receiveUser = receiveUser;
        this.sendUser = sendUser;
        this.noticeType = noticeType;
        this.readFlag = readFlag;
        this.deleteFlag = deleteFlag;
        this.createdDate = createdDate;
    }

    public static  Notice createNotice (User receiveUser, User sendUser, NoticeType noticeType, Boolean readFlag, Boolean deleteFlag, LocalDateTime createdDate){
        return Notice.builder()
                .receiveUser(receiveUser)
                .sendUser(sendUser)
                .noticeType(noticeType)
                .readFlag(readFlag)
                .deleteFlag(deleteFlag)
                .createdDate(createdDate)
                .build();
    }
}
