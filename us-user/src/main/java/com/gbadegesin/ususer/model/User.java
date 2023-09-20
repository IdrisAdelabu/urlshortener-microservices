package com.gbadegesin.ususer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.List;

@Entity
@Table(name = "us_users",
        uniqueConstraints = {@UniqueConstraint(name = "users_email_unique", columnNames = "email"),
                @UniqueConstraint(name = "users_username_unique", columnNames = "username")})
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Accessors(chain = true)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    private String username;
    private String password;
    private String creationDate;
    private boolean isActive;
    private String role;

    @OneToMany
    private List<Url> urls;

//    @Transient
//    private List<GrantedAuthority> authorities;
//    @Transient
//    private boolean isValidated;
//    @Transient
//    private boolean accountNonExpired;
//    @Transient
//    private boolean accountNonLocked;
//    @Transient
//    private boolean credentialsNonExpired;
//    @Transient
//    private boolean enabled;

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return null;
//    }
//
//    @Override
//    public String getUsername() {
//        return null;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return false;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return false;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return false;
//    }


}
