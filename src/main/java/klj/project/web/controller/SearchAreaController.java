package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.service.CodeService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.code.CodeRequestDto;
import klj.project.web.dto.code.RegionCodeRequestDto;
import klj.project.web.dto.gym.GymLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class SearchAreaController {

    private final CodeService codeService;
    @PostMapping(path="/search/area" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<CodeDto>> getArea(@RequestBody CodeRequestDto codeRequestDto){
        try {
            String codeParentId = codeRequestDto.getId();
            List<CodeDto> codeList = codeService.getCodeList(codeParentId);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(codeList);

        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);

        }
    }

    @PostMapping(path="/search/regionCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<CodeDto> getRegionCode(@RequestBody RegionCodeRequestDto reduxRegionDto) {
        try {
            String area = reduxRegionDto.getArea();
            String region = reduxRegionDto.getRegion();
            CodeDto regionCode = codeService.getRegionCode(area, region);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(regionCode);

        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);
        }
    }


}
