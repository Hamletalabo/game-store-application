package com.hamlet.store.game;

import com.hamlet.store.category.Category;
import com.hamlet.store.comment.Comment;
import com.hamlet.store.common.BaseEntity;
import com.hamlet.store.wishlist.Wishlist;
import jakarta.persistence.*;
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
public class Game extends BaseEntity {

    private String title;

    @Enumerated (EnumType.STRING)
    private SupportedPlatforms supportedPlatforms; //PC, XBOX, PLAYSTATION, NINTENDO, ETC.

    private String coverPicture;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "game")
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
}
