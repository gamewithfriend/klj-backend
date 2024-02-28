package klj.project.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {


    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    private final WebClient webClient;

    public void getNaverAccessToken(String code, String state){
        URI url = URI.create("https://nid.naver.com/oauth2.0/token");
        String requestBody = "grant_type=authorization_code&client_id=" + naverClientId + "&client_secret=" + naverClientSecret + "&code=" + code + "&state=" + state;

        webClient.post()
                .uri(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .doOnNext(response -> log.info("Naver access token response: {}", response))
                .subscribe();

    }
}
