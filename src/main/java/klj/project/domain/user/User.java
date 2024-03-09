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

    private OauthType oauthType;

    private Authority authority;

    private String nickName;

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
}
