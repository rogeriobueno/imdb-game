package com.bueno.imdbgame.controlles;

import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.services.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequestMapping(value = "/api/movies")
public class MovieController {

    private MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Page<MovieVO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort
    ) {
        Sort.Direction order = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable Pageable = PageRequest.of(page, size, Sort.by(order, "title"));
        Page<MovieVO> movies = movieService.findALlPageable(Pageable);
        return ResponseEntity.ok(movies);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<MovieVO> findById(@PathVariable(value = "id") Long id) {
        return ResponseEntity.ok(movieService.findById(id));
    }


}
