package com.bank.registersystem.model;

import com.bank.registersystem.constant.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String identity;
    private String email;
    private String password;
    @Column(name = "user_type")
    @Enumerated(EnumType.STRING)
    private UserTypeEnum userType;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "wallet_id", referencedColumnName = "id")
    private WalletModel wallet;
}
