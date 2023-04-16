package com.bueno.imdbgame.model.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@RequiredArgsConstructor
@ToString
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Entity
@Table(name = "permissions")
public class Permission implements GrantedAuthority, Serializable {
    @Serial
    private static final long serialVersionUID = 7776510672802683351L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
    @Column(name = "description")
    private String description;

    @Override
    public String getAuthority() {
        return this.description;
    }

}
