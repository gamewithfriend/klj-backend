package klj.project.service;

import klj.project.web.dto.Login.oauth.NaverInfoResponseDto;
import klj.project.web.dto.Login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

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

    public OauthTokenDto getNaverAccessToken(String code, String state){
        URI uri = UriComponentsBuilder.fromUriString("https://nid.naver.com/oauth2.0/token?")
                .queryParam("grant_type", "authorization_code")
                .queryParam("client_id", naverClientId)
                .queryParam("client_secret", naverClientSecret)
                .queryParam("code", code)
                .queryParam("state", state)
                .build()
                .toUri();

        Mono<OauthTokenDto> responseOauthTokenDtoMono = webClient.post()
                .uri(uri)
                .retrieve()
                .bodyToMono(OauthTokenDto.class);

        OauthTokenDto oauthTokenDto = responseOauthTokenDtoMono.block();

        log.info("Naver Access Token Response: {}", oauthTokenDto);
        log.info("Naver Access Token Response: {}", oauthTokenDto.getAccess_token());
        return oauthTokenDto;
    }
    public String getNaverInfo(OauthTokenDto oauthTokenDto){
        String token = oauthTokenDto.getAccess_token();
        String header = oauthTokenDto.getToken_type()+" "+ token;
        String apiURL = "https://openapi.naver.com/v1/nid/me";

        Mono<NaverInfoResponseDto> naverInfoResponseDtoMono = webClient.get()
                .uri(apiURL)
                .header("Authorization",token+header)
                .retrieve()
                .bodyToMono(NaverInfoResponseDto.class);
        NaverInfoResponseDto naverInfoResponseDto= naverInfoResponseDtoMono.block();
        log.info("Naver info response: {}", naverInfoResponseDto.getResponse().getId());
        return naverInfoResponseDto.getResponse().getId();
    }
}
