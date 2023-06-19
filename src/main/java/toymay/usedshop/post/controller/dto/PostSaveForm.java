package toymay.usedshop.post.controller.dto;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class PostSaveForm {

    private List<MultipartFile> files;
    private String text;

    private String name;
    private int registrationPrice;
}