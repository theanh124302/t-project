package tproject.postservice.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tproject.postservice.enumerates.Visibility;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostRequestDTO {

    private Visibility visibility;

    private String content;

    List<Long> viewableUser;

    private String fileType;

}

