package com.gor.socialmediarest.db.entities;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;
import java.util.Collection;
import java.util.stream.Collectors;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Entity
@NoArgsConstructor
@Data
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)
public class UserEntity implements UserDetails {

    public UserEntity(String name, String username, String password) {
        this.name = name;
        this.username = username;
        this.password = password;
    }

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(length = 50)
    private String name;
    
    @NotNull
    @NotBlank
    @NotEmpty
    @Column(length = 50)
    private String username;

    @NotNull
    @NotBlank
    @NotEmpty
    @Column(length = 60)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<UserRoleEntity> roles = new ArrayList<UserRoleEntity>();
    
    @OneToMany(mappedBy="author")
    private List<PostEntity> posts;

    private boolean expired = false;

    private boolean locked = false;

    private boolean credentialsExpired = false;

    private boolean enabled = true;

    @CreatedDate
    private LocalDate createdDate;

    @LastModifiedDate
    private LocalDate lastModifiedDate;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().name()))
                .collect(Collectors.toSet());
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return !expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void addRole(UserRoleEntity role) {
        roles.add(role);
    }
}
