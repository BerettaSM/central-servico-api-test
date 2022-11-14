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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TBL_TICKET")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "STATUS")
    private Integer status;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "PRIORITY")
    private String priority;

    @Column(name = "DESCRIPTION")
    private String description;

    @ManyToOne
    @JoinColumn(name = "ID_USER_OPEN_BY", nullable = false)
    private User openedBy;

    @ManyToOne
    @JoinColumn(name = "ID_USER_RESPONSIBLE")
    private User responsibleUser;

    @ManyToOne
    @JoinColumn(name = "ID_AREA", nullable = false)
    private Area area;

    @ManyToOne
    @JoinColumn(name = "ID_CLASSIFICATION", nullable = false)
    private Classification classification;

    @Column(name = "DATE_START")
    private String dateStart;

    @Column(name = "DATE_END")
    private String dateEnd;

    @Column(name = "DATE_UPDATED")
    private String dateUpdated;

    @Column(name = "ENABLE")
    private Boolean enabled;

}
