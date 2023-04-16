package com.bueno.imdbgame.services;

import com.bueno.imdbgame.dto.mappers.MovieMapper;
import com.bueno.imdbgame.model.entities.Movie;
import com.bueno.imdbgame.model.repositories.MovieRepository;
import com.bueno.imdbgame.testutils.MockMovie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class MovieServiceTest {
    @Spy
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);
    @InjectMocks
    private MovieService movieService;
    @Mock
    private MovieRepository movieRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Long id;
        Movie movie = MockMovie.mockEntity();
        id = movie.getId();
        Mockito.when(movieRepository.findById(id)).thenReturn(Optional.of(movie));

        var result = movieService.findById(id);

        assertAll("methods validations",
                () -> assertNotNull(result)
        );
    }

    @Test
    void findRandomMovie() {
        List<Movie> moviesA = new ArrayList<>();
        Movie movieA = MockMovie.mockEntity();
        moviesA.add(movieA);

        List<Movie> moviesB = new ArrayList<>();
        Movie movieB = MockMovie.mockEntity();
        moviesB.add(movieB);

        Mockito.when(movieRepository.findAll(PageRequest.of(0, 1)))
                .thenReturn(new PageImpl<>(moviesA))
                .thenReturn(new PageImpl<>(moviesB));

        var result = movieService.findRandomMovie();

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(result.getId(), movieA.getId())
        );
    }
}