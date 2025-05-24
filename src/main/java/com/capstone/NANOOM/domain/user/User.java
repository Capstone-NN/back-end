package com.capstone.NANOOM.domain.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.annotations.SoftDelete;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SoftDelete
@EqualsAndHashCode(of = "id")
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // validation을 dto에서 할지, entity에서 할지 고민
    @NotBlank @Size(max = 30)
    @Column(name = "login_id", length = 30, nullable = false, unique = true)
    private String loginId;

    @NotBlank @Size(min = 8, max = 100)
    @Column(length = 100, nullable = false)
    @JsonIgnore
    private String password;

    @NotBlank @Size(max = 20)
    @Column(length = 20, nullable = false)
    private String nickname;

    @NotBlank @Email
    @Column(length = 100, nullable = false, unique = true)
    private String email;

    @Column(length = 255, name = "profile_image")
    @Builder.Default
    private String profileImage = "https://default-image.com/default.jpg";
    // default 이미지 필요

    @Column(name = "nonoom_score", nullable = false)
    @Builder.Default
    private int nanoomScore = 0;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id")
    )
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Set<Role> roles = new HashSet<>();
}
