package toymay.usedshop.product.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import toymay.usedshop.file.FileDto;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.service.PostDto;
import toymay.usedshop.product.entity.Product;
import toymay.usedshop.product.entity.ProductStatus;

@Getter
@AllArgsConstructor
public class ProductDto {
    private String name;
    private int registrationPrice;
    private Long postId;
    private ProductStatus productStatus;

    public static PostDto toDto(Post post) {
        PostDto postDto = new PostDto(post.getMember().getId(), FileDto.postFileToFileDto(post.getPostFiles()), post.getText());
        return postDto;
    }

    public static ProductDto toDto(Product product) {
        ProductDto productDto = new ProductDto(product.getName(), product.getRegistrationPrice(),
                product.getPost().getId(), product.getStatus());
        return productDto;
    }
}
