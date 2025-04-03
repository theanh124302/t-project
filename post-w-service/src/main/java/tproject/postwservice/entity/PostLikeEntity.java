package tproject.postwservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("post_likes")
public class PostLikeEntity extends ReactiveBaseEntity {

    private Long userId;

    private Long postId;

}
