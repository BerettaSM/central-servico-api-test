package br.com.test.centralservico.centralservicoapitest.domain.dto;

import br.com.test.centralservico.centralservicoapitest.domain.model.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TicketDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long ticketId;

    private Integer status;

    private String descStatus;

    private String title;

    private User openedBy;

    private String dateStart;

    private String dateEnd;

    private String dateUpdated;

    private String priority;

    private User responsibleUser;

    private Boolean onTime;

}
