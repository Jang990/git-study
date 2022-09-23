package com.example.inflearnJWT.entity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
//@Table(name = "`user`")
@Table(name = "user_info")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

   @Id
   @Column(name = "user_id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long userId;

   @Column(name = "username", length = 50, unique = true)
   private String username;

   @Column(name = "password", length = 100)
   private String password;

   @Column(name = "nickname", length = 50)
   private String nickname;

   @Column(name = "activated")
   private boolean activated;

   @ManyToMany
   @JoinTable(
      name = "user_authority", // 연결 테이블 지정
      joinColumns = {@JoinColumn(name = "user_id", referencedColumnName = "user_id")}, // 현재 방향인 User와 매핑할 조인 컬럼 정보 지정
      inverseJoinColumns = {@JoinColumn(name = "authority_name", referencedColumnName = "authority_name")}) // 반대 방향인 Authority와 매핑할 조인 컬럼 정보 지정
   private Set<Authority> authorities;
}