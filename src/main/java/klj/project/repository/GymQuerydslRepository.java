package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.user.QTrainer;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.gym.GymLocationDto;
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
                        trainer.trainPlace.as("adrress"),
                        trainer.trainPlaceDetail.as("gymName")
                        )).from(trainer)
                .fetch();
        return gymList;
    }
}
