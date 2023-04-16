package com.bueno.imdbgame.services;

import com.bueno.imdbgame.dto.GameVO;
import com.bueno.imdbgame.dto.TurnVO;
import com.bueno.imdbgame.dto.UserVO;
import com.bueno.imdbgame.dto.mappers.GameMapper;
import com.bueno.imdbgame.dto.mappers.MovieMapper;
import com.bueno.imdbgame.exceptions.GameException;
import com.bueno.imdbgame.exceptions.NotFoundException;
import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.Match;
import com.bueno.imdbgame.model.entities.Movie;
import com.bueno.imdbgame.model.entities.User;
import com.bueno.imdbgame.model.enums.Position;
import com.bueno.imdbgame.model.repositories.GameRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Log4j2
@Service
public class GameService {

    private GameRepository gameRepository;
    private MovieMapper movieMapper;
    private GameMapper gameMapper;
    private UserService userService;
    private MovieService movieService;

    public GameService(GameRepository gameRepository, MovieMapper movieMapper, GameMapper gameMapper, UserService userService, MovieService movieService) {
        this.gameRepository = gameRepository;
        this.movieMapper = movieMapper;
        this.gameMapper = gameMapper;
        this.userService = userService;
        this.movieService = movieService;
    }

    public Page<GameVO> findAll(Pageable pageable) {
        log.info("findAll");
        Page<Game> pageGame = gameRepository.findAll(pageable);
        return pageGame.map(game -> gameMapper.toRecord(game));
    }

    public Page<GameVO> findAllSortedByScore(Pageable pageable) {
        log.info("Finding ALL movies pageable");
        Page<Game> pageMovie = gameRepository.findAll(
                PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                        Sort.by(Sort.Order.desc("score"))));
        return pageMovie.map(game -> gameMapper.toRecord(game));
    }

    public GameVO startGame(UserVO userVO) {
        log.info("Start game {}", userVO);

        User user = userService.findById(userVO.id());

        Game newGame = new Game();
        newGame.setUserMatch(user);

        Game savedGame = gameRepository.save(newGame);
        return gameMapper.toRecord(savedGame);
    }

    public GameVO endGame(GameVO gameVO) {
        log.info("End game {}", gameVO);

        Game game = findGameEntityById(gameVO.id());

        if (game.getEndGame()) {
            throw new GameException(String.format("Game ended id %s ", game.getId()));
        }

        game.setEndGame(Boolean.TRUE);
        Game savedGame = gameRepository.save(game);

        return gameMapper.toRecord(savedGame);
    }

    public TurnVO startMatch(Long gameId) {
        log.info("Start a match turn {} ", gameId);

        Game game = findGameEntityById(gameId);

        Movie firstMovie, secondMovie;
        do {
            firstMovie = uniqueMovieSelected(game);
            secondMovie = uniqueMovieSelected(game);
        } while (firstMovie.equals(secondMovie));

        return new TurnVO(game.getId(),
                movieMapper.toRecord(firstMovie),
                movieMapper.toRecord(secondMovie),
                null);
    }

    private Movie uniqueMovieSelected(Game game) {
        Movie randomMovie = movieService.findRandomMovie();
        if (game.getMatches().stream().noneMatch(match ->
                match.getFirstMovie().equals(randomMovie) ||
                        match.getSecondMovie().equals(randomMovie))) {
            return randomMovie;
        } else
            return uniqueMovieSelected(game);
    }

    public GameVO executeMatch(TurnVO turnVO) {
        log.info("Execute a match turn {} ", turnVO);

        Game game = findGameEntityById(turnVO.gameId());
        if (game.getEndGame()) {
            throw new GameException(String.format("Game ended id %s ", game.getId()));
        }

        Movie firstMovie = movieService.findMovieEntityById(turnVO.firstMovie().id());
        Movie secondMovie = movieService.findMovieEntityById(turnVO.secondMovie().id());

        Match match = new Match();
        match.setFirstMovie(firstMovie);
        match.setSecondMovie(secondMovie);
        match.setSelectMovie(turnVO.selected());
        match.setCorrectMovie(findCorrectMovie(firstMovie, secondMovie));
        game.addMatch(match);
        Game gameSaved = gameRepository.save(game);
        return gameMapper.toRecord(gameSaved);
    }

    private static Position findCorrectMovie(Movie firstMovie, Movie secondMovie) {
        if(!firstMovie.getVoteAverage().equals(secondMovie.getVoteAverage())){
            return firstMovie.getVoteAverage() > secondMovie.getVoteAverage() ?
                    Position.FIRST : Position.SECOND;
        } else {
            return firstMovie.getVoteCounting() > secondMovie.getVoteCounting() ?
                    Position.FIRST : Position.SECOND;
        }
    }

    public GameVO findById(Long id) {
        return gameMapper.toRecord(findGameEntityById(id));
    }

    Game findGameEntityById(Long id) {
        return gameRepository.findById(id).orElseThrow(() -> {
            log.info("Game not found with id {}", id);
            return new NotFoundException("Game not found " + id);
        });
    }


}
