package com.bueno.imdbgame.model.entities;

import com.bueno.imdbgame.model.enums.Position;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Matches")
public class Match implements Serializable {
    @Serial
    private static final long serialVersionUID = 7776123456432683369L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    private Game game;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "first_movie", nullable = false)
    private Movie firstMovie;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "second_movie", nullable = false)
    private Movie secondMovie;

    @Column(name = "select_movie", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Position selectMovie;
    @Column(name = "correct_movie", length = 10, nullable = false)
    @Enumerated(EnumType.STRING)
    private Position correctMovie;

    public Boolean getCorrectAnswer() {
        if (selectMovie == null || correctMovie == null) {
            return false;
        }
        return selectMovie.equals(correctMovie);
    }
}
