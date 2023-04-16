package com.bueno.imdbgame.testutils;


import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.model.entities.Movie;
import com.github.javafaker.Faker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class MockMovie {

    public static Movie mockEntity() {
        Faker faker = new Faker(new Locale("pt-BR"));
        return Movie.builder()
                .id(faker.random().nextLong())
                .title(faker.book().title())
                .releaseDate(Calendar.getInstance())
                .popularity(faker.random().nextInt(1, 10))
                .voteAverage(faker.random().nextInt(1, 10))
                .voteCounting(faker.random().nextInt(1, 10000))
                .build();
    }

    public static MovieVO mockVO() {
        Movie movie = mockEntity();
        return new MovieVO(movie.getId(), movie.getTitle(), movie.getReleaseDate(),
                movie.getPopularity(), mockVO().voteAverage(), movie.getVoteCounting());
    }

    public static List<Movie> mockEntityList() {
        List<Movie> entities = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            entities.add(mockEntity());
        }
        return entities;
    }

    public static List<MovieVO> mockVOList() {
        List<MovieVO> vos = new ArrayList<>();
        for (long i = 0; i < 20; i++) {
            vos.add(mockVO());
        }
        return vos;
    }

}
