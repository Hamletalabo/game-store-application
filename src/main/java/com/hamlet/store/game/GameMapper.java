package com.hamlet.store.game;

import com.hamlet.store.category.Category;

public class GameMapper {
    public Game toGame(GameRequest gameRequest) {
        return Game.builder()
                .title(gameRequest.title())
                .category(Category.builder()
                        .id(gameRequest.categoryId())
                        .build()
                        )
                .build();
    }
}
