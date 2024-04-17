package klj.project.service;

import klj.project.repository.CodeQuerydslRepository;
import klj.project.repository.GymQuerydslRepository;
import klj.project.web.dto.code.CodeDto;
import klj.project.web.dto.gym.GymLocationDto;
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

}
