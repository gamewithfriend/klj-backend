package klj.project.domain.code;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
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
    private Long codeOrder;

    @CreatedDate
    private LocalDateTime createdDate;

    public Code(String id, String name, String description, Long depth, CodeParent codeParent, Long codeOrder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.depth = depth;
        this.codeParent = codeParent;
        this.codeOrder = codeOrder;
    }

    @Override
    public boolean isNew() {
        return createdDate == null; // (5)
    }
}
