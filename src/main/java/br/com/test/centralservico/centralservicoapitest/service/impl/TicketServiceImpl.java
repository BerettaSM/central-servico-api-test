package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.domain.dto.Mapper;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.persistence.TicketRepository;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
import br.com.test.centralservico.centralservicoapitest.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public Page<TicketDto> findAll(int status, int page, int size, boolean isEnabled) {

        Page<Ticket> tickets;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        if(status == StatusTicketEnum.NOT_FOUND.getValue())
            tickets = ticketRepository.findAllByEnabled(isEnabled, pageRequest);

        else
            tickets = ticketRepository.findAllByEnabledAndStatus(isEnabled, status, pageRequest);

        return tickets.map(Mapper::fromTicketToDto);

    }

    @Override
    public Optional<Ticket> findById(Long ticketId) {

        return ticketRepository.findById(ticketId);

    }

    @Override
    public Optional<Ticket> save(Ticket ticket) {

        Integer deadlineInHours = ticket.getClassification().getAddTime();

        LocalDateTime startDate = LocalDateTime.now();

        LocalDateTime endDate = startDate.plusHours(deadlineInHours);

        ticket.setStatus(StatusTicketEnum.OPEN.getValue());

        ticket.setDateStart(DateUtils.toFormattedString(startDate));

        ticket.setDateEnd(DateUtils.toFormattedString(endDate));

        ticket.setEnabled(true);

        Ticket savedTicket = ticketRepository.save(ticket);

        return Optional.of(savedTicket);

    }

    @Override
    public Optional<Ticket> update(Ticket ticket) {

        Optional<Ticket> ticketToUpdate = ticketRepository.findById(ticket.getId());

        if(ticketToUpdate.isEmpty())
            return Optional.empty();

        ticket.setDateUpdated(DateUtils.currentTimeStamp());

        return Optional.of(ticketRepository.saveAndFlush(ticket));

    }

    @Override
    public Optional<Ticket> deleteById(Long ticketId) {

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        if(ticket.isEmpty())
            return Optional.empty();

        Ticket ticketToDelete = ticket.get();

        ticketToDelete.setEnabled(false);

        ticketToDelete.setDateUpdated(DateUtils.currentTimeStamp());

        if(ticketToDelete.getStatus() != StatusTicketEnum.CLOSED.getValue())
            ticketToDelete.setStatus(StatusTicketEnum.CANCELLED.getValue());

        return Optional.of(ticketRepository.saveAndFlush(ticketToDelete));

    }

}
