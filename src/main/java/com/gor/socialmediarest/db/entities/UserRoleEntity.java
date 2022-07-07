package com.gor.socialmediarest.db.entities;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import com.gor.socialmediarest.security.UserRole;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "roles")
@EntityListeners(AuditingEntityListener.class)
public class UserRoleEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserRole name;

    @ManyToMany(mappedBy = "roles")
    private List<UserEntity> users;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;

    public UserRoleEntity(UserRole name) {
        this.name = name;
    }
}
