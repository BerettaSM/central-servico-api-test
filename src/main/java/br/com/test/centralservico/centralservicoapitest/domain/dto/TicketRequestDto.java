package br.com.test.centralservico.centralservicoapitest.domain.dto;


import br.com.test.centralservico.centralservicoapitest.domain.model.Ticket;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TicketRequestDto {

    private Ticket ticket;

    private Long userId;

}
