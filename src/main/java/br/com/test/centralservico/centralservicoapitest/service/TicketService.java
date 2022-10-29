package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface TicketService {

    Page<TicketDto> findAll(int status, int page, int size);

    Optional<TicketDto> findById(Long ticketId);

    Optional<TicketDto> save(Ticket ticket);

    Optional<Ticket> update(Ticket ticket);

    Optional<TicketDto> deleteById(Long ticketId);

}
