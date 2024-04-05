package klj.project.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import klj.project.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    public void sendMail(User receiveUser, String htmlString, String htmlTitleString){
        String receiverNickName = receiveUser.getNickName();
        String receiverEmail = receiveUser.getTrainer().getEmail();
        htmlString = "<html >" +
                "<body>" +
                "<div style=\"margin:100px;\">" +
                "<h1> 안녕하세요.</h1>" +
                "<h1> 개발자를 위한 면접 준비 플랫폼 SAVIEW 입니다.</h1>" +
                "<br>" +
                "<p> 아래 코드를 회원가입 창으로 돌아가 입력해주세요.</p>" +
                "<br>" +
                "<div align=\"center\" style=\"border:1px solid black; font-family:verdana;\">" +
                "<h3 style=\"color:blue\"> 회원가입 인증 코드 입니다. </h3>" +
                "<div style=\"font-size:130%\" > "+receiverNickName+"</div>" +
                "</div>" +
                "<br/>" +
                "</div>" +
                "</body>" +
                "</html>";
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(receiverEmail); // 메일 수신자
            mimeMessageHelper.setSubject(htmlTitleString); // 메일 제목
            mimeMessageHelper.setText(htmlString, true); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}
