package br.com.test.centralservico.centralservicoapitest.util;

import br.com.test.centralservico.centralservicoapitest.domain.enums.AuthorityEnum;
import br.com.test.centralservico.centralservicoapitest.domain.model.User;

public class AuthorityUtil {

    public static boolean hasRole(AuthorityEnum role, User user) {

        return user.getAuthorities()
                .stream()
                .anyMatch(auth -> auth.getAuthority().equals(role.name()));

    }

}
