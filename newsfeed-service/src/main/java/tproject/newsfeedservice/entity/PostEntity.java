package tproject.newsfeedservice.entity;

import lombok.Data;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@Data
@Table("post")
public class PostEntity {
    private Integer id;
    private String firstName;
    private String lastName;
    private Integer gender;
    private Integer countryId;
    private String title;
    private LocalDate startedDate;
    private int syncStatus;
}

