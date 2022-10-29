package br.com.test.centralservico.centralservicoapitest.domain.dto;

import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.util.DateUtils;

public abstract class Mapper {

    public static TicketDto fromTicketToDto(Ticket ticket) {

        String statusDescription = StatusTicketEnum.getEnumByValue(ticket.getStatus())
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
                .priority(ticket.getPriority())
                .responsibleUser(ticket.getResponsibleUser())
                .onTime(DateUtils.isOnTime(ticket.getDateEnd()))
                .build();

    }

}
