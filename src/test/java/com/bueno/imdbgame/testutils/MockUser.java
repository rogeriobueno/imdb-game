package com.bueno.imdbgame.testutils;


import com.bueno.imdbgame.dto.UserVO;
import com.bueno.imdbgame.model.entities.Permission;
import com.bueno.imdbgame.model.entities.User;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockUser {

    public static User mockEntity() {
        Faker faker = new Faker(new Locale("pt-BR"));
        return User.builder()
                .id(faker.random().nextLong())
                .userName(faker.name().username())
                .fullName(faker.name().fullName())
                .enabled(Boolean.TRUE)
                .password("pass")
                .permissions(List.of(new Permission()))
                .build();
    }

    public static UserVO mockVO() {
        User user = mockEntity();
        return new UserVO(user.getId(), user.getFullName(), user.getUsername());
    }

    public static List<User> mockEntityList() {
        List<User> entities = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            entities.add(mockEntity());
        }
        return entities;
    }

    public static List<UserVO> mockVOList() {
        List<UserVO> vos = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            vos.add(mockVO());
        }
        return vos;
    }

}
