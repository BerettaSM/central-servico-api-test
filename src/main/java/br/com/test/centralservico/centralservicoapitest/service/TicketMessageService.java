package br.com.test.centralservico.centralservicoapitest.service;


import br.com.test.centralservico.centralservicoapitest.domain.model.TicketMessage;

import java.util.List;
import java.util.Optional;

public interface TicketMessageService {

    Optional<List<TicketMessage>> findAll(Long ticketId);

    Optional<TicketMessage> findById(Long ticketMessageId);

    TicketMessage save(TicketMessage ticketMessage);

    Optional<TicketMessage> update(TicketMessage ticketMessage);

    Optional<TicketMessage> deleteById(Long ticketMessageId);

}
