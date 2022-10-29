package br.com.test.centralservico.centralservicoapitest.domain.dto;


import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class TicketRequestDto implements Serializable {

    private static final Long serialVersionUID = 1L;

    private String title;

    private String description;

    private String priority;

    private Long userId;

    private Long areaId;

    private Long classificationId;

}
