package klj.project.web.controller;

import klj.project.service.CodeService;
import klj.project.service.GymService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.code.CodeRequestDto;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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

    @PostMapping (path="/search/trainer" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<GymLocationDto>>getTrainer(@RequestBody TrainerRequestDto trainerRequestDto){
        try {

            List<String> category = trainerRequestDto.getCategory();
            String trainingArea = trainerRequestDto.getTrainingArea();
            int personCnt = trainerRequestDto.getPersonCnt();
            String trainingTime = trainerRequestDto.getTrainingTime();

//            List<String> categoryList = new ArrayList<>();
//            for(int i = 0 ; i < category.length ; i++){
//                categoryList.add(category[i]);
//            }

            log.info("===================== categoryList" + category);

            List<GymLocationDto> gymList = gymService.getTrainerList(category, trainingArea, personCnt, trainingTime);

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
