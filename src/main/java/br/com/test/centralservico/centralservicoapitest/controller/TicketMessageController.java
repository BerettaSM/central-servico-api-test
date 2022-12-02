package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.dto.Mapper;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ticket-message")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketMessageController {

    private final TicketMessageService ticketMessageService;

    private final TicketService ticketService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Optional<List<TicketMessageResponseDto>>> findAll(@RequestParam(value = "ticketId",
                                                                               required = false,
                                                                               defaultValue = "0") Long ticketId) {

        Optional<List<TicketMessage>> allMessages = ticketMessageService.findAll(ticketId);

        if(allMessages.isEmpty())
            throw new ResourceNotFoundException("Nenhuma mensagem encontrada"
                                                + (ticketId == 0 ? "." : " para o ticket com id " + ticketId));

        Optional<List<TicketMessageResponseDto>> allMessagesResponseDto = Optional.of(

            Mapper.fromTicketMessageListToDtoList(allMessages.get())

        );

        return ResponseEntity.status(HttpStatus.OK).body(allMessagesResponseDto);

    }

    @PostMapping
    public ResponseEntity<Optional<TicketMessageResponseDto>> save(@RequestBody TicketMessageRequestDto ticketMessageRequestDto) {

        Optional<Ticket> ticket = ticketService.findById(ticketMessageRequestDto.getTicketId());

        if(ticket.isEmpty())
            throw new ResourceNotFoundException("O id de ticket presente na mensagem é inválido.");

        Optional<User> sender = userService.findById(ticketMessageRequestDto.getSenderId());

        if(sender.isEmpty())
            throw new ResourceNotFoundException("O id de usuário presente na mensagem é inválido.");

        TicketMessage ticketMessageToSave = TicketMessage.builder()
                                                         .message(ticketMessageRequestDto.getMessage())
                                                         .sendDate(DateUtils.currentTimeStamp())
                                                         .ticket(ticket.get())
                                                         .sender(sender.get())
                                                         .build();

        TicketMessage savedMessage = ticketMessageService.save(ticketMessageToSave);

        Optional<TicketMessageResponseDto> savedMessageResponseDto = Optional.of(

                Mapper.fromTicketMessageToResponseDto(savedMessage)

        );

        return ResponseEntity.status(HttpStatus.OK).body(savedMessageResponseDto);

    }

}
