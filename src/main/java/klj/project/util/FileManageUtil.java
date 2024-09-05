package klj.project.util;

import klj.project.domain.file.FileGroup;
import klj.project.domain.file.FileType;
import klj.project.domain.file.Files;
import klj.project.repository.FileRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileManageUtil {

    static String os = System.getProperty("os.name").toLowerCase();
    static String FILE_PATH = "";



    public static List<Files> saveFiles(MultipartFile[] flles, FileGroup fileGroup)	throws Exception{

        List<Files> fileList = new ArrayList<Files>();
        String saveFileName = UUID.randomUUID().toString().replace("-", "");
        String savePath = fileGroup.getGroupCategory().toString();

        if (os.contains("win")) {
            FILE_PATH = "C:"+ File.separator+"java"+File.separator+"file";
        } else if (os.contains("linux")) {
            FILE_PATH = "/home/ec2-user/file";
        }
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDateTime = localDateTime.format(formatter);
        for (MultipartFile multipartFile : flles) {
            File saveFile = new File(FILE_PATH+File.separator+savePath+File.separator+formattedDateTime, saveFileName);

            // 실제 파일 저장
            saveFile.mkdirs();
            multipartFile.transferTo(saveFile);

            // 저장정보 file Domain  변경

            Files file = new Files(FileType.jpg
                    ,multipartFile.getOriginalFilename()
                    ,saveFileName
                    ,FILE_PATH+File.separator+savePath+File.separator+formattedDateTime+File.separator+saveFileName
                    ,multipartFile.getSize()
                    ,localDateTime
                    ,fileGroup);
            fileList.add(file);
        }

        return fileList;
    }

    public static List<Files> deleteFiles(List<Files> files)	throws Exception{

        if (os.contains("win")) {
            FILE_PATH = "C:"+ File.separator+"java"+File.separator+"file";
        } else if (os.contains("linux")) {
            FILE_PATH = "/home/ec2-user/file";
        }

        for (Files  fileDomain : files) {
            File file=new File(fileDomain.getFilePath());
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
