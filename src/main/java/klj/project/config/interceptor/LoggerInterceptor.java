package klj.project.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import klj.project.domain.user.User;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.repository.LogsRepository;
import klj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
@RequiredArgsConstructor
public class LoggerInterceptor implements HandlerInterceptor {

    private final LogsRepository logsRepository;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.debug("===============================================");
        log.debug("==================== BEGIN ====================");
        log.debug("Request URI ===> " + request.getRequestURI());
        //insert log
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if(authentication.getPrincipal() != null){
            user = (User) authentication.getPrincipal();
        }

        Logs pointLogs = new Logs(user, LogsType.search, "검색데이터",request.getRemoteAddr());

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }


}
