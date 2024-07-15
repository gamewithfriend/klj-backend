package klj.project.service;

import klj.project.domain.code.Code;
import klj.project.repository.GymQuerydslRepository;
import klj.project.web.dto.gym.GymLocationDto;
import klj.project.web.dto.gym.TrainerRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j
public class GymService {

    private final GymQuerydslRepository gymQuerydslRepository;

    public List<GymLocationDto> getGymList(){
        List<GymLocationDto> gymLocationList = gymQuerydslRepository.getGymList();

        return gymLocationList;
    }


    public List<GymLocationDto> getTrainerList(List<String> category, String trainingArea, int personCnt, String trainingTime) {
        List<GymLocationDto> gymLocationList = gymQuerydslRepository.getTrainerList(category, trainingArea, personCnt, trainingTime);

        return gymLocationList;

    }
}
