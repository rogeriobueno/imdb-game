package com.bueno.imdbgame.dto.mappers;

import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.model.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    MovieVO toRecord(Movie entity);
    List<MovieVO> toRecord(Iterable<Movie> entities);

    Movie fromRecord(MovieVO vo);
    List<Movie> fromRecord(Iterable<MovieVO> vos);

}
