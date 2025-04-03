package tproject.postwservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import tproject.postwservice.enumeration.Visibility;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("posts")
public class PostEntity extends ReactiveBaseEntity {

    private Long userId;

    private String status;

    private Visibility visibility;

}
