package tproject.postwservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import tproject.postwservice.enumeration.MediaType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("post_medias")
public class PostMediaEntity {

    private Long postId;

    private String mediaUrl;

    private MediaType mediaType;

}
