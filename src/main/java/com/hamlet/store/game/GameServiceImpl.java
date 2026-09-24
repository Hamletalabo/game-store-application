package com.hamlet.store.game;

import com.hamlet.store.category.CategoryRepository;
import com.hamlet.store.comment.CommentRepository;
import com.hamlet.store.common.PageResponse;
import com.hamlet.store.platform.Console;
import com.hamlet.store.platform.Platform;
import com.hamlet.store.platform.PlatformRepository;
import com.hamlet.store.wishlist.WishlistRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final CategoryRepository categoryRepository;
    private final CommentRepository commentRepository;
    private final WishlistRepository wishlistRepository;
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

        return savedGame.getId();
    }

    @Override
    public void updateGame(String gameId, GameRequest gameRequest) {
       final Game game = gameRepository.findById(gameId).orElseThrow(
                ()-> new RuntimeException("Game not found"));
        if (game.getTitle().equals(gameRequest.title()) && gameRepository.existsByTitle(gameRequest.title())){
            log.info("Game already exists: {}", gameRequest.title());

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

        final List<String> platformIds  = platforms.stream()
                .map(Platform::getId)
                .collect(Collectors.toList());

        List<Platform> currentPlatforms = game.getPlatforms();
        List<Platform> newPlatforms = platformRepository.findAllById(platformIds);
        List<Platform> platformsToAdd = new ArrayList<>(newPlatforms);
        platformsToAdd.removeAll(currentPlatforms);

        List<Platform> platformsToRemove = new ArrayList<>(currentPlatforms);
        platformsToRemove.removeAll(newPlatforms);

        for (Platform platform: platformsToAdd){
            game.addPlatform(platform);
        }
        for (Platform platform: platformsToRemove){
            game.removePlatform(platform);
        }

        game.setTitle(gameRequest.title());
        gameRepository.save(game);

    }

    @Override
    public String uploadGameImage(MultipartFile multipartFile, String gameId) {
        return "";
    }

    @Override
    public PageResponse<GameResponse> findAllGames(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<Game> gamePage = gameRepository.findAll(pageable);
        List<GameResponse> gameResponses = gamePage.stream()
                .map(this.gameMapper::toGameResponse)
                .toList();
        return PageResponse.<GameResponse>builder()
                .content(gameResponses)
                .pageNumber(gamePage.getNumber())
                .size(gamePage.getSize())
                .totalElements(gamePage.getTotalElements())
                .totalPages(gamePage.getTotalPages())
                .isFirst(gamePage.isFirst())
                .isLast(gamePage.isLast())
                .build();
    }

    @Transactional
    @Override
    public void deleteGame(String gameId, boolean confirm) {

        Long commentCount = commentRepository.countByGameId(gameId);
        Long wishListCount = wishlistRepository.countByGameId(gameId);

        final List<String> warnings = new ArrayList<>();

        if (commentCount > 0){
            System.out.println("The current game has comments: " + commentCount);
        }
        if (wishListCount > 0){
            warnings.add("Wish list count is greater than 0");
            System.out.println("The current game has wishlist: " + wishListCount);
        }
        if (warnings.size() > 0 && !confirm ){
            throw new RuntimeException("one or more warnings");
        }

        gameRepository.deleteById(gameId);

    }

}
