package com.example.demo.Entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

/**
 * Created by Cloie Andrea on 15/07/2018.
 */

@Entity
@Table(name="users")
@EntityListeners(AuditingEntityListener.class)
//@JsonIgnoreProperties(value={"createdAt","updatedAt"},allowGetters=true)
public class User  {

    @Id
    @GeneratedValue
    private Long id;


    private String first_name;


    private String last_name;

    @Column(unique = true)
    private String email;

    @NotBlank
    @Column(unique = true)
    private String username;

    @NotBlank
    private String password;

//    @Column(name = "enabled", nullable = false)
//    private boolean enabled;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


//    public void AccountAvailable() {
//        return "account found";
//    }
//    public String NotAccountAvailable() {
//        return "account not found";
//    }
//
//
//    public boolean isAccountNonLocked() {
//        // we never lock accounts
//        return true;
//    }
//
//
//    public boolean isCredentialsNonExpired() {
//        // credentials never expire
//        return true;
//    }
//
//
//    public boolean isEnabled() {
//        return enabled;
//    }
}
