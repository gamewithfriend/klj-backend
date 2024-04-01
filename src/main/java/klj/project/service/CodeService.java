package klj.project.service;

import klj.project.domain.code.Code;
import klj.project.domain.user.Authority;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.User;
import klj.project.repository.CodeQuerydslRepository;
import klj.project.web.dto.code.CodeDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CodeService {

    private final CodeQuerydslRepository codeQuerydslRepository;

    public List<CodeDto> getCodeList(String codeId){
        List<CodeDto> codeDtoList = codeQuerydslRepository.findByCodeId(codeId);

        log.info("codeDtoList info response: list", codeDtoList);
        return codeDtoList;
    }


}
