package com.bank.registersystem.model;

import com.bank.registersystem.constant.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user")
@NoArgsConstructor
@AllArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String identity;
    private String email;
    private String password;
    @Column(name = "user_type")
    private UserTypeEnum userType;
}
