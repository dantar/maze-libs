package it.dantar.games.maze.master;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.test.context.junit4.SpringRunner;

import it.dantar.games.maze.grid.MazeGrid;
import it.dantar.games.maze.grid.MazeMap;
import it.dantar.games.maze.grid.MazeRoom;
import it.dantar.games.maze.grid.MazeWall;

@RunWith(SpringRunner.class)
public class MazeMasterServiceTest {

	@Autowired
	MazeMasterService service;
	
	@Test
	public void testStartAndEnd() {
		MazeGrid maze = new MazeGrid(3, 3);
		service.placeStartAndEnd(maze);
	}

	@Test
	public void testPathOfRooms() {
		MazeGrid maze = new MazeGrid(3, 3);
		maze.addWall(maze.wall(1, 0));
		maze.addWall(maze.wall(3, 0));
		maze.addWall(maze.wall(0, 3));
		maze.addWall(maze.wall(3, 4));
		MazeMap map = new MazeMap(maze);
		List<MazeRoom> path = service.pathOfRooms(map, maze.room(0, 0), maze.room(4, 4));
		assertEquals(5, path.size());
		assertTrue(path.contains(maze.room(0, 0)));
		assertTrue(path.contains(maze.room(4, 4)));
	}

	@Test
	public void testPathOfWalls() {
		MazeGrid maze = new MazeGrid(3, 3);
		maze.addWall(maze.wall(1, 0));
		maze.addWall(maze.wall(3, 0));
		maze.addWall(maze.wall(0, 3));
		maze.addWall(maze.wall(3, 4));
		MazeMap map = new MazeMap(maze);
		List<MazeWall> walls = service.pathOfWalls(map, maze.room(0, 0), maze.room(4, 4));
		assertEquals(4, walls.size());
		assertTrue(walls.contains(maze.wall(0, 1)));
		assertTrue(walls.contains(maze.wall(1, 2)));
		assertTrue(walls.contains(maze.wall(3, 2)));
		assertTrue(walls.contains(maze.wall(4, 3)));
	}

	@Test
	public void testBuildChambers() {
		MazeGrid maze = new MazeGrid(3, 3);
		maze.addWall(maze.wall(1, 0));
		maze.addWall(maze.wall(3, 0));
		maze.addWall(maze.wall(0, 3));
		maze.addWall(maze.wall(3, 2));
		MazeMap map = new MazeMap(maze);
		final Map<MazeRoom, String> chambers = new HashMap<>();
		ChambersBuilder builder = new ChambersBuilder() {
			@Override
			public void setExitChamber(MazeRoom room) {
			}
			@Override
			public void setEntranceChamber(MazeRoom room) {
			}
			@Override
			public void addDeadEndChamber(MazeRoom room) {
				chambers.put(room, "dead end");
			}
			@Override
			public void addCrossroadChamber(MazeRoom room) {
				chambers.put(room, "crossroad");
			}
			@Override
			public void setCriticalPathChamber(MazeRoom room) {
				chambers.put(room, "critical path");
			}
			@Override
			public void addCorridorChamber(MazeRoom room) {
				chambers.put(room, "corridor");
			}
		};
		service.buildChambers(map, builder);
		assertEquals("dead end", chambers.get(maze.room(0, 0)));
	}

    @SpringBootApplication
    static class TestConfiguration {
    }
}
