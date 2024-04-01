package klj.project.domain.code;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class Code implements Persistable<String> {

    @Id
    @Column(name = "code_id")
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codeParent_id")
    private CodeParent codeParent;

    private String name;
    private String description;
    private Long depth;


    @CreatedDate
    private LocalDateTime createdDate;

    public Code(String id, String name, String description, Long depth, CodeParent codeParent) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.depth = depth;
        this.codeParent = codeParent;
    }

    @Override
    public boolean isNew() {
        return createdDate == null; // (5)
    }
}
