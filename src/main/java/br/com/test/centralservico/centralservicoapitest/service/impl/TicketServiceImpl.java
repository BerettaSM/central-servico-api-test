package br.com.test.centralservico.centralservicoapitest.service.impl;

import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.enums.PriorityTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.persistence.TicketRepository;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.util.MyDateUtils;
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

    public Page<TicketDto> findAll(int status, int page, int size) {

        Page<Ticket> tickets;

        PageRequest pageRequest = PageRequest.of(page, size, Sort.Direction.ASC, "id");

        if(status == StatusTicketEnum.NOT_FOUND.getValue())
            tickets = ticketRepository.findAll(pageRequest);

        else
            tickets = ticketRepository.findAllByStatus(status, pageRequest);

        return tickets.map(this::convertFromTicketToDto);

    }

    @Override
    public Optional<TicketDto> findById(Long ticketId) {

        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        if(ticket.isEmpty())
            return Optional.empty();

        return Optional.of(convertFromTicketToDto(ticket.get()));

    }

    @Override
    public Optional<TicketDto> save(Ticket ticket) {

        Integer addTime = ticket.getClassification().getAddTime();

        LocalDateTime startDate = LocalDateTime.now();

        LocalDateTime endDate = startDate.plusHours(addTime);

        ticket.setStatus(StatusTicketEnum.OPEN.getValue());

        ticket.setDateStart(MyDateUtils.toFormattedString(startDate));

        ticket.setDateEnd(MyDateUtils.toFormattedString(endDate));

        ticket.setEnabled(true);

        Ticket savedTicket = ticketRepository.save(ticket);

        return Optional.of(convertFromTicketToDto(savedTicket));

    }

    @Override
    public Optional<Ticket> update(Ticket ticket) {

        Optional<Ticket> ticketToUpdate = ticketRepository.findById(ticket.getId());

        if(ticketToUpdate.isEmpty())
            return Optional.empty();

        return Optional.of(ticketRepository.saveAndFlush(ticket));

    }

    @Override
    public Optional<Ticket> deleteById(Long ticketId) {

        Optional<Ticket> ticketToDelete = ticketRepository.findById(ticketId);

        ticketToDelete.ifPresent(ticketRepository::delete);

        return ticketToDelete;

    }

    private TicketDto convertFromTicketToDto(Ticket ticket) {

        String statusDescription = StatusTicketEnum.getEnumByValue(ticket.getStatus())
                                                   .getDescription();

        String priorityDescription = PriorityTicketEnum.getEnumByValue(ticket.getPriority())
                                                       .getDescription();

        return TicketDto.builder()
                        .ticketId(ticket.getId())
                        .status(ticket.getStatus())
                        .descStatus(statusDescription)
                        .title(ticket.getTitle())
                        .openedBy(ticket.getOpenedBy())
                        .dateStart(ticket.getDateStart())
                        .dateEnd(ticket.getDateEnd())
                        .dateUpdated(ticket.getDateUpdated())
                        .priority(priorityDescription)
                        .responsibleUser(ticket.getResponsibleUser())
                        .onTime(MyDateUtils.isOnTime(ticket.getDateEnd()))
                        .build();

    }

}
