package com.hamlet.store.game;

import com.hamlet.store.category.CategoryRepository;
import com.hamlet.store.common.PageResponse;
import com.hamlet.store.platform.Console;
import com.hamlet.store.platform.Platform;
import com.hamlet.store.platform.PlatformRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final GameMapper gameMapper;

    @Override
    public String save(final GameRequest gameRequest) {

        if (gameRepository.existsByTitle(gameRequest.title())){
            log.info("Game already exist.");
            throw new RuntimeException("Game already exists");
        }

        final List< Console> selectedConsoles = gameRequest.platforms()
                .stream()
                .map(p -> Console.valueOf(p))
                .toList();

        final List<Platform> platforms = platformRepository.findAllByConsoleIn(selectedConsoles);
        if (platforms.size() != selectedConsoles.size()){
            log.warn("Received a non supported platforms. Received: {} - Stored: {}", selectedConsoles, platforms);
            throw new RuntimeException("One or more platforms are not supported");
        }
        if (!categoryRepository.existsById(gameRequest.categoryId())){
            log.warn("Received a category that does not exist: {}", gameRequest.categoryId());
            throw new RuntimeException("Category does not exist.");
        }
        final Game game = gameMapper.toGame(gameRequest);
        game.setPlatforms(platforms);
        final Game savedGame = gameRepository.save(game);

        return "";
    }

    @Override
    public void updateGame(String gameId, GameRequest gameRequest) {

    }

    @Override
    public String uploadGameImage(MultipartFile multipartFile, String gameId) {
        return "";
    }

    @Override
    public PageResponse<GameResponse> findAllGames(int page, int size) {
        return null;
    }

    @Override
    public void deleteGame(String gameId) {

    }
}
