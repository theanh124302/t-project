package tproject.reactivetest;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Table("authentications")
public class EngineerEntity {

    @Id
    private Long id;

    private Long countryId;

}
