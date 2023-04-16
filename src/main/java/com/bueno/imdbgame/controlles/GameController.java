package com.bueno.imdbgame.controlles;

import com.bueno.imdbgame.dto.GameVO;
import com.bueno.imdbgame.dto.TurnVO;
import com.bueno.imdbgame.dto.UserVO;
import com.bueno.imdbgame.exceptions.BadRequestException;
import com.bueno.imdbgame.services.GameService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@Log4j2
@RestController
@RequestMapping(value = "/api/games")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping(value = "/start",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<GameVO> startGame(@RequestBody UserVO userVO) {
        return new ResponseEntity<>(gameService.startGame(userVO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/end", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<GameVO> endGame(@RequestBody GameVO gameVO) {
        return new ResponseEntity<>(gameService.endGame(gameVO), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{gameId}/turn", produces = MediaType.APPLICATION_JSON_VALUE)
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<TurnVO> startMatch(@PathVariable(value = "gameId") Long gameId) {
        return new ResponseEntity<>(gameService.startMatch(gameId), HttpStatus.CREATED);
    }

    @PostMapping(value = "/{gameId}/execute", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<GameVO> startMatch(@PathVariable(value = "gameId") Long gameId, @RequestBody TurnVO turnVO) {
        if (!Objects.equals(gameId, turnVO.gameId())) {
            throw new BadRequestException("Wrong game");
        }
        return new ResponseEntity<>(gameService.executeMatch(turnVO), HttpStatus.CREATED);
    }

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<Page<GameVO>> findAll(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            @RequestParam(value = "sort", defaultValue = "ASC") String sort
    ) {
        Sort.Direction order = sort.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        Pageable Pageable = PageRequest.of(page, size, Sort.by(order, "id"));
        Page<GameVO> games = gameService.findAll(Pageable);
        return ResponseEntity.ok(games);
    }

    @GetMapping(value = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    //@Operation(security = {@SecurityRequirement(name = "bearer-key")})
    public ResponseEntity<GameVO> findOne(@PathVariable(value = "id") Long gameId) {
        return ResponseEntity.ok(gameService.findById(gameId));
    }

}
