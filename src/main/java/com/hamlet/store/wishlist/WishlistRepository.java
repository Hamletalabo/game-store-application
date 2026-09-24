package com.hamlet.store.wishlist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WishlistRepository extends JpaRepository<Wishlist, String> {

    @Query("SELECT COUNT (w) FROM WishList w JOIN w.games g WHERE g.id = :gameId")
    Long countByGameId(String gameId);
}
