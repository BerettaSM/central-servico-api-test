package br.com.test.centralservico.centralservicoapitest.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.FetchType;
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
import jakarta.persistence.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;

@Entity
@Table(name = "TBL_LEVEL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Level implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "AUTHORITY")
    private String authority;

    @Column(name = "LEVEL_NAME")
    private String levelName;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToMany(mappedBy = "authorities", fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<User> users;

    @Column(name = "ENABLE")
    private Boolean enabled;

}
