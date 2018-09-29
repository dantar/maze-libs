package it.dantar.games.maze.master;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import it.dantar.games.maze.grid.MazeGrid;
import it.dantar.games.maze.grid.MazeMap;
import it.dantar.games.maze.grid.MazeRoom;
import it.dantar.games.maze.grid.MazeWalker;
import it.dantar.games.maze.grid.MazeWalker.RoomVisitor;
import it.dantar.games.maze.grid.MazeWalker.RoomVisitor.AbortVisit;
import it.dantar.games.maze.grid.MazeWall;

@Service
public class MazeMasterService {

	public void placeStartAndEnd(MazeGrid maze) {
		
	}

	public List<MazeRoom> pathOfRooms(MazeMap map, MazeRoom start, MazeRoom finish) {
		final List<MazeRoom> result = new ArrayList<>();
		MazeWalker walker = new MazeWalker();
		try {
			walker.walkAllRooms(map, start, new RoomVisitor() {
				@Override
				public void visit(MazeRoom room) {
					result.add(room);
					if (room.equals(finish)) throw new AbortVisit();
				}
				@Override
				public void leave(MazeRoom room) {
					result.remove(room);
				}
			});
		} catch (AbortVisit e) {
			// visit complete
		}
		return result;
	}

	public List<MazeWall> pathOfWalls(MazeMap map, MazeRoom start, MazeRoom finish) {
		List<MazeRoom> rooms = this.pathOfRooms(map, start, finish);
		MazeRoom current = rooms.remove(0);
		List<MazeWall> result = new ArrayList<>();
		while (!rooms.isEmpty()) {
			MazeRoom next = rooms.remove(0);
			result.add(current.wallTo(next));
			current = next;
		}
		return result ;
	}

	public void buildChambers(MazeMap map, ChambersBuilder builder) {
		final List<MazeRoom> deadEnds = new ArrayList<MazeRoom>();
		final List<MazeRoom> crossRoads = new ArrayList<MazeRoom>();
		MazeWalker walker = new MazeWalker();
		RoomVisitor visitor = new RoomVisitor() {

			@Override
			public void visit(MazeRoom room) {
				switch (map.connectedRooms(room).size()) {
				case 1:					
					deadEnds.add(room);
					builder.addDeadEndChamber(room);
					break;
				case 2:					
					deadEnds.add(room);
					builder.addCorridorChamber(room);
					break;
				default:
					crossRoads.add(room);
					builder.addCrossroadChamber(room);
					break;
				}
			}

			@Override
			public void leave(MazeRoom room) {
			}
			
		};
		walker.walkAllRooms(map, map.room(1, 1), visitor );
		int index = 0;
		int longestSize = 0;
		MazeRoom longestFrom = null;
		MazeRoom longestTo = null;
		while (index<deadEnds.size()) {
			MazeRoom fromRoom = deadEnds.get(index);
			for (int i = index+1; i < deadEnds.size(); i++) {
				int currentSize = pathOfRooms(map, deadEnds.get(index), deadEnds.get(i)).size();
				if (currentSize > longestSize) {
					longestFrom = deadEnds.get(index);
					longestTo = deadEnds.get(i);
					longestSize = currentSize;
				}
			}
			index ++;
		}
		builder.setEntranceChamber(longestFrom);
		builder.setExitChamber(longestTo);
		for (MazeRoom mazeRoom : pathOfRooms(map, longestFrom, longestTo)) {
			builder.setCriticalPathChamber(mazeRoom);
		}
	}

}
