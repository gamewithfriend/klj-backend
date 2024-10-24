package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.code.Code;
import klj.project.domain.user.*;
import klj.project.web.dto.gym.GymLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

import static klj.project.domain.code.QCode.code;
import static klj.project.domain.user.QTrainer.*;
import static klj.project.domain.user.QTrainerCategory.*;

@Repository
@RequiredArgsConstructor
public class GymQuerydslRepository {

    private final JPAQueryFactory queryFactory;
    private BooleanExpression categoryIn(List<String> category){
        if(category.size() > 0){
            return QTrainerCategoryId.trainerCategoryId.trainerCategoryCode.id.in(category);
        }
        return null;
    }

    private BooleanExpression memberNumberLoe(int personCnt){
        if(personCnt > 0){
            return (QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainerMemberNumber.loe(personCnt));
        }
        return null;
    }

    private BooleanExpression trainingAreaContains(String trainingArea){
        if(trainingArea.equals("")){
            return (trainer.trainPlace.contains(trainingArea));
        }
        return null;
    }


    public List<GymLocationDto> getGymList (){

        List<GymLocationDto> gymList = queryFactory
                .select(Projections.fields(GymLocationDto.class,
                        trainer.trainPlace.as("address"),
                        trainer.trainPlaceName.as("gymName"),
                        trainer.trainerName.as("trainerName"),
                        trainer.id.as("trainerId"),
                        QUser.user.id.as("userId")
                        ))
                .where(trainer.trainerAcceptFlag.eq(true))
                .join(QUser.user).on(QUser.user.trainer.id.eq(trainer.id))
                .from(trainer)
                .fetch();
        return gymList;
    }


    public List<GymLocationDto> getTrainerList(List<Code> category, String trainingArea, long personCnt, LocalTime startTime, LocalTime endTime) {

        QTrainerTime trainerTime = QTrainerTime.trainerTime;
        BooleanExpression timeCondition = trainerTime.trainerTimeId.trainerTime.between(startTime, endTime);

        List<String> areaName = queryFactory
                .select(code.name)
                .from(code)
                .where(code.id.eq(trainingArea.substring(0,8)))
                .fetch();

        List<String> regionName = queryFactory
                .select(code.name)
                .from(code)
                .where(code.id.eq(trainingArea))
                .fetch();

        List<Long> trainerIdList = queryFactory.
                selectDistinct(trainer.id.as("trainerId"))
                    .from(trainer)
                .join(trainerCategory).on(trainer.id.eq(trainerCategory.trainerCategoryId.trainer.id))
                .join(QTrainerTime.trainerTime).on(trainer.id.eq(QTrainerTime.trainerTime.trainerTimeId.trainerId))
                .join(QTrainerMemberNumber.trainerMemberNumber).on(trainer.id.eq(QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainer.id))
                .where(
                        !category.isEmpty() ? trainerCategory.trainerCategoryId.trainerCategoryCode.in(category) : null,
                        trainer.trainPlace.contains(areaName.get(0) + " " + regionName.get(0)),
                        QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainerMemberNumber.loe(personCnt),
                        timeCondition
                )
                .fetch();

        System.out.println("==============trainerIdList : " + trainerIdList);

        List<GymLocationDto> gymList = queryFactory
                .select(Projections.fields(GymLocationDto.class,
                        trainer.trainPlace.as("address"),
                        trainer.trainPlaceName.as("gymName"),
                        trainer.trainerName.as("trainerName"),
                        trainer.id.as("trainerId"),
                        QUser.user.id.as("userId")
                ))
                .from(trainer)
                .join(QUser.user).on(QUser.user.trainer.id.eq(trainer.id))
                .where(trainer.id.in(trainerIdList)
                )
                .fetch();


        return gymList;

    }

    public List<GymLocationDto> getTrainerList(String trainingArea) {
        List<String> areaName = queryFactory
                .select(code.name)
                .from(code)
                .where(code.id.eq(trainingArea.substring(0,8)))
                .fetch();

        List<String> regionName = queryFactory
                .select(code.name)
                .from(code)
                .where(code.id.eq(trainingArea))
                .fetch();

        List<Long> trainerIdList = queryFactory.
                selectDistinct(trainer.id.as("trainerId"))
                .from(trainer)
                .join(trainerCategory).on(trainer.id.eq(trainerCategory.trainerCategoryId.trainer.id))
                .join(QTrainerTime.trainerTime).on(trainer.id.eq(QTrainerTime.trainerTime.trainerTimeId.trainerId))
                .join(QTrainerMemberNumber.trainerMemberNumber).on(trainer.id.eq(QTrainerMemberNumber.trainerMemberNumber.trainerMemberNumberId.trainer.id))
                .where(
                        trainer.trainPlace.contains(areaName.get(0) + " " + regionName.get(0))
                )
                .fetch();

        System.out.println("==============trainerIdList : " + trainerIdList);

        List<GymLocationDto> gymList = queryFactory
                .select(Projections.fields(GymLocationDto.class,
                        trainer.trainPlace.as("address"),
                        trainer.trainPlaceName.as("gymName"),
                        trainer.trainerName.as("trainerName"),
                        trainer.id.as("trainerId"),
                        QUser.user.id.as("userId")
                ))
                .from(trainer)
                .join(QUser.user).on(QUser.user.trainer.id.eq(trainer.id))
                .where(trainer.id.in(trainerIdList)
                )
                .fetch();


        return gymList;

    }
}
