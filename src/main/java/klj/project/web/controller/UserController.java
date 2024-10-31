package klj.project.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import klj.project.domain.file.FileCategory;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.domain.user.User;
import klj.project.repository.FileGroupRepository;
import klj.project.repository.FileQuerydslRepository;
import klj.project.repository.FileRepository;
import klj.project.repository.UserRepository;
import klj.project.util.FileManageUtil;
import klj.project.web.dto.Error;
import klj.project.web.dto.KljResponse;
import klj.project.web.dto.gym.TrainerRequestDto;
import klj.project.web.dto.user.UserInfoRequestDto;
import klj.project.web.dto.user.UserInfoResponseDto;
import klj.project.web.dto.user.UserLoginDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserRepository userRepository;

    private final FileRepository fileRepository;

    private final FileQuerydslRepository fileQuerydslRepository;

    private final FileGroupRepository fileGroupRepository;

    @Operation(summary = "로그인 유저 개인정보 확인", description = "todo: implementation")
    @GetMapping(path = "/user/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public KljResponse<UserLoginDto> userLoginInfo(Authentication authentication) {
        try {
            User user = (User) authentication.getPrincipal();
            log.info(user.getOauthId());
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            String userFilePath = "";
            if(loginUser.getFileGroup() !=null){
                FileGroup fileGroup = loginUser.getFileGroup();
                Long fileGroupId = fileGroup.getId();
                Files files = fileRepository.findByFileGroupId(fileGroupId).get();
                userFilePath = files.getFilePath();
            }


            UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName(),userFilePath);
            

            return KljResponse.create()
                    .succeed()
                    .buildWith(userDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "로그인 유저 개인정보 업데이트", description = "todo: implementation")
    @PutMapping(path = "/user/info",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KljResponse<UserLoginDto> userInfoUpdate(@RequestParam("multipartFile") MultipartFile[] multipartFiles, Authentication authentication,  @RequestParam("nickName") String nickName) {
        try {
            User user = (User) authentication.getPrincipal();
            User loginUser = userRepository.findByOauthId(user.getOauthId()).get();
            loginUser.changeNickName(nickName);
            LocalDateTime currentDateTime = LocalDateTime.now();
            if(loginUser.getFileGroup() !=null){
                Long fileGroupId = loginUser.getFileGroup().getId();
                List<Files> files = fileQuerydslRepository.getFileListByFileGroupId(fileGroupId);
                FileManageUtil.deleteFiles(files);
                fileRepository.deleteAll(files);
                FileGroup fileGroup = new FileGroup(FileCategory.profile,"프로필사진",currentDateTime);
                fileGroupRepository.save(fileGroup);
                List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup,FileType.jpg);
                fileRepository.saveAll(filesInsertList);
                loginUser.changeImage(fileGroup);
            }else {
                FileGroup fileGroup = new FileGroup(FileCategory.profile,"프로필사진",currentDateTime);
                fileGroupRepository.save(fileGroup);
                List<Files> filesInsertList = FileManageUtil.saveFiles(multipartFiles, fileGroup,FileType.jpg);
                fileRepository.saveAll(filesInsertList);
                loginUser.changeImage(fileGroup);
            }
            userRepository.save(loginUser);
            UserLoginDto userDto = new UserLoginDto(loginUser.getId(),loginUser.getNickName(),"");

            return KljResponse.create()
                    .succeed()
                    .buildWith(userDto);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }

    @Operation(summary = "유저 개인정보", description = "todo: implementation")
    @PostMapping(path = "/user/info",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public KljResponse<UserInfoResponseDto> userInfo(@RequestBody UserInfoRequestDto userInfoRequestDto) {
        try {

            Long id = userInfoRequestDto.getId();
            User user = userRepository.getReferenceById(id);
            String nickName = user.getNickName();
            Long fileGroupId = user.getFileGroup().getId();

            return KljResponse.create()
                    .succeed()
                    .buildWith(null);
        }catch (Exception e){
            log.info(e.toString());
            return KljResponse
                    .create()
                    .fail(new Error(HttpStatus.INTERNAL_SERVER_ERROR,"에러"))
                    .buildWith(null);
        }
    }


}
