package klj.project.web.controller;

import klj.project.service.CodeService;
import klj.project.service.GymService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.code.CodeRequestDto;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequiredArgsConstructor
@Slf4j
public class GymController {

    private final GymService gymService;
    @GetMapping (path="/search/gym" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<GymLocationDto>>getArea(){
        try {

            List<GymLocationDto> gymList = gymService.getGymList();

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(gymList);

        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);

        }
    }


}
