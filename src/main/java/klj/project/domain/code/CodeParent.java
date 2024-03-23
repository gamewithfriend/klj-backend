package klj.project.domain.code;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.domain.Persistable;

import java.time.LocalDateTime;
@Entity
@Getter
@NoArgsConstructor
public class CodeParent implements Persistable<String> {

    @Id
    @Column(name = "codeParent_id")
    private String id;

    private String name;
    private String description;

    @CreatedDate
    private LocalDateTime createdDate;

    public CodeParent(String id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    @Override
    public boolean isNew() {
        return createdDate == null; // (5)
    }

}
