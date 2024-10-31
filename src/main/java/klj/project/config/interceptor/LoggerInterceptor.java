package klj.project.config.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import klj.project.domain.user.User;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.repository.LogsRepository;
import klj.project.repository.UserRepository;
import klj.project.web.dto.code.CodeRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.io.BufferedReader;

@Component
@Slf4j
public class LoggerInterceptor implements HandlerInterceptor {



    private final LogsRepository logsRepository;

    private final UserRepository userRepository;

    public LoggerInterceptor(LogsRepository logsRepository, UserRepository userRepository) {
        this.logsRepository = logsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler ) throws Exception {

        if(request.getQueryString() != null){
            String requestURI = request.getRequestURI();
            String[] requestUriArr = requestURI.split("/");


            String localAddr = request.getLocalAddr();
            String queryString = request.getQueryString();
            String[] queryStringArr = queryString.split("&");

            String queryStringDetailUserId = queryStringArr[queryStringArr.length - 2];
            String[] queryStringDetailUserIdArr = queryStringDetailUserId.split("=");
            String queryStringUserIdValue = queryStringDetailUserIdArr[queryStringDetailUserIdArr.length - 1];

            String queryStringDetailType = queryStringArr[queryStringArr.length - 1];
            String[] queryStringDetailTypeArr = queryStringDetailType.split("=");
            String queryStringTypeValue = queryStringDetailTypeArr[queryStringDetailTypeArr.length - 1];


            LogsType logsType = LogsType.valueOf(queryStringTypeValue);
            User loginUser = userRepository.getReferenceById(Long.parseLong(queryStringUserIdValue));

            Logs logs = new Logs(loginUser,logsType,queryString,localAddr);
            logsRepository.save(logs);
        }


        return true;


    }


}
