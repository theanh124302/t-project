//package tproject.userservice.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.NonNull;
//import lombok.Setter;
//import tproject.tcommon.model.BaseEntity;
//import tproject.userservice.enumeration.UserBlockStatus;
//
//import java.sql.Timestamp;
//
//@Entity
//@Table(name = "user_blocks")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
//public class UserBlockEntity extends BaseEntity {
//
//    @NonNull
//    private Long blockerId;
//
//    @NonNull
//    private Long blockedId;
//
//    @NonNull
//    @Enumerated(EnumType.STRING)
//    private UserBlockStatus status;
//
//    private Timestamp blockDate;
//
//}