package com.bueno.imdbgame.dto;

import com.bueno.imdbgame.model.enums.Position;

public record TurnVO(Long gameId, MovieVO firstMovie, MovieVO secondMovie, Position selected) {
}