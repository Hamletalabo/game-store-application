package com.hamlet.store.game;

import com.hamlet.store.category.Category;

import java.util.stream.Collectors;

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

    public GameResponse toGameResponse(Game game) {
        return GameResponse.builder()
                .id(game.getId())
                .title(game.getTitle())
                .imageUrl("fix me")
                .platforms(game.getPlatforms()
                        .stream()
                        .map(p -> p.getConsole().name())
                        .collect(Collectors.toSet()))
                .build();

    }
}
