package toymay.usedshop.file;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import toymay.usedshop.member.entity.Member;
import toymay.usedshop.member.entity.MemberImage;
import toymay.usedshop.member.service.MemberDto;
import toymay.usedshop.post.entity.PostFile;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
public class FileDto {

    private String uploadFileName;
    private String storeFileName;
    private String extension;

    public static FileDto toDto(MemberImage memberImage) {
        FileDto dto = new FileDto(memberImage.getUploadFileName(),
                memberImage.getStoreFileName(),
                memberImage.getExtension());

        return dto;
    }

    public static String toMemberImageName(MemberImage memberImage) {
        String origianlStoreName = memberImage.getStoreFileName() + "." + memberImage.getExtension();
        return origianlStoreName;

    }

    public static List<FileDto> postFileToFileDto(List<PostFile> postFiles) {
        List<FileDto> fileDtos = postFiles.stream()
                .map(postFile -> new FileDto(postFile.getUploadFileName(), postFile.getStoreFileName(), postFile.getExtension()))
                .collect(Collectors.toList());
        return fileDtos;
    }
}