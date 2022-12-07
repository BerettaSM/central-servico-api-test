package br.com.test.centralservico.centralservicoapitest.domain.enums;

import lombok.Getter;

@Getter
public enum AuthorityEnum {

    ROLE_ADMIN(1),
    ROLE_ATTENDANT(2),
    ROLE_USER(3);

    private final Integer value;

    AuthorityEnum(Integer value) {

        this.value = value;

    }

}
