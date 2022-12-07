package br.com.test.centralservico.centralservicoapitest.domain.enums;

import lombok.Getter;

@Getter
public enum TicketQueryTypeEnum {

    ALL_TICKETS(0),
    OPEN_TICKETS(1),
    ONGOING_TICKETS(2),
    ONGOING_TICKETS_BY_ASSIGNED_USER(3),
    CLOSED_TICKETS_BY_ASSIGNED_USER(4);

    private final int queryType;

    TicketQueryTypeEnum(int queryType) {

        this.queryType = queryType;

    }

    public static TicketQueryTypeEnum findQueryType(int queryType) {

        for(TicketQueryTypeEnum ticketQueryTypeEnum : TicketQueryTypeEnum.values())
            if(queryType == ticketQueryTypeEnum.getQueryType())
                return ticketQueryTypeEnum;

        return ALL_TICKETS;

    }

}
