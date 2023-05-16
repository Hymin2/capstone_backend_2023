package ac.kr.tukorea.capstone.config.util;

import ac.kr.tukorea.capstone.config.Exception.FileIsEmptyException;
import ac.kr.tukorea.capstone.config.Exception.InvalidFileFormatException;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Component
public class ImageComponent {
    public Resource getImage(String imgPath){
        Resource resource = new FileSystemResource(imgPath);

        if(!resource.exists()){
            return null;
        }

        return resource;
    }

    public String uploadImage(MultipartFile multipartFile, String imgPath){
        if(!multipartFile.isEmpty())
            throw new FileIsEmptyException();

        if(!multipartFile.getContentType().startsWith("image"))
            throw new InvalidFileFormatException();

        UUID uuid = UUID.randomUUID();

        File file = new File(imgPath + uuid + "_" + multipartFile.getOriginalFilename());

        try {
            multipartFile.transferTo(file);
        } catch (IOException e){
            throw new RuntimeException();
        }

        return file.getName();
    }
}
