package com.hamlet.store.wishlist;

import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.game.Game;
import com.hamlet.store.user.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Wishlist extends BaseEntity {

    private String name;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "wishlist", fetch = FetchType.EAGER)
    private List<Game> games;
}
