package klj.project.config.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import klj.project.config.interceptor.util.CachedBodyHttpServletRequest;
import klj.project.domain.user.User;
import klj.project.domain.util.Logs;
import klj.project.domain.util.LogsType;
import klj.project.repository.LogsRepository;
import klj.project.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
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
        if (request instanceof HttpServletRequest) {

            // 요청 본문을 캐싱하여 새로운 HttpServletRequest로 전달
            CachedBodyHttpServletRequest cachedBodyHttpServletRequest = new CachedBodyHttpServletRequest(request);

            // 본문을 한 번 읽음 (원하는 처리 수행 가능)
            String body = IOUtils.toString(cachedBodyHttpServletRequest.getReader());


            log.debug("===============================================");
            log.debug("==================== BEGIN ====================");
            log.debug("Request URI ===> " + request.getRequestURI());
            //insert log
            //Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            User user = null;
            /*if(authentication.getPrincipal() != null){
                user = (User) authentication.getPrincipal();
            }*/

            Logs pointLogs = new Logs(user, LogsType.search, "검색데이터",request.getRemoteAddr());

            return true;
        }
        return false;


    }


}
