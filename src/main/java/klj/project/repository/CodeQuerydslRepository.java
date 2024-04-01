package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.code.QCode;
import klj.project.web.dto.code.CodeDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CodeQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<CodeDto> findByCodeId (String codeParentId){

        List<CodeDto> codeList =  queryFactory
                .select(Projections.fields(CodeDto.class,
                        QCode.code.name,
                        QCode.code.id
                )).from(QCode.code)
                .where(QCode.code.codeParent.id.eq(codeParentId))
                .fetch();
        return codeList;
    }

}
