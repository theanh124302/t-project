package tproject.reactivetest;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("engineer_sync")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class EngineerEntity {

    @Id
    private Long id;

    private String title;

}
