package com.bueno.imdbgame.dto.mappers;

import com.bueno.imdbgame.dto.GameVO;
import com.bueno.imdbgame.dto.MovieVO;
import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.Movie;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameVO toRecord(Game entity);
    List<GameVO> toRecord(Iterable<Game> entities);

    Game fromRecord(GameVO vo);
    List<Game> fromRecord(Iterable<GameVO> vos);

}
