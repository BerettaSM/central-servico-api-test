package br.com.test.centralservico.centralservicoapitest.persistence;

import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Page<Ticket> findAllByEnabled(Boolean enabled, PageRequest pageRequest);

    Page<Ticket> findAllByEnabledAndStatus(Boolean enabled, Integer status,  PageRequest pageRequest);

    Page<Ticket> findAllByEnabledAndStatusAndResponsibleUser(Boolean enabled,
                                                             Integer status,
                                                             User responsibleUser,
                                                             PageRequest pageRequest);

}
