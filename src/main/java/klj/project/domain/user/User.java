package klj.project.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String oauthId;

    @Enumerated(EnumType.STRING)
    private OauthType oauthType;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    private String nickName;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Builder
    public User(String oauthId, OauthType oauthType, Authority authority, String nickName) {
        this.oauthId = oauthId;
        this.oauthType = oauthType;
        this.authority = authority;
        this.nickName = nickName;
    }

    public static  User createUser (String oauthId, OauthType oauthType, Authority authority, String nickName){
        return User.builder()
                .oauthId(oauthId)
                .oauthType(oauthType)
                .authority(authority)
                .nickName(nickName)
                .build();
    }

    public User applyTrainer (Trainer trainer){
        this.trainer = trainer;
        return this;
    }

    public User changeNickName (String nickName){
        this.nickName = nickName;
        return this;
    }

}
