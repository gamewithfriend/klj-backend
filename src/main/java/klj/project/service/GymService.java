package klj.project.service;

import klj.project.domain.code.Code;
import klj.project.repository.GymQuerydslRepository;
import klj.project.web.dto.gym.GymLocationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
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


    public List<GymLocationDto> getTrainerList(List<Code> category, String trainingArea, long personCnt, LocalTime startTime, LocalTime endTime) {
        List<GymLocationDto> gymLocationList = gymQuerydslRepository.getTrainerList(category, trainingArea, personCnt, startTime, endTime);

        return gymLocationList;

    }

    public List<GymLocationDto> getTrainerListWithoutParam(String trainingArea) {
        List<GymLocationDto> gymLocationList = gymQuerydslRepository.getTrainerList(trainingArea);

        return gymLocationList;
    }
}
