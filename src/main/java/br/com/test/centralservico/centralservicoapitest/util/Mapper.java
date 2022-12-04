package br.com.test.centralservico.centralservicoapitest.util;

import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketMessageResponseDto;
import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Level;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.TicketMessage;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public static TicketMessageResponseDto fromTicketMessageToResponseDto(TicketMessage ticketMessage) {

        return TicketMessageResponseDto.builder()
                                       .id(ticketMessage.getId())
                                       .message(ticketMessage.getMessage())
                                       .sendDate(ticketMessage.getSendDate())
                                       .senderId(ticketMessage.getSender().getId())
                                       .senderName(ticketMessage.getSender().getFullName())
                                       .senderLevel(getHighestUserAuthority(ticketMessage))
                                       .build();

    }

    public static List<TicketMessageResponseDto> fromTicketMessageListToDtoList(List<TicketMessage> ticketMessages) {

        return ticketMessages.stream()
                             .map(Mapper::fromTicketMessageToResponseDto)
                             .collect(Collectors.toList());

    }

    private static String getHighestUserAuthority(TicketMessage ticketMessage) {

        Optional<Level> level = ticketMessage.getSender()
                .getAuthorities()
                .stream().min(Comparator.comparing(Level::getId));

        if(level.isEmpty())
            return "DESCONHECIDO";

        return level.get().getLevelName();

    }

}
