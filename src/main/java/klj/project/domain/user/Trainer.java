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

    private String trainPlaceDetail;

    private String trainPlacePostcode;

    private String trainPlaceName;

    private String phoneNumber;

    private String email;

    private Boolean trainerApplyFlag;

    private Boolean trainerAcceptFlag;

    @Builder
    public Trainer(User user, String employmentHistoryPeriod, String trainPlace, String trainPlacePostcode, String trainPlaceDetail, String trainPlaceName, String phoneNumber, String email, Boolean trainerApplyFlag, Boolean trainerAcceptFlag) {
        this.user = user;
        this.employmentHistoryPeriod = employmentHistoryPeriod;
        this.trainPlace = trainPlace;
        this.trainPlaceDetail = trainPlaceDetail;
        this.trainPlacePostcode = trainPlacePostcode;
        this.trainPlaceName = trainPlaceName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.trainerApplyFlag = trainerApplyFlag;
        this.trainerAcceptFlag = trainerAcceptFlag;
    }

    public static  Trainer createTrainer (User user, String employmentHistoryPeriod, String trainPlace, String trainPlaceDetail, String trainPlacePostcode, String trainPlaceName, String phoneNumber, String email, Boolean trainerApplyFlag, Boolean trainerAcceptFlag){
        return Trainer.builder()
                .user(user)
                .employmentHistoryPeriod(employmentHistoryPeriod)
                .trainPlace(trainPlace)
                .trainPlaceDetail(trainPlaceDetail)
                .trainPlacePostcode(trainPlacePostcode)
                .trainPlaceName(trainPlaceName)
                .phoneNumber(phoneNumber)
                .email(email)
                .trainerApplyFlag(trainerApplyFlag)
                .trainerAcceptFlag(trainerAcceptFlag)
                .build();
    }

    public Trainer updateTrainerProfile (String employmentHistoryPeriod, String trainPlace, String trainPlaceDetail, String trainPlacePostcode, String trainPlaceName, String phoneNumber, String email){
        this.employmentHistoryPeriod = employmentHistoryPeriod;
        this.trainPlace = trainPlace;
        this.trainPlaceDetail = trainPlaceDetail;
        this.trainPlacePostcode = trainPlacePostcode;
        this.trainPlaceName = trainPlaceName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        return this;
    }




}
