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
@Table(name = "file")
public class Files {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "file_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private FileType fileType;

    private String originalName;

    private String storedName;

    private String filePath;

    private Long fileSize;

    @CreatedDate
    private LocalDateTime uploadDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id")
    private FileGroup fileGroup;


    @Builder
    public Files(FileType fileType, String originalName, String storedName,String filePath, Long fileSize, LocalDateTime uploadDate,FileGroup fileGroup) {
        this.fileType = fileType;
        this.originalName = originalName;
        this.storedName = storedName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.uploadDate = uploadDate;
        this.fileGroup = fileGroup;
    }

    public static Files createFile (FileType fileType, String originalName, String storedName,String filePath, Long fileSize, LocalDateTime uploadDate){
        return Files.builder()
                .fileType(fileType)
                .originalName(originalName)
                .storedName(storedName)
                .filePath(filePath)
                .fileSize(fileSize)
                .uploadDate(uploadDate)
                .build();
    }

}
