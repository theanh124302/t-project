package tproject.postwservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;
import tproject.tcommon.model.BaseEntity;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("posts")
public class PostEntity extends BaseEntity {

    private Long userId;

    private String status;

    private String visibility;

}
