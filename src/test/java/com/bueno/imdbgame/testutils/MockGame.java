package com.bueno.imdbgame.testutils;


import com.bueno.imdbgame.dto.GameVO;
import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.User;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MockGame {

    public static Game mockEntity() {
        Faker faker = new Faker(new Locale("pt-BR"));
        return Game.builder()
                .id(faker.random().nextLong())
                .score(faker.random().nextInt(1, 10))
                .endGame(Boolean.FALSE)
                .matchDate(Calendar.getInstance())
                .userMatch(User.builder().build())
                .matches(MockMatch.mockEntityList())
                .build();
    }

    public static GameVO mockVO() {
        Game game = mockEntity();
        return new GameVO(game.getId(), game.getMatchDate(), game.getEndGame(),
                MockUser.mockVO(), game.getScore());
    }

    public static List<Game> mockEntityList() {
        List<Game> entities = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            entities.add(mockEntity());
        }
        return entities;
    }

    public static List<GameVO> mockVOList() {
        List<GameVO> vos = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            vos.add(mockVO());
        }
        return vos;
    }

}
