package com.hamlet.store.comment;

import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.game.Game;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity

public class Comment  extends BaseEntity {
    private String comment;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;
}
