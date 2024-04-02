package klj.project.service;

import klj.project.domain.user.Authority;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.repository.UserTrainerRepository;
import klj.project.strategyPattern.context.NoticeContext;
import klj.project.strategyPattern.strategy.sendInnerNoticeAdmin;
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
    private final NoticeService noticeService;
    private final UserRepository userRepository;

    public UserTrainerResponseDto userTrainerApply(User loginUser, UserTrainerAppplyDto userTrainerAppplyDto){
        String employmentHistoryPeriod = userTrainerAppplyDto.getEmploymentHistoryPeriod();
        String trainPlace = userTrainerAppplyDto.getTrainPlace();
        String phoneNumber = userTrainerAppplyDto.getPhoneNumber();

        Trainer trainer = Trainer.createTrainer(loginUser, employmentHistoryPeriod, trainPlace, phoneNumber, true);
        userTrainerRepository.save(trainer);

        //UserTrainerResponseDto 변환과정
        UserTrainerResponseDto userTrainerResponseDto = new UserTrainerResponseDto(trainer.getId(), trainer.getEmploymentHistoryPeriod(), trainer.getPhoneNumber(), trainer.getTrainPlace(), trainer.getTrainerApplyFlag());

        // 유저 트레이너 변환 과정
        loginUser.applyTrainer(trainer);
        userRepository.save(loginUser);

        // 알림 전송
        NoticeContext noticeContext = new NoticeContext();
        noticeContext.setStrageyNoticeInner(new sendInnerNoticeAdmin(noticeService,userRepository));
        noticeContext.sendInnerNotice(loginUser, null, "notice01");

        return userTrainerResponseDto;
    }

}
