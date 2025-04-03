package tproject.postwservice.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePostResponse {

    private Long id;

    private Long userId;

    private String status;

    private String visibility;

    private Instant createdAt;

}
