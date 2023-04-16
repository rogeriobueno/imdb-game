package com.bueno.imdbgame.model.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "movies")
public class Movie implements Serializable {
    @Serial
    private static final long serialVersionUID = 4138806363402662499L;
    @Id
    @EqualsAndHashCode.Include
    @Column(name = "id")
    private Long id;
    @Column(name = "title")
    private String title;
    @Column(name = "release_date")
    private Calendar releaseDate;
    @Column(name = "popularity")
    private Integer popularity;
    @Column(name = "vote_average")
    private Integer voteAverage;
    @Column(name = "vote_counting")
    private Integer voteCounting;
}
