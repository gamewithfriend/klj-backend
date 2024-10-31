package klj.project.web.controller;

import klj.project.domain.code.Code;
import klj.project.domain.user.Trainer;
import klj.project.repository.trainer.TrainerRepository;
import klj.project.service.GymService;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerDetaiRequestlDto;
import klj.project.web.dto.gym.TrainerDetailResponseDto;
import klj.project.web.dto.gym.TrainerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequiredArgsConstructor
@Slf4j
public class GymController {

    private final GymService gymService;

    private final TrainerRepository trainerRepository;

    @PostMapping (path="/search/trainer" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<List<GymLocationDto>>getTrainer(@RequestBody TrainerRequestDto trainerRequestDto){
        try {

            List<Code> category = new ArrayList<Code>();
            Code tempCode = new Code();

            for(String temp : trainerRequestDto.getCategory()){
                tempCode.setId(temp);
                category.add(tempCode);
            }

            String trainingArea = trainerRequestDto.getTrainingArea();
            long personCnt = trainerRequestDto.getPersonCnt();
            String tempStartTime = trainerRequestDto.getStartTime();
            String tempEndTime = trainerRequestDto.getEndTime();
            boolean paramFlag = trainerRequestDto.isParamFlag();

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalTime startTime = LocalTime.parse(tempStartTime, timeFormatter);
            LocalTime endTime = LocalTime.parse(tempEndTime, timeFormatter);

            List<GymLocationDto> gymList = new ArrayList<>();

            if(paramFlag){
                gymList = gymService.getTrainerList(category, trainingArea, personCnt, startTime, endTime);
            }else{
                gymList = gymService.getTrainerListWithoutParam(trainingArea);
            }

            log.info("===================== gymList : " + gymList.toString());

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

    @PostMapping (path="/search/trainer/detail" , produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<TrainerDetailResponseDto>getTrainerDetail(@RequestBody TrainerDetaiRequestlDto trainerRequestDetailDto){
        try {
            Long trainerId = trainerRequestDetailDto.getTrainerId();
            Trainer trainer = trainerRepository.getReferenceById(trainerId);
            String trainPlace = trainer.getTrainPlace();
            String trainerName = trainer.getTrainerName();
            String trainPlaceName = trainer.getTrainPlaceName();
            log.info("===================== gymList : " );
            TrainerDetailResponseDto trainerDetailResponseDto = new TrainerDetailResponseDto(trainerId,trainPlace,trainPlaceName,trainerName);

            return KljResponse
                    .create()
                    .succeed()
                    .buildWith(trainerDetailResponseDto);

        } catch (Exception e) {
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR, "에러"))
                    .buildWith(null);

        }
    }

}
