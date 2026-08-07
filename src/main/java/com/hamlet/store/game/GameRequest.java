package com.hamlet.store.game;

import com.hamlet.store.platform.Platform;

import java.util.Set;

public record GameRequest(
        String title,
        String categoryId,
        Set<String> platforms
) {
}
