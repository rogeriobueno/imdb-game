package com.bueno.imdbgame.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.Calendar;

@JsonPropertyOrder({"id", "full_name", "user_name"})
public record UserVO(Long id,
                     @JsonProperty("full_name") String fullName,
                     @JsonProperty("user_name")String userName) {
}
