package br.com.test.centralservico.centralservicoapitest.persistence;

import br.com.test.centralservico.centralservicoapitest.domain.model.TicketMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketMessageRepository extends JpaRepository<TicketMessage, Long> {

    Optional<List<TicketMessage>> findAllByTicketId(Long ticketId);

}
