package com.hamlet.store.game;

import com.hamlet.store.common.PageResponse;
import org.springframework.web.multipart.MultipartFile;

public interface GameService {

    String save (GameRequest gameRequest);

    void updateGame(String gameId, GameRequest gameRequest);

    String uploadGameImage(MultipartFile multipartFile, String gameId);

    PageResponse<GameResponse> findAllGames(int page, int size);

    void deleteGame(String gameId, boolean confirm);
;
}
