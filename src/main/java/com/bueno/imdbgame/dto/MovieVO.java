package com.bueno.imdbgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Calendar;

@JsonPropertyOrder({"id", "title", "release_date", "popularity", "vote_average", "vote_counting"})
public record MovieVO(Long id,
                      String title,
                      @JsonProperty("release_date") Calendar releaseDate,
                      Integer popularity,
                      @JsonProperty("vote_average") Integer voteAverage,
                      @JsonProperty("vote_counting") Integer voteCounting) {
}
