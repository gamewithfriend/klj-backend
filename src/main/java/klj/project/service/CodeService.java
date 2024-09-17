package klj.project.service;

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
        
        return codeDtoList;
    }


    public CodeDto getRegionCode(String area, String region) {
        CodeDto regionCode = codeQuerydslRepository.getRegionCode(area, region);

        return regionCode;
    }
}
