package br.com.test.centralservico.centralservicoapitest.controller;

import br.com.test.centralservico.centralservicoapitest.util.Mapper;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketDto;
import br.com.test.centralservico.centralservicoapitest.domain.dto.TicketRequestDto;
import br.com.test.centralservico.centralservicoapitest.domain.enums.StatusTicketEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.Area;
import br.com.test.centralservico.centralservicoapitest.domain.model.Classification;
import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import br.com.test.centralservico.centralservicoapitest.exception.ResourceNotFoundException;
import br.com.test.centralservico.centralservicoapitest.service.AreaService;
import br.com.test.centralservico.centralservicoapitest.service.ClassificationService;
import br.com.test.centralservico.centralservicoapitest.service.TicketService;
import br.com.test.centralservico.centralservicoapitest.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    private final AreaService areaService;

    private final UserService userService;

    private final TicketService ticketService;

    private final ClassificationService classificationService;

    @GetMapping
    public ResponseEntity<Page<TicketDto>> findAll(@RequestParam(value = "queryType",
                                                                 required = false,
                                                                 defaultValue = "0") int queryType,
                                                   @RequestParam(value = "page",
                                                                 required = false,
                                                                 defaultValue = "0") int page,
                                                   @RequestParam(value = "size",
                                                                 required = false,
                                                                 defaultValue = "10") int size,
                                                   @RequestParam(value = "isEnabled",
                                                                 required = false,
                                                                 defaultValue = "true") boolean isEnabled,
                                                   @AuthenticationPrincipal User user) {

        Page<TicketDto> ticketPage = ticketService.findAll(queryType, page, size, isEnabled, user);

        if(ticketPage.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketPage);

    }

    @GetMapping("/{ticketId}")
    public ResponseEntity<Optional<TicketDto>> findById(@PathVariable Long ticketId,
                                                        @AuthenticationPrincipal User user) {

        Optional<Ticket> ticket = ticketService.findById(ticketId);

        if(ticket.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket com o id " + ticketId + " foi encontrado.");

        Optional<TicketDto> ticketFound = Optional.of(Mapper.fromTicketToDto(ticket.get()));

        return ResponseEntity.status(HttpStatus.OK).body(ticketFound);

    }

    @GetMapping("/assign-to-me")
    public ResponseEntity<Optional<TicketDto>> assignToMe(@RequestParam(value = "ticketId") Long ticketId,
                                                          @RequestParam(value = "attendantId") Long attendantId,
                                                          @AuthenticationPrincipal User user) {

        Optional<User> attendant = userService.findById(attendantId);

        Optional<Ticket> ticketToAssign = ticketService.findById(ticketId);

        if(attendant.isEmpty() || ticketToAssign.isEmpty())
            throw new ResourceNotFoundException("Atribuição de ticket não válida.");

        ticketToAssign.get().setResponsibleUser(attendant.get());

        Integer ticketStatus = ticketToAssign.get().getStatus();

        if(ticketStatus == StatusTicketEnum.OPEN.getValue() || ticketStatus == StatusTicketEnum.REOPENED.getValue())
            ticketToAssign.get().setStatus(StatusTicketEnum.IN_PROGRESS.getValue());

        Optional<Ticket> updatedTicket = ticketService.update(ticketToAssign.get());

        if(updatedTicket.isEmpty())
            throw new ResourceNotFoundException("Falha na atualização.");

        Optional<TicketDto> updatedTicketDto = Optional.of(Mapper.fromTicketToDto(updatedTicket.get()));

        return ResponseEntity.status(HttpStatus.OK).body(updatedTicketDto);

    }

    @PostMapping
    public ResponseEntity<Optional<Ticket>> save(@RequestBody TicketRequestDto ticketRequestDto,
                                                 @AuthenticationPrincipal User user) {

        Optional<User> openedBy = userService.findById(ticketRequestDto.getUserId());

        if(openedBy.isEmpty())
            throw new ResourceNotFoundException("O ticket não está vinculado a nenhum usuário.");

        Optional<Classification> classification =
                classificationService.findById(ticketRequestDto.getClassificationId());

        if(classification.isEmpty())
            throw new ResourceNotFoundException("O ticket não possui uma classificação válida.");

        Optional<Area> area = areaService.findById(ticketRequestDto.getAreaId());

        if(area.isEmpty())
            throw new ResourceNotFoundException("O ticket não possui uma área válida.");

        Ticket ticketToSave = Ticket.builder()
                                    .title(ticketRequestDto.getTitle())
                                    .description(ticketRequestDto.getDescription())
                                    .priority(ticketRequestDto.getPriority())
                                    .openedBy(openedBy.get())
                                    .classification(classification.get())
                                    .area(area.get())
                                    .build();

        Optional<Ticket> savedTicket = ticketService.save(ticketToSave);

        return ResponseEntity.status(HttpStatus.OK).body(savedTicket);

    }

    @PutMapping
    public ResponseEntity<Optional<Ticket>> update(@RequestBody Ticket ticket,
                                                   @AuthenticationPrincipal User user) {

        Optional<Ticket> ticketToUpdate = ticketService.update(ticket);

        if(ticketToUpdate.isEmpty())
            throw new ResourceNotFoundException("Ticket não existe no banco de dados.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketToUpdate);

    }

    @DeleteMapping("/{ticketId}")
    public ResponseEntity<Optional<Ticket>> deleteById(@PathVariable Long ticketId,
                                                       @AuthenticationPrincipal User user) {

        Optional<Ticket> ticketToDelete = ticketService.deleteById(ticketId);

        if(ticketToDelete.isEmpty())
            throw new ResourceNotFoundException("Nenhum ticket com o id " + ticketId + " foi encontrado.");

        return ResponseEntity.status(HttpStatus.OK).body(ticketToDelete);

    }

}
