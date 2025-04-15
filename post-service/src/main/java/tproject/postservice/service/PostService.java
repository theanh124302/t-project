package tproject.postservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tproject.postservice.dto.request.CreatePostRequestDTO;
import tproject.postservice.dto.response.CreatedLightPostResponseDTO;
import tproject.postservice.dto.response.CreatedMassivePostResponseDTO;
import tproject.postservice.entity.FileEntity;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.FileProcessStatus;
import tproject.postservice.enumerates.PostStatus;
import tproject.postservice.repository.FileRepository;
import tproject.postservice.repository.PostRepository;
import tproject.postservice.util.StorageUtils;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.exception.base.BaseException;
import tproject.tcommon.response.message.ResponseMessage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    private final FileRepository fileRepository;

    private final StorageUtils storageUtils;

    private static final String BUCKET_NAME = "taprojectbucket";

    @Transactional
    public CreatedLightPostResponseDTO createLightPost(CreatePostRequestDTO request, Long userId, MultipartFile uploadMedia) {

        PostEntity postEntity = initPostEntity(request, userId);

        if (uploadMedia != null) {
            try {
                File file = convertMultiPartToFile(uploadMedia);
                String fileName = uploadMedia.getOriginalFilename();
                if (fileName == null) {
                    throw new BaseException(ResponseMessage.ERROR, ResponseStatus.ERROR);
                }
                String fileUrl = storageUtils.uploadFile(BUCKET_NAME, fileName,file);

                FileEntity fileEntity = FileEntity.builder()
                        .postId(postEntity.getId())
                        .fileType(storageUtils.getFileType(fileName))
                        .fileName(fileName)
                        .fileUrl(fileUrl)
                        .preSignedUrl(storageUtils.genPreSignedUrl(BUCKET_NAME, fileName))
                        .status(FileProcessStatus.SUCCESS)
                        .hidden(false)
                        .build();

                fileRepository.save(fileEntity);

                return CreatedLightPostResponseDTO.builder()
                        .postId(postEntity.getId())
                        .status(postEntity.getStatus().name())
                        .fileUrl(fileUrl)
                        .build();


            } catch (IOException e) {
                throw new BaseException(ResponseMessage.ERROR, ResponseStatus.ERROR);
            }
        }

        return CreatedLightPostResponseDTO.builder()
                .postId(postEntity.getId())
                .status(postEntity.getStatus().name())
                .build();
    }

    @Transactional
    public CreatedMassivePostResponseDTO createMassivePost(CreatePostRequestDTO request, Long userId) {

        PostEntity postEntity = initPostEntity(request, userId);

        String preSignedUrl = storageUtils.genPreSignedUrl(BUCKET_NAME, UUID.randomUUID().toString());

        return CreatedMassivePostResponseDTO.builder()
                .postId(postEntity.getId())
                .status(postEntity.getStatus().name())
                .preSignedUrl(preSignedUrl)
                .build();

    }

    private PostEntity initPostEntity(CreatePostRequestDTO request, Long userId) {

        PostEntity postEntity = PostEntity.builder()
                .userId(userId)
                .status(PostStatus.SUCCESS)
                .hidden(false)
                .content(request.getContent())
                .visibility(request.getVisibility())
                .build();

        return postRepository.save(postEntity);

    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

}
