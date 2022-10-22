package br.com.test.centralservico.centralservicoapitest.domain.enums;

import lombok.Getter;

@Getter
public enum PriorityTicketEnum {

    LOW("Baixa", 0),
    MEDIUM("Média", 1),
    HIGH("Alta", 2),
    HIGHEST("Altíssima", 3);

    private final String description;

    private final int value;

    private PriorityTicketEnum(String description, int value) {
        this.description = description;
        this.value = value;
    }

    public static PriorityTicketEnum getDescByValue(int value) {

        for(PriorityTicketEnum priority: PriorityTicketEnum.values())
            if(priority.value == value)
                return priority;

        return LOW;

    }

}
