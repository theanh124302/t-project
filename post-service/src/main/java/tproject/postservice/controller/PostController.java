package tproject.postservice.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import tproject.postservice.dto.request.CreatePostRequestDTO;
import tproject.postservice.dto.response.CreatedLightPostResponseDTO;
import tproject.postservice.dto.response.CreatedMassivePostResponseDTO;
import tproject.postservice.service.PostService;
import tproject.tcommon.enums.ResponseStatus;
import tproject.tcommon.response.restfulresponse.RestfulResponse;

@Slf4j
@RestController
@RequestMapping("/v1")
@AllArgsConstructor
public class PostController {


    private final PostService postService;

    private final ObjectMapper objectMapper;

    @PostMapping(value = "/create/light", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public RestfulResponse<CreatedLightPostResponseDTO> createLightPost(@RequestParam("data") String  createPostRequestText,
                                                                        @RequestParam("file") MultipartFile file,
                                                                        @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        CreatePostRequestDTO createPostRequestDTO;
        try {
            createPostRequestDTO = objectMapper.readValue(createPostRequestText, CreatePostRequestDTO.class);
        } catch (Exception e) {
            log.error("Error parsing JSON: {}", e.getMessage());
            return RestfulResponse
                    .error("Invalid JSON format", ResponseStatus.ERROR);
        }
        CreatedLightPostResponseDTO createdLightPostResponseDTO = postService.createLightPost(createPostRequestDTO, userId, file);
        return RestfulResponse
                .success(createdLightPostResponseDTO, ResponseStatus.SUCCESS);

    }

    @PostMapping("/create/massive")
    public RestfulResponse<CreatedMassivePostResponseDTO> createMassivePost(@RequestBody CreatePostRequestDTO createPostRequestDTO,
                                                                           @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        CreatedMassivePostResponseDTO createdMassivePostResponseDTO = postService.createMassivePost(createPostRequestDTO, userId);
        return RestfulResponse
                .success(createdMassivePostResponseDTO, ResponseStatus.SUCCESS);
    }

}
