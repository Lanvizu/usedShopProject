package toymay.usedshop.post.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toymay.usedshop.file.FileDto;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.entity.PostFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class PostDto {
    private Long memberId;
    private List<FileDto> fileDTOS;
    private String text;

    public static PostDto toDto(Post post) {
        PostDto postDto = new PostDto(post.getMember().getId(), FileDto.postFileToFileDto(post.getPostFiles()), post.getText());
        return postDto;
    }

    public static List<PostDto> postDtos(List<Post> posts) {
        List<PostDto> postDtos = new ArrayList<>();
        for (Post post : posts){
            PostDto dto = new PostDto(post.getMember().getId(), FileDto.postFileToFileDto(post.getPostFiles())
                    , post.getText());
            postDtos.add(dto);
        }
        return postDtos;
    }
}
