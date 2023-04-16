package com.bueno.imdbgame.dto;

import com.bueno.imdbgame.model.entities.Game;
import com.bueno.imdbgame.model.entities.Movie;
import com.bueno.imdbgame.model.enums.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"id", "game", "first_movie", "second_movie", "choose_movie", "correct_movie"})
public record MatchVO(
        Long id,
        Game game,
        @JsonProperty("first_movie") Movie firstMovie,
        @JsonProperty("second_movie") Movie secondMovie,
        @JsonProperty("select_movie") Position selectMovie,
        @JsonProperty("correct_movie") Position correctMovie,
        @JsonIgnore Boolean correctAnswer) {
}
