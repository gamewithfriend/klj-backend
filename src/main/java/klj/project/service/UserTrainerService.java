package klj.project.service;

import klj.project.domain.user.Authority;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.repository.UserTrainerRepository;
import klj.project.web.dto.user.UserTrainerAppplyDto;
import klj.project.web.dto.user.UserTrainerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserTrainerService {

    private final UserTrainerRepository userTrainerRepository;
    private final UserRepository userRepository;

    public UserTrainerResponseDto userTrainerApply(User loginUser, UserTrainerAppplyDto userTrainerAppplyDto){
        String employmentHistoryPeriod = userTrainerAppplyDto.getEmploymentHistoryPeriod();
        String trainPlace = userTrainerAppplyDto.getTrainPlace();
        String phoneNumber = userTrainerAppplyDto.getPhoneNumber();

        Trainer trainer = Trainer.createTrainer(loginUser, employmentHistoryPeriod, trainPlace, phoneNumber, true);
        userTrainerRepository.save(trainer);
        UserTrainerResponseDto userTrainerResponseDto = new UserTrainerResponseDto(trainer.getId(), trainer.getEmploymentHistoryPeriod(), trainer.getPhoneNumber(), trainer.getTrainPlace(), trainer.getTrainerApplyFlag());
        loginUser.applyTrainer(trainer);
        userRepository.save(loginUser);
        return userTrainerResponseDto;
    }

}
