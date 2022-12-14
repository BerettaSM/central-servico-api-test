package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.util.Mapper;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketMessageRequestDto;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketMessageResponseDto;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.TicketMessage;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.TicketMessageService;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
import br.com.test.centralservico.centralservicoapitest.util.DateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ChatController {

    private final SimpMessagingTemplate simpMessagingTemplate;

    private final TicketMessageService ticketMessageService;

    private final TicketService ticketService;

    private final UserService userService;

    @MessageMapping("/message/{ticketId}")
    public void receiveTicketMessage(@DestinationVariable String ticketId,
                                     @Payload TicketMessageRequestDto message) {

        TicketMessageResponseDto savedMessage = saveMessage(message);

        String destination = "/topic/ticket/" + ticketId;

        simpMessagingTemplate.convertAndSend(destination, savedMessage);

    }

    private TicketMessageResponseDto saveMessage(TicketMessageRequestDto message) {

        Optional<User> sender = userService.findById(message.getSenderId());
        Optional<Ticket> ticket = ticketService.findById(message.getTicketId());

        if(sender.isEmpty() || ticket.isEmpty())
            throw new ResourceNotFoundException("O id do ticket/usu??rio ?? inv??lido.");

        TicketMessage ticketMessage = TicketMessage.builder()
                                                   .message(message.getMessage())
                                                   .sender(sender.get())
                                                   .ticket(ticket.get())
                                                   .sendDate(DateUtils.currentTimeStamp())
                                                   .build();

        TicketMessage savedMessage = ticketMessageService.save(ticketMessage);

        return Mapper.fromTicketMessageToResponseDto(savedMessage);

    }

}
