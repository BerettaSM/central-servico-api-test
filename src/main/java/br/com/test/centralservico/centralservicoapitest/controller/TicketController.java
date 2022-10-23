package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketRequestDto;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/ticket")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TicketController {

    private final TicketService ticketService;

    private final UserService userService;

    @GetMapping
    public ResponseEntity<Page<TicketDto>> findAll(@RequestParam(value = "status",
                                                                 required = false,
                                                                 defaultValue = "0") int status,
                                                   @RequestParam(value = "page",
                                                                 required = false,
                                                                 defaultValue = "0") int page,
                                                   @RequestParam(value = "size",
                                                                 required = false,
                                                                 defaultValue = "10") int size) {

        Page<TicketDto> ticketPage = ticketService.findAll(status, page, size);

        if(ticketPage.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketPage);

    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Optional<TicketDto>> findById(@PathVariable Long ticketId) {

        Optional<TicketDto> ticket = ticketService.findById(ticketId);

        if(ticket.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket com o id " + ticketId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(ticket);

    }

    @PostMapping
    public ResponseEntity<Optional<TicketDto>> save(@RequestBody TicketRequestDto ticketRequestDto) {

        Optional<User> openedBy = userService.findById(ticketRequestDto.getUserId());

        if(openedBy.isEmpty())
            throw new ResourceNotFoundException("O ticket não está vinculado a nenhum usuário.");

        Ticket ticket = ticketRequestDto.getTicket();

        ticket.setOpenedBy(openedBy.get());

        Optional<TicketDto> ticketToSave = ticketService.save(ticket);

        return ResponseEntity.status(HttpStatus.OK).body(ticketToSave);

    }

    @PutMapping
    public ResponseEntity<Optional<Ticket>> update(@RequestBody Ticket ticket) {

        Optional<Ticket> ticketToUpdate = ticketService.update(ticket);

        if(ticketToUpdate.isEmpty())
            throw new ResourceNotFoundException("Ticket não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketToUpdate);

    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Optional<Ticket>> deleteById(@PathVariable Long ticketId) {

        Optional<Ticket> ticketToDelete = ticketService.deleteById(ticketId);

        if(ticketToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket com o id " + ticketId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketToDelete);

    }

}
