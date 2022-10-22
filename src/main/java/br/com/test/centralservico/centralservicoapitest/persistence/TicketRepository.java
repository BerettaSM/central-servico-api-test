package br.com.test.centralservico.centralservicoapitest.persistence;

import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByStatus(Integer status);

}
