package br.com.test.centralservico.centralservicoapitest.domain.dto;

import br.com.test.centralservico.centralservicoapitest.domain.model.Area;
import br.com.test.centralservico.centralservicoapitest.domain.model.Classification;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlattenedTicketResponseDto {

    private Long ticketId;
    private Integer statusId;
    private String statusDesc;
    private String title;
    private String priority;
    private String description;
    private String openedByName;
    private Long openedById;
    private String responsibleUserName;
    private Long responsibleUserId;
    private Long areaId;
    private String areaDesc;
    private Long classificationId;
    private String classificationDesc;
    private String dateStart;
    private String dateEnd;
    private String dateUpdated;
    private Boolean onTime;
    private Boolean enabled;

}
