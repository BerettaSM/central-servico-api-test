package br.com.test.centralservico.centralservicoapitest.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TicketMessageRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String message;

    private Long ticketId;

    private Long senderId;

}
