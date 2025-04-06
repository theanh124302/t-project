package tproject.reactivereadpostservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import tproject.reactivereadpostservice.enumerate.MediaType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("medias")
public class MediaEntity extends ReactiveBaseEntity {

    private Long postId;

    private String mediaUrl;

    private MediaType mediaType;

}
