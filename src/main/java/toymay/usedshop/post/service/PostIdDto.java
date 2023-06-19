package toymay.usedshop.post.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toymay.usedshop.common.Chrono;
import toymay.usedshop.file.FileDto;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.product.entity.Product;
import toymay.usedshop.product.service.ProductDto;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
public class PostIdDto {

    private Long postId;
    private MemberDto memberDto;
    private String memberImageName;
    private List<FileDto> fileDTOS;
    private String text;
    private ProductDto productDto;
    private String modifiedAt;

    public static PostIdDto toDto(Post post) {
        PostIdDto postIdDto = new PostIdDto(post.getId(), MemberDto.toDto(post.getMember()), post.getMember().getMemberImage().getOriginalStoreFileName(),
                FileDto.postFileToFileDto(post.getPostFiles()), post.getText(), ProductDto.toDto(post.getProduct()),
               Chrono.timesAgo(post.getLastModifiedDate()));
        return postIdDto;
    }

    public static List<PostIdDto> postIdDtos(List<Post> posts) {
        List<PostIdDto> postIdDtos = new ArrayList<>();
        for (Post post : posts){
            PostIdDto dto = new PostIdDto(post.getId(), MemberDto.toDto(post.getMember()),
                    FileDto.toMemberImageName(post.getMember().getMemberImage()),
                    FileDto.postFileToFileDto(post.getPostFiles()), post.getText(),
                    ProductDto.toDto(post.getProduct()), Chrono.timesAgo(post.getLastModifiedDate()));
            postIdDtos.add(dto);
        }
        return postIdDtos;
    }
}
