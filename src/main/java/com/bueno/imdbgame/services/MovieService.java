package com.bueno.imdbgame.services;

import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.dto.mappers.MovieMapper;
import com.bueno.imdbgame.exceptions.NotFoundException;
import com.bueno.imdbgame.model.entities.Movie;
import com.bueno.imdbgame.model.repositories.MovieRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Random;

@Log4j2
@Service
public class MovieService {

    private MovieRepository movieRepository;
    private MovieMapper movieMapper;

    public MovieService(MovieRepository movieRepository, MovieMapper movieMapper) {
        this.movieRepository = movieRepository;
        this.movieMapper = movieMapper;
    }

    public Page<MovieVO> findALlPageable(Pageable pageable) {
        log.info("Finding ALL movies pageable");
        Page<Movie> pageMovie = movieRepository.findAll(pageable);
        return pageMovie.map(movie -> movieMapper.toRecord(movie));
    }

    public MovieVO findById(Long id) {
        return movieMapper.toRecord(findMovieEntityById(id));
    }
    Movie findMovieEntityById(Long id) {
        log.info("Finding movies by id {}", id);
        return movieRepository.findById(id).orElseThrow((() -> {
            log.info("User not found with id {}",id);
            return new NotFoundException("user not found" +id);
        }));
    }

    Movie findRandomMovie() {
        int random = (int)(Math.random() * movieRepository.count());
        Page<Movie> movie = movieRepository.findAll(PageRequest.of(random, 1));
        return movie.getContent().get(0);
    }

}
