package com.bueno.imdbgame.services;

import com.bueno.imdbgame.dto.GameVO;
import com.bueno.imdbgame.dto.TurnVO;
import com.bueno.imdbgame.dto.UserVO;
import com.bueno.imdbgame.dto.mappers.GameMapper;
import com.bueno.imdbgame.dto.mappers.MovieMapper;
import com.bueno.imdbgame.exceptions.GameException;
import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.Movie;
import com.bueno.imdbgame.model.entities.User;
import com.bueno.imdbgame.model.enums.Position;
import com.bueno.imdbgame.model.repositories.GameRepository;
import com.bueno.imdbgame.model.repositories.MovieRepository;
import com.bueno.imdbgame.model.repositories.UserRepository;
import com.bueno.imdbgame.testutils.MockGame;
import com.bueno.imdbgame.testutils.MockMovie;
import com.bueno.imdbgame.testutils.MockUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class GameServiceTest {
    @Spy
    private GameMapper gameMapper = Mappers.getMapper(GameMapper.class);
    @Spy
    private MovieMapper movieMapper = Mappers.getMapper(MovieMapper.class);
    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private MovieRepository movieRepository;
    @Mock
    private MovieService movieService;
    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById() {
        Long id;
        Game game = MockGame.mockEntity();
        id = game.getId();
        Mockito.when(gameRepository.findById(id)).thenReturn(Optional.of(game));

        var result = gameService.findById(id);

        assertAll("methods validations",
                () -> assertNotNull(result)
        );
    }

    @Test
    void findAll() {
        List<Game> games = MockGame.mockEntityList();

        Mockito.when(gameRepository.findAll(PageRequest.of(0, 20)))
                .thenReturn(new PageImpl<>(games));

        var result = gameService.findAll(PageRequest.of(0, 20));

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(20, result.getTotalElements())
        );
    }

    @Test
    void startGame() {
        UserVO userVO = MockUser.mockVO();
        User user = MockUser.mockEntity();
        Game gameSaved = MockGame.mockEntity();
        gameSaved.setUserMatch(user);

        Mockito.lenient().when(userService.findById(Mockito.any(Long.class)))
                .thenReturn(user);
        Mockito.lenient().when(gameRepository.save(Mockito.any(Game.class)))
                .thenReturn(gameSaved);

        var result = gameService.startGame(userVO);

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(result.userMatch().id(), user.getId())
        );

    }

    @Test
    void shouldEndGameSuccess() {
        Game game = MockGame.mockEntity();
        game.setEndGame(Boolean.FALSE);
        GameVO gameVO = gameMapper.toRecord(game);

        Mockito.lenient().when(gameRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(game));
        Mockito.lenient().when(gameRepository.save(Mockito.any(Game.class)))
                .thenReturn(game);

        var result = gameService.endGame(gameVO);

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(result.score(), game.getScore())
        );

    }

    @Test
    void shouldThrowGameExceptionOnEndGameWithEndedGame() {
        Game game = MockGame.mockEntity();
        game.setEndGame(Boolean.TRUE); //Exception

        GameVO gameVO = gameMapper.toRecord(game);

        Mockito.lenient().when(gameRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(game));
        Mockito.lenient().when(gameRepository.save(Mockito.any(Game.class)))
                .thenReturn(game);

        Exception exception = assertThrows(GameException.class, () -> gameService.endGame(gameVO));
    }

    @Test
    void startMatch() {
        Game game = MockGame.mockEntity();
        Movie movieFirst = MockMovie.mockEntity();
        Movie moviesSecond = MockMovie.mockEntity();

        Mockito.lenient().when(movieRepository.count())
                .thenReturn(10L);
        Mockito.lenient().when(gameRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(game));
        Mockito.lenient().when(movieService.findRandomMovie())
                .thenReturn(movieFirst)
                .thenReturn(movieFirst)
                .thenReturn(movieFirst)
                .thenReturn(moviesSecond);

        var result = gameService.startMatch(game.getId());

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(result.gameId(), game.getId())
        );
    }

    @Test
    void executeMatch() {
        Game game = MockGame.mockEntity();
        Movie movieFirst = MockMovie.mockEntity();
        Movie moviesSecond = MockMovie.mockEntity();

        Mockito.lenient().when(gameRepository.findById(Mockito.any(Long.class)))
                .thenReturn(Optional.of(game));
        Mockito.lenient().when(movieService.findMovieEntityById((Mockito.any(Long.class))))
                .thenReturn(movieFirst)
                .thenReturn(moviesSecond);
        Mockito.lenient().when(gameRepository.save(Mockito.any(Game.class)))
                .thenReturn(game);

        TurnVO turnVO = new TurnVO(game.getId(), movieMapper.toRecord(movieFirst), movieMapper.toRecord(moviesSecond),
                Position.FIRST);

        var result = gameService.executeMatch(turnVO);

        assertAll("methods validations",
                () -> assertNotNull(result),
                () -> assertEquals(result.id(), game.getId())
        );
    }

}