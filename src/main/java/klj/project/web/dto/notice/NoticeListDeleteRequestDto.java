package klj.project.web.dto.notice;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;

import java.util.List;

@Getter
@JsonAutoDetect
public class NoticeListDeleteRequestDto {

    private List<Long> noticeList;
    private String id;
}
