package br.com.test.centralservico.centralservicoapitest.domain.dto;


import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class TicketRequestDto {

    private String title;

    private String description;

    private Integer priority;

    private Long userId;

    private Long areaId;

    private Long classificationId;

}
