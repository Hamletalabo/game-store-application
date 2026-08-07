package com.hamlet.store.game;

import com.hamlet.store.common.PageResponse;
import com.hamlet.store.platform.Console;
import com.hamlet.store.platform.Platform;
import com.hamlet.store.platform.PlatformRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GameRepository gameRepository;
    private final PlatformRepository platformRepository;
    private final GameMapper gameMapper;

    @Override
    public String save(final GameRequest gameRequest) {

        final Set< Console> selectedConsoles = gameRequest.platforms()
                .stream()
                .map(p -> Console.valueOf(p))
                .collect(Collectors.toSet());

        final Set<Platform> platforms = platformRepository.findAllByConsoleIn(selectedConsoles);

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
