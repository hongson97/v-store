package com.example.vstore.mode;

import javax.persistence.*;

import java.io.Serializable;
import java.util.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Getter
@Setter
@EqualsAndHashCode
//@AllArgsConstructor
@Entity
@Table(name="user")
@JacksonXmlRootElement(localName = "user")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements Serializable, UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id_user;
    private String user_name;
    private String password;
    private String name;
    private int age;
    private Long wallet;
    private String email;
    private String avt = "default.jpg";
    private String role;
    private Boolean enabled;



    public  User() {
    }
    public  User(Long id_user, String user_name, String password, String name, int age,
                 Long wallet, String email, String avt, String role, Boolean enable) {
        this.id_user = id_user;
        this.user_name = user_name;
        this.password = password;
        this.name = name;
        this.age = age;
        this.wallet = wallet;
        this.email = email;
        this.role =role;
        this.enabled=enable;
        this.avt =avt;
    }
    // Get
    public Long getId_user() {return id_user;}
    public int getAge() {
        return age;
    }
    public String getUser_name() {
        return user_name;
    }
    public Long getWallet() {
        return wallet;
    }
    public String getEmail() {
        return  email;
    }
    public Boolean getEnabled() {
        return enabled;
    }
    public String getRole() {
        return role;
    }
    public String getAvt() {
        return avt;
    }
    public String getName() {
        return name;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return user_name;
    }

    // Set

    public void setName(String name) {
        this.name = name;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public void setWallet(Long wallet) {
        this.wallet = wallet;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public void setAvt(String avt) {
        this.avt = avt;
    }

    @Override
    public String toString(){
        return "User{id="+ getId_user() + ", userName=" + getUser_name() +", name="+getName()
                + ", age=" +getAge()+", email="+getEmail()+", role=" + getRole()+ ", enabled="+getEnabled()+"};";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        final SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
        return Collections.singletonList(simpleGrantedAuthority);
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }



}
