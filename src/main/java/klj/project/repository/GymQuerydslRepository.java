package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.user.QTrainer;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static klj.project.domain.user.QTrainer.*;

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

}
