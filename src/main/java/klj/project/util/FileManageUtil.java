package klj.project.util;

import jakarta.annotation.PostConstruct;
import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.repository.FileRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileManageUtil {

    private static String FILE_LOCAL_PATH;

    static String os = System.getProperty("os.name").toLowerCase();
    static String FILE_PATH = "";

    @Value("${file.path}")
    private  String fileLocalPath;

    // @PostConstruct를 사용하여 static 변수 초기화
    @PostConstruct
    public void init() {
        FILE_LOCAL_PATH = fileLocalPath;
    }

    public static String getFileLocalPath() {
        return FILE_LOCAL_PATH;
    }

    public static List<Files> saveFiles(MultipartFile[] flles, FileGroup fileGroup, FileType fileType)	throws Exception{
        String localFileType = "";
        List<Files> fileList = new ArrayList<Files>();
        String saveFileName = UUID.randomUUID().toString().replace("-", "");
        String savePath = fileGroup.getGroupCategory().toString();

        if (os.contains("win")) {
            FILE_PATH = FILE_LOCAL_PATH+ File.separator+"java"+File.separator+"file";
            localFileType = "."+fileType;
        } else if (os.contains("linux")) {
            FILE_PATH = "/home/ec2-user/file";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDateTime = localDateTime.format(formatter);
        for (MultipartFile multipartFile : flles) {
            File saveFile = new File(FILE_PATH+File.separator+savePath+File.separator+formattedDateTime, saveFileName+localFileType);

            // 실제 파일 저장
            saveFile.mkdirs();
            multipartFile.transferTo(saveFile);

            // 저장정보 file Domain  변경

            Files file = new Files(fileType
                    ,multipartFile.getOriginalFilename()
                    ,saveFileName+localFileType
                    ,FILE_PATH+File.separator+savePath+File.separator+formattedDateTime+File.separator+saveFileName+localFileType
                    ,multipartFile.getSize()
                    ,localDateTime
                    ,fileGroup);
            fileList.add(file);
        }

        return fileList;
    }

    public static List<Files> deleteFiles(List<Files> files)	throws Exception{
        String localFileType = "";
        if (os.contains("win")) {
            FILE_PATH = "C:"+ File.separator+"java"+File.separator+"file";

        } else if (os.contains("linux")) {
            FILE_PATH = "/home/ec2-user/file";
        }

        for (Files  fileDomain : files) {
            if (os.contains("win")) {
                localFileType = "."+fileDomain.getFileType();
            }
            File file=new File(fileDomain.getFilePath()+localFileType);
            file.delete();
        }

        return files;
    }

   /* public static void downFile(HttpServletResponse response,String fileName) throws Exception{

        if (os.contains("win")) {
            FILE_PATH = "C:"+File.separator+"java"+File.separator+"file";
        } else if (os.contains("linux")) {
            FILE_PATH = "/home/ubuntu/file";
        }
        String downloadPath = FILE_PATH+File.separator+fileName;
        File file = new File(downloadPath);
        FileInputStream in = new FileInputStream(downloadPath);

        fileName = new String(fileName.getBytes("UTF-8"), "8859_1");

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        OutputStream os = response.getOutputStream();

        int length;
        byte[] b = new byte[(int) file.length()];
        while ((length = in.read(b)) > 0) {
            os.write(b, 0, length); // outputSteam의 write 메소드.
        }

        os.flush();

        os.close();
        in.close();

    }*/
}
