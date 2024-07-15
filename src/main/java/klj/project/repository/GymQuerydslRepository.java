package klj.project.repository;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.code.Code;
import klj.project.domain.user.*;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerDto;
import klj.project.web.dto.gym.TrainerRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

import static klj.project.domain.code.QCode.code;
import static klj.project.domain.user.QTrainer.*;
import static klj.project.domain.user.QTrainerCategory.*;
import static klj.project.domain.user.QTrainerCategoryId.*;

@Repository
@RequiredArgsConstructor
public class GymQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<GymLocationDto> getGymList (){

        List<GymLocationDto> gymList = queryFactory
                .select(Projections.fields(GymLocationDto.class,
                        trainer.trainPlace.as("address"),
                        trainer.trainPlaceName.as("gymName"),
                        trainer.trainerName.as("trainerName"),
                        trainer.id.as("trainerId")
                        ))
                .where(trainer.trainerAcceptFlag.eq(true))
                .from(trainer)
                .fetch();
        return gymList;
    }


    public List<GymLocationDto> getTrainerList(List<String> category, String trainingArea, int personCnt, String trainingTime) {

        List<Long> trainerIdList = queryFactory.
                selectDistinct(trainer.id.as("trainerId"))
                    .from(trainer)
                .join(trainerCategory).on(trainer.id.eq(trainerCategory.trainerCategoryId.trainer.id))
                .join(QTrainerTime.trainerTime).on(trainer.id.eq(QTrainerTime.trainerTime.trainerTimeId.trainerId))
                .join(QTrainerMemberNumber.trainerMemberNumber).on(trainer.id.eq(QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainerId))
                .where(QTrainerCategoryId.trainerCategoryId.trainerCategoryCode.id.in(category)
                        ,(QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainerMemberNumber.loe(personCnt))
                        ,(trainer.trainPlace.contains(trainingArea)
                        ))
                .fetch();

        System.out.println("======================= trainerIdList" + trainerIdList);

        List<GymLocationDto> gymList = queryFactory
                .select(Projections.fields(GymLocationDto.class,
                        trainer.trainPlace.as("address"),
                        trainer.trainPlaceName.as("gymName"),
                        trainer.trainerName.as("trainerName"),
                        trainer.id.as("trainerId")
                ))
                .from(trainer)
                .where(trainer.id.in(trainerIdList)
                )
                .fetch();


        return gymList;

    }
}
