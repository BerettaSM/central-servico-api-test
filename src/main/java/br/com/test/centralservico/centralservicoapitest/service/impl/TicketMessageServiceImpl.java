package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.domain.model.TicketMessage;
import br.com.test.centralservico.centralservicoapitest.persistence.TicketMessageRepository;
import br.com.test.centralservico.centralservicoapitest.service.TicketMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketMessageServiceImpl implements TicketMessageService {

    private final TicketMessageRepository ticketMessageRepository;

    @Override
    public Optional<List<TicketMessage>> findAll(Long ticketId) {

        if(ticketId == 0)
            return Optional.of(ticketMessageRepository.findAll());

        else
            return ticketMessageRepository.findAllByTicketId(ticketId);

    }

    @Override
    public Optional<TicketMessage> findById(Long ticketMessageId) {

        return ticketMessageRepository.findById(ticketMessageId);

    }

    @Override
    public TicketMessage save(TicketMessage ticketMessage) {

        return ticketMessageRepository.save(ticketMessage);

    }

    @Override
    public Optional<TicketMessage> update(TicketMessage ticketMessage) {

        Optional<TicketMessage> ticketMessageToUpdate = ticketMessageRepository.findById(ticketMessage.getId());

        if(ticketMessageToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(ticketMessageRepository.saveAndFlush(ticketMessage));

    }

    @Override
    public Optional<TicketMessage> deleteById(Long ticketMessageId) {

        Optional<TicketMessage> ticketMessageToDelete = ticketMessageRepository.findById(ticketMessageId);

        ticketMessageToDelete.ifPresent(ticketMessageRepository::delete);

        return ticketMessageToDelete;

    }

}
