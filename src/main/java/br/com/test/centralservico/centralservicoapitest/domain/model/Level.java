package br.com.test.centralservico.centralservicoapitest.domain.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TBL_LEVEL")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Level implements Serializable {

    private static final long serialVersionUID = -9045151681602620943L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LEVEL_CODE")
    private String levelCode;

    @Column(name = "LEVEL_NAME")
    private String levelName;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "ENABLE")
    private Boolean enabled;

}
