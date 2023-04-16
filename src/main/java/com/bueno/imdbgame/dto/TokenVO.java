package com.bueno.imdbgame.dto;

import java.util.Date;
public record TokenVO(String username, Boolean authenticated, Date created, Date expiration,
                      String accessToken, String refreshToken) {

}
