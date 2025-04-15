package tproject.postservice.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tproject.postservice.dto.request.CreatePostRequestDTO;
import tproject.postservice.dto.response.CreatedLightPostResponseDTO;
import tproject.postservice.entity.FileEntity;
import tproject.postservice.entity.PostEntity;
import tproject.postservice.enumerates.FileProcessStatus;
import tproject.postservice.enumerates.PostStatus;
import tproject.postservice.repository.FileRepository;
import tproject.postservice.repository.PostRepository;
import tproject.postservice.util.StorageUtils;

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

    private final AmazonS3 s3Client;

    private final StorageUtils storageUtils;

    private static final String BUCKET_NAME = "taprojectbucket";

    @Transactional
    public CreatedLightPostResponseDTO createLightPost(CreatePostRequestDTO request, Long userId, MultipartFile uploadMedia) {

        PostEntity postEntity = initPostEntity(request, userId);

        if (uploadMedia != null) {
            try {
                File file = convertMultiPartToFile(uploadMedia);
                String fileName = uploadMedia.getOriginalFilename();
                String fileUrl = storageUtils.uploadPrivateFile(BUCKET_NAME, fileName,file);

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
                throw new RuntimeException(e);
            }
        }


        return CreatedLightPostResponseDTO.builder()
                .postId(postEntity.getId())
                .status(postEntity.getStatus().name())
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


    public void test() {
        try {
            File file = generateHtmlFile("test", "temp.html");
            PutObjectRequest uploadRequest = (new PutObjectRequest("taprojectbucket", UUID.randomUUID().toString(), file)).withCannedAcl(CannedAccessControlList.PublicRead);
            s3Client.putObject(uploadRequest);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String genPreSignedUrl() {

        Date expiration = new Date();
        long expTimeMillis = expiration.getTime() + 3 * 60 * 1000;
        expiration.setTime(expTimeMillis);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest("taprojectbucket", UUID.randomUUID().toString())
                .withMethod(HttpMethod.PUT)
                .withExpiration(expiration);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.CONTENT_TYPE,
                "text/html"
        );

        URL url = s3Client.generatePresignedUrl(generatePresignedUrlRequest);

        return url.toString();
    }


    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convertedFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream fos = new FileOutputStream(convertedFile);
        fos.write(file.getBytes());
        fos.close();
        return convertedFile;
    }

    private File generateHtmlFile(String htmlContent, String filePath) throws IOException {
        File file = new File(filePath);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(htmlContent);
        }
        return file;
    }

    public void testCollection() {

    }

}
