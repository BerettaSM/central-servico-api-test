package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.util.Mapper;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.enums.TicketQueryTypeEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.persistence.TicketRepository;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketServiceImpl implements TicketService {

    private final TicketRepository ticketRepository;

    public Page<TicketDto> findAll(int queryType, int page, int size, boolean isEnabled, User user) {

        Page<Ticket> tickets = findAllByQueryType(queryType, page, size, isEnabled, user);

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

    public Optional<List<Ticket>> findAllUnpaginatedTickets() {

        return Optional.of(ticketRepository.findAll());

    }

    private Page<Ticket> findAllByQueryType(int queryType, int page, int size, boolean isEnabled, User user) {

        TicketQueryTypeEnum query = TicketQueryTypeEnum.findQueryType(queryType);

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.DESC, "dateStart");

        switch(query) {

            case OPEN_TICKETS:

                return ticketRepository.findAllByEnabledAndStatus(

                        isEnabled, StatusTicketEnum.OPEN.getValue(), pageRequest

                );

            case ONGOING_TICKETS:

                return ticketRepository.findAllByEnabledAndStatus(

                        isEnabled, StatusTicketEnum.IN_PROGRESS.getValue(), pageRequest

                );

            case ONGOING_TICKETS_BY_ASSIGNED_USER:

                return ticketRepository.findAllByEnabledAndStatusAndResponsibleUser(

                        isEnabled, StatusTicketEnum.IN_PROGRESS.getValue(), user, pageRequest

                );

            case CLOSED_TICKETS_BY_ASSIGNED_USER:

                return ticketRepository.findAllByEnabledAndStatusAndResponsibleUser(

                        isEnabled, StatusTicketEnum.CLOSED.getValue(), user, pageRequest

                );

        }

        // Default é ALL_TICKETS
        return ticketRepository.findAllByEnabled(isEnabled, pageRequest);

    }

}
