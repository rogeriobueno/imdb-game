package com.bueno.imdbgame.testutils;


import com.bueno.imdbgame.dto.MatchVO;
import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.Match;
import com.bueno.imdbgame.model.enums.Position;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MockMatch {

    public static Match mockEntity() {
        Faker faker = new Faker(new Locale("pt-BR"));
        MockMovie mockMovie = new MockMovie();
        return Match.builder()
                .id(faker.random().nextLong())
                .game(Game.builder().build())
                .correctMovie(Position.FIRST)
                .selectMovie(Position.SECOND)
                .firstMovie(mockMovie.mockEntity())
                .secondMovie(mockMovie.mockEntity())
                .build();
    }

    public static MatchVO mockVO() {
        Match match = mockEntity();
        return new MatchVO(match.getId(), match.getGame(), match.getFirstMovie(), match.getSecondMovie(),
                match.getSelectMovie(), match.getCorrectMovie(), match.getCorrectAnswer());
    }

    public static List<Match> mockEntityList() {
        List<Match> entities = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            entities.add(mockEntity());
        }
        return entities;
    }

    public static List<MatchVO> mockVOList() {
        List<MatchVO> vos = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            vos.add(mockVO());
        }
        return vos;
    }

}
