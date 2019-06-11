package org.kimbs.jpademo.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", nullable = false)
    @NotEmpty(message = "loginId is required")
    @Length(max = 36, message = "should be loginId length less than 36")
    private String loginId;

    @Column(name = "password", nullable = false)
    @NotEmpty(message = "password is required")
    private String password;

    @Column(name = "email")
    @Email(message = "wrong email")
    private String email;

    @Column(name = "phone")
    private String phone;
}