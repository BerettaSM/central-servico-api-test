package br.com.test.centralservico.centralservicoapitest.domain.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Entity
@Table(name = "TBL_USER")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FULL_NAME")
    private String fullName;

    @Column(name = "USERNAME")
    private String username;

    @Column(name = "PASSWORD")
    @JsonIgnore
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "TBL_USER_LEVEL",
               joinColumns = @JoinColumn(name = "ID_USER", referencedColumnName = "ID"),
               inverseJoinColumns = @JoinColumn(name = "ID_LEVEL", referencedColumnName = "ID"))
    private Set<Level> authorities;

    @Column(name = "ENABLE")
    private Boolean enabled;

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
