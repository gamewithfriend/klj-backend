package klj.project.domain.file;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class FileGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "group_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileCategory groupCategory;

    private String description;

    @CreatedDate
    private LocalDateTime createDate;

    @Builder
    public FileGroup(FileCategory groupCategory, String description, LocalDateTime createDate) {
        this.groupCategory = groupCategory;
        this.description = description;
        this.createDate = createDate;
    }

    public static FileGroup createFileGroup (FileCategory groupCategory, String description, LocalDateTime createDate){
        return FileGroup.builder()
                .groupCategory(groupCategory)
                .description(description)
                .createDate(createDate)
                .build();
    }
}
