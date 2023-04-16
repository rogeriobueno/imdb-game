package com.bueno.imdbgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Calendar;

@JsonPropertyOrder({"id", "match_date", "game_ended", "score", "user_match", "score"})
public record GameVO(
        Long id,
        @JsonProperty("match_date") Calendar matchDate,
        @JsonProperty("game_ended") Boolean endGame,
        @JsonProperty("user_match") UserVO userMatch,
        Integer score) {
}


