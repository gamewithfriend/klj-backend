package klj.project.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import klj.project.domain.user.Authority;
import klj.project.domain.user.OauthType;
import klj.project.domain.user.User;
import klj.project.jwt.CustomAuthority;
import klj.project.jwt.TokenProvider;
import klj.project.repository.UserQuerydslRepository;
import klj.project.repository.UserRepository;
import klj.project.web.dto.login.jwt.TokenDto;
import klj.project.web.dto.login.oauth.IdTokenDto;
import klj.project.web.dto.login.oauth.NaverInfoResponseDto;
import klj.project.web.dto.login.oauth.OauthTokenDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;

@RequiredArgsConstructor
@Service
@Slf4j
public class LoginService {


    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String naverClientId;

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String kakaoClientId;

    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String naverClientSecret;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String kakaoRedirectUri;

    private final WebClient webClient;

    private final UserQuerydslRepository userQuerydslRepository;

    private final UserRepository userRepository;

    private final TokenProvider tokenProvider;


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

    public User userNaverLogin(String oauthId){
        User user = userQuerydslRepository.findByOauthIdAndOauthType(oauthId, OauthType.naver);

        if(user == null){
            long count = userRepository.count();
            User saveUser = User.createUser(oauthId, OauthType.naver, Authority.user, "피트니스새싹" + count);
            userRepository.save(saveUser);
            user = saveUser;
        }

        log.info("user info response: {}", user);
        return user;
    }

    public TokenDto authorize(User user){

        Collection<CustomAuthority> authorities = new ArrayList<>();
        authorities.add(new CustomAuthority(user.getAuthority().name()));

        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getOauthId(), "", authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.createToken(authentication);
        log.info("jwt: {}", jwt);
        return new TokenDto(jwt);
    }

    public OauthTokenDto getKakaoAccessToken(String code, String state){
        URI uri = UriComponentsBuilder.fromUriString("https://kauth.kakao.com/oauth/token?grant_type=authorization_code")
                .queryParam("client_id", kakaoClientId)
                .queryParam("redirect_uri", kakaoRedirectUri)
                .queryParam("code", code)
                .build()
                .toUri();

        Mono<OauthTokenDto> responseOauthTokenDtoMono = webClient.post()
                .uri(uri)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(OauthTokenDto.class);

        OauthTokenDto oauthTokenDto = responseOauthTokenDtoMono.block();

        log.info("Kakao Access Token Response: {}", oauthTokenDto);
        log.info("Kakao Access Token Response: {}", oauthTokenDto.getAccess_token().toString());
        return oauthTokenDto;
    }

    public String getKakaoInfo(OauthTokenDto oauthTokenDto) throws IOException {
        // JWT 디코딩
        String[] jwtParts = oauthTokenDto.getId_token().split("\\.");
        String payload = jwtParts[1];

        // Base64 디코딩
        byte[] decodedBytes = Base64.getDecoder().decode(payload);
        String decodedPayload = new String(decodedBytes);
        
        // IdTokenDto로 변환
        ObjectMapper objectMapper = new ObjectMapper();
        IdTokenDto idTokenDto =  objectMapper.readValue(decodedPayload, IdTokenDto.class);
        

        log.info("Kakao info response: {}", idTokenDto.getSub());
        return idTokenDto.getSub();
    }

    public User userKakaoLogin(String oauthId){
        User user = userQuerydslRepository.findByOauthIdAndOauthType(oauthId, OauthType.kakao);

        if(user == null){
            long count = userRepository.count();
            User saveUser = User.createUser(oauthId, OauthType.kakao, Authority.user, "피트니스새싹" + count);
            userRepository.save(saveUser);
            user = saveUser;
        }

        log.info("user info response: {}", user);
        return user;
    }

}
