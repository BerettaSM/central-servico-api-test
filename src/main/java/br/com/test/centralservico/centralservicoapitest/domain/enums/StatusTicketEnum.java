package br.com.test.centralservico.centralservicoapitest.domain.enums;

import lombok.Getter;

@Getter
public enum StatusTicketEnum {

    NOT_FOUND("Ticket não encontrado", 0),
    OPEN("Ticket aberto", 1),
    REOPENED("Ticket reaberto",2),
    IN_PROGRESS("Ticket em andamento",3),
    CLOSED("Ticket fechado",4),
    CANCELLED("Ticket cancelado",5);

    private final String description;

    private final int value;

    private StatusTicketEnum(String description, int value) {

        this.description = description;

        this.value = value;

    }

    public static StatusTicketEnum getDescByValue(int value) {

        for(StatusTicketEnum status: StatusTicketEnum.values())
            if(status.value == value)
                return status;

        return NOT_FOUND;

    }

    public static void main(String[] args) {

        System.out.println(getDescByValue(6));

        for(StatusTicketEnum status: StatusTicketEnum.values()) {
            System.out.println(status + " = " + status.value);
        }

    }

}
