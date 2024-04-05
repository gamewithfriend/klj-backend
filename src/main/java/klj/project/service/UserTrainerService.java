package klj.project.service;

import klj.project.domain.user.Trainer;
import klj.project.domain.user.User;
import klj.project.repository.UserRepository;
import klj.project.repository.UserTrainerRepository;
import klj.project.strategyPattern.context.NoticeContext;
import klj.project.strategyPattern.strategy.SendInnerNoticeAdmin;
import klj.project.strategyPattern.strategy.SendOuterNoticeEmail;
import klj.project.web.dto.user.UserTrainerAppplyDto;
import klj.project.web.dto.user.UserTrainerResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Slf4j
@Transactional
public class UserTrainerService {

    private final UserTrainerRepository userTrainerRepository;
    private final UserRepository userRepository;
    private final NoticeService noticeService;
    private final EmailService emailService;

    public UserTrainerResponseDto userTrainerApply(User loginUser, UserTrainerAppplyDto userTrainerApplyDto){
        String employmentHistoryPeriod = userTrainerApplyDto.getEmploymentHistoryPeriod();
        String trainPlace = userTrainerApplyDto.getTrainPlace();
        String phoneNumber = userTrainerApplyDto.getPhoneNumber();
        String email = userTrainerApplyDto.getEmail();

        Trainer trainer =null;
        if(loginUser.getTrainer() == null){ //트레이너 신청을 한번도 신청하지않았다면
            trainer = Trainer.createTrainer(loginUser, employmentHistoryPeriod, trainPlace, phoneNumber, email,true);
            userTrainerRepository.save(trainer);
        }else { //트레이너 신청을 신청했다면
            trainer = loginUser.getTrainer();
            trainer.updateTrainerProfile(employmentHistoryPeriod, trainPlace, phoneNumber, email);
            userTrainerRepository.save(trainer);
        }

        //UserTrainerResponseDto 변환과정
        UserTrainerResponseDto userTrainerResponseDto = new UserTrainerResponseDto(trainer.getId(), trainer.getEmploymentHistoryPeriod(), trainer.getPhoneNumber(), trainer.getEmail(), trainer.getTrainPlace(), trainer.getTrainerApplyFlag());

        // 유저 트레이너 변환 과정
        loginUser.applyTrainer(trainer);
        userRepository.save(loginUser);

        // 알림 전송 콘텍스트 생성
        NoticeContext noticeContext = new NoticeContext();

        // 관리자 전송 알림
        noticeContext.setStrageyNoticeInner(new SendInnerNoticeAdmin(noticeService,userRepository));
        noticeContext.sendInnerNotice(loginUser, null, "notice01");

        // 이메일 발송
        String htmlString = ""; // htmltemplate(이메일 본문 내용)
        String htmlTitleString = "klj-project "; // 이메일 발송 제목

        noticeContext.setStrageyNoticeOuter(new SendOuterNoticeEmail(emailService));
        noticeContext.sendOuterNotice(loginUser, htmlString, htmlTitleString);
        
        return userTrainerResponseDto;
    }

}
