package toymay.usedshop.common.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import toymay.usedshop.common.file.FileDto;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileManager {

    private String fileDir;

    public FileManager(@Value("${file.dir}") String fileDir) {
        this.fileDir = fileDir;
    }

    public String getFullPath(String originalFileName) {
        return fileDir + originalFileName;
    }

    public FileDto storeImageFile(MultipartFile multipartFile) throws IOException {
        String originalFileName = multipartFile.getOriginalFilename();
        String storeFileName = UUID.randomUUID().toString();
        String extension = extractExtension(originalFileName);

        multipartFile.transferTo(new File(getFullPath(storeFileName + "." + extension)));

        return new FileDto(extractFileName(originalFileName), storeFileName, extension);
    }

    public List<FileDto> storeImageFiles(List<MultipartFile> multipartFiles) throws IOException {
        List<FileDto> fileDTOS = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            fileDTOS.add(storeImageFile(multipartFile));
        }
        return fileDTOS;
    }


    private String extractExtension(String filePath) {
        return filePath.substring(filePath.lastIndexOf(".") + 1);
    }

    private String extractFileName(String filePath) {
        return filePath.substring(0, filePath.lastIndexOf("."));
    }

    public void deleteFile(String storeFileName, String extension) {
        File storedFile = new File(getFullPath(storeFileName + "." + extension));

        if (storedFile.exists()) {
            storedFile.delete();
        }
    }
}
