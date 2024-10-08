package klj.project.domain.util;

import jakarta.persistence.*;
import klj.project.domain.user.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Logs {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "logs_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private LogsType logsType;

    String description;

    String ipAddress;

    @CreatedDate
    private LocalDateTime createdDate;

    @Builder
    public Logs(User user, LogsType logsType, String description, String ipAddress) {
        this.user = user;
        this.logsType = logsType;
        this.description = description;
        this.ipAddress = ipAddress;
    }

    public static  Logs createLogs (User user, LogsType logsType, String description, String ipAddress){
        return Logs.builder()
                .user(user)
                .logsType(logsType)
                .description(description)
                .ipAddress(ipAddress)
                .build();
    }

}
