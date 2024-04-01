package klj.project.domain.user;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trainer_id")
    private Long id;

    @OneToOne(mappedBy = "trainer", fetch = FetchType.LAZY)
    private User user;

    private String employmentHistoryPeriod;

    private String trainPlace;

    private String phoneNumber;

    private Boolean trainerApplyFlag;

    @Builder
    public Trainer(User user, String employmentHistoryPeriod, String trainPlace, String phoneNumber, Boolean trainerApplyFlag) {
        this.user = user;
        this.employmentHistoryPeriod = employmentHistoryPeriod;
        this.trainPlace = trainPlace;
        this.phoneNumber = phoneNumber;
        this.trainerApplyFlag = trainerApplyFlag;
    }

    public static  Trainer createTrainer (User user, String employmentHistoryPeriod, String trainPlace, String phoneNumber, Boolean trainerApplyFlag){
        return Trainer.builder()
                .user(user)
                .employmentHistoryPeriod(employmentHistoryPeriod)
                .trainPlace(trainPlace)
                .phoneNumber(phoneNumber)
                .trainerApplyFlag(trainerApplyFlag)
                .build();
    }




}