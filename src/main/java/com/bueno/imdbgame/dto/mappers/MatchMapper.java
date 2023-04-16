package com.bueno.imdbgame.dto.mappers;

import com.bueno.imdbgame.dto.MatchVO;
import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.model.entities.Match;
import com.bueno.imdbgame.model.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MatchMapper {
    MatchVO toRecord(Match entity);
    List<MatchVO> toRecord(Iterable<Match> entities);

    Match fromRecord(MatchVO vo);
    List<Match> fromRecord(Iterable<MatchVO> vos);

}
