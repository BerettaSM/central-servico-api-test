package br.com.test.centralservico.centralservicoapitest.service;

import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface TicketService {

    Page<TicketDto> findAll(int queryType, int page, int size, boolean isEnabled, User user);

    Optional<Ticket> findById(Long ticketId);

    Optional<Ticket> save(Ticket ticket);

    Optional<Ticket> update(Ticket ticket);

    Optional<Ticket> deleteById(Long ticketId);

    Optional<List<Ticket>> findAllUnpaginatedTickets();

}
