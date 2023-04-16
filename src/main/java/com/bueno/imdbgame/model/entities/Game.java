package com.bueno.imdbgame.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Games")
public class Game implements Serializable {
    @Serial
    private static final long serialVersionUID = 7776510671232683369L;
    @Id
    @ToString.Include
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @ToString.Include
    @Column(name = "game_date")
    private Calendar matchDate = Calendar.getInstance();
    @ToString.Include
    @Column(name = "end_game")
    private Boolean endGame = Boolean.FALSE;
    @ToString.Include
    @Column(name = "score", nullable = false)
    private Integer score = 0;
    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_user")
    private User userMatch;
    @OneToMany(mappedBy = "game", fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, orphanRemoval = true)
    private List<Match> matches = new ArrayList<>();

    public void addMatch(Match match) {
        if (match.getCorrectAnswer()) {
            score++;
        }
        match.setGame(this);
        matches.add(match);
    }

}
