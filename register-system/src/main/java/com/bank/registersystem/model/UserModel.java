package com.bank.registersystem.model;

import com.bank.registersystem.constant.UserTypeEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user_tb")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class UserModel implements UserDetails {

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

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.userType == UserTypeEnum.USER)
            return List.of(new SimpleGrantedAuthority("USER"), new SimpleGrantedAuthority("SHOPKEEPER"));
        else return List.of(new SimpleGrantedAuthority("SHOPKEEPER"));
    }

    @Override
    public String getUsername() {
        return this.name;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
