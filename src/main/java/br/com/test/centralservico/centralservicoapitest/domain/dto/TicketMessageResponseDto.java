package br.com.test.centralservico.centralservicoapitest.domain.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class TicketMessageResponseDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String message;

    private String sendDate;

    private Long senderId;

    private String senderName;

    private String senderLevel;

}
