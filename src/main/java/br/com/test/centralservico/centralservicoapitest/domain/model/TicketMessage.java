package br.com.test.centralservico.centralservicoapitest.domain.model;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "TBL_TICKET_MESSAGE")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketMessage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "MESSAGE", nullable = false)
    private String message;

    @Column(name = "DATE_SEND", nullable = false)
    private String sendDate;

    @ManyToOne
    @JoinColumn(name = "ID_TICKET", nullable = false)
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "ID_SENDER", nullable = false)
    private User sender;

}
