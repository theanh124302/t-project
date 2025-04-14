package tproject.postservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.postservice.enumerates.FileType;
import tproject.postservice.enumerates.Visibility;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequestDTO {

    private String status;

    private Visibility visibility;

    private Instant createdAt;

    private String content;

    List<Long> viewableUser;

    private FileType fileType;

}

