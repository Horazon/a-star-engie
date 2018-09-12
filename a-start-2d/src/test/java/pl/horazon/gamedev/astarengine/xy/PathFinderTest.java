package pl.horazon.gamedev.astarengine.xy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import pl.horazon.gamedev.astarengine.path.PathFinder;
import pl.horazon.gamedev.astarengine.xy.GameBoard2D;
import pl.horazon.gamedev.astarengine.xy.Poind2D;


public class PathFinderTest {

    @Test
    void shouldFindPath(){
        Poind2D start = new Poind2D(9, 7);
        Poind2D stop = new Poind2D(9, 3);

        GameBoard2D gameBoard = new GameBoard2D("map.txt");
        gameBoard.loadMap();
        
        PathFinder<Poind2D, GameBoard2D> pathFinder = new PathFinder<>(start, stop, gameBoard);
        pathFinder.run();

        Assertions.assertThat(pathFinder.getResult()).isEqualTo(PathFinder.Result.PATH_FOUND);
    }

    @Test
    void shouldNotFindPath(){

        Poind2D start = new Poind2D(1, 8);
        Poind2D stop = new Poind2D(9, 3);

        GameBoard2D gameBoard = new GameBoard2D("map-no-path.txt");
        gameBoard.loadMap();

        PathFinder<Poind2D, GameBoard2D> pathFinder = new PathFinder<>(start, stop, gameBoard);
        pathFinder.run();

        Assertions.assertThat(pathFinder.getResult()).isEqualTo(PathFinder.Result.PATH_NOT_FOUND);
    }
}
