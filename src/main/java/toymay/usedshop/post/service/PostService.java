package toymay.usedshop.post.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import toymay.usedshop.common.file.FileManager;
import toymay.usedshop.member.controller.dto.LoginMemberDto;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.repository.MemberRepository;
import toymay.usedshop.post.entity.Post;
import toymay.usedshop.post.entity.PostFile;
import toymay.usedshop.post.repository.PostRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final MemberRepository memberRepository;
    private final FileManager fileManager;

    @Transactional
    public Long writePost(PostDto dto) throws Exception {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid member id"));
        Post post = new Post(member, dto.getText());

        dto.getFileDTOS().forEach(file -> post.addPostFile(
                new PostFile(file.getUploadFileName(), file.getStoreFileName(), file.getExtension())));

        postRepository.save(post);
        //test
        member.addPost(post);

        return post.getId();
    }

    @Transactional
    public List<PostIdDto> getPostListByIdDesc2() {
        List<Post> posts = postRepository.findAllByOrderByIdDesc();
        List<PostIdDto> postIdDtos = PostIdDto.postIdDtos(posts);
        return postIdDtos;
    }

    @Transactional
    public void modifyPostText(Long postId, String modifiedText) {
        Post post = postRepository.findById(postId).orElseThrow();
        post.changeText(modifiedText);
    }

    @Transactional
    public void deletePost(Long postId) {
        Post findPost = postRepository.findById(postId).orElseThrow();
        deletePostFiles(findPost);
        postRepository.deleteById(postId);
    }

    private void deletePostFiles(Post post) {
        post.getPostFiles().forEach((postFile) ->
                fileManager.deleteFile(postFile.getStoreFileName(), postFile.getExtension()));
    }

    @Transactional
    public PostDto getPostDto(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostDto postDto = PostDto.toDto(post);
        return postDto;
    }

    @Transactional
    public PostIdDto getPostIdDto(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow();
        PostIdDto postIdDto = PostIdDto.toDto(post);
        return postIdDto;
    }

    @Transactional
    public LoginMemberDto loginMemberDtoByMemberId(Long memberId) {
        Member member = memberRepository.findById(memberId).orElseThrow();
        String nickname = member.getNickname();
        String imageName = member.getMemberImage().getOriginalStoreFileName();
        LoginMemberDto loginMemberDto = new LoginMemberDto(memberId, nickname, imageName);
        return loginMemberDto;
    }
}
