//package tproject.userservice.entity;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//import tproject.tcommon.model.BaseEntity;
//import tproject.userservice.enumeration.UserTheme;
//
//@Entity
//@Table(name = "user_settings")
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//public class UserSettingEntity extends BaseEntity {
//
//    private Long userId;
//
//    @Column(name = "theme")
//    @Enumerated(EnumType.STRING)
//    private UserTheme theme;
//
//}