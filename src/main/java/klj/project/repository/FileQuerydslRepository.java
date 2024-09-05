package klj.project.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import klj.project.domain.file.Files;
import klj.project.web.dto.gym.GymLocationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static klj.project.domain.file.QFiles.files;
import static klj.project.domain.file.QFileGroup.fileGroup;

@Repository
@RequiredArgsConstructor
public class FileQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Files> getFileListByFileGroupId (Long fileGroupId){

        List<Files> FilesList = queryFactory
                .select(Projections.fields(Files.class,
                        files.id,
                        files.fileGroup,
                        files.fileSize,
                        files.fileType,
                        files.originalName,
                        files.storedName,
                        files.uploadDate
                ))
                .where(fileGroup.id.eq(fileGroupId))
                .from(files)
                .fetch();
        return FilesList;
    }
}
