package com.hamlet.store.game;

import com.hamlet.store.category.Category;
import com.hamlet.store.comment.Comment;
import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.platform.Console;
import com.hamlet.store.platform.Platform;
import com.hamlet.store.wishlist.Wishlist;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
public class Game extends BaseEntity {

    @Column(nullable = false, unique = true)
    private String title;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private List<Platform> platforms; //PC, XBOX, PLAYSTATION, NINTENDO, ETC.

    private String coverPicture;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "game", orphanRemoval = true)
    private List<Comment> comments;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable (name = "game_wishlist", joinColumns =
            {@JoinColumn(name = "game_id")},
            inverseJoinColumns = {@JoinColumn(
            name = "wishlist_id"
    )})
    private List<Wishlist> wishlists;

    public void addWishList(Wishlist wishlist){
        this.wishlists.add(wishlist);
        wishlist.getGames().add(this);
    }
    public void removeWishList(Wishlist wishlist){
        this.wishlists.remove(wishlist);
        wishlist.getGames().remove(this);
    }

    public void addPlatform(Platform platform){
        this.platforms.add(platform);
        platform.getGames().add(this);
    }
    public void removePlatform(Platform platform){
        this.platforms.remove(platform);
        platform.getGames().remove(this);
    }
}
