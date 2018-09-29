package it.dantar.games.maze.master;

import it.dantar.games.maze.grid.MazeRoom;

public interface ChambersBuilder {

	void setEntranceChamber(MazeRoom room);
	void addCorridorChamber(MazeRoom room);
	void addCrossroadChamber(MazeRoom room);
	void setCriticalPathChamber(MazeRoom room);
	void addDeadEndChamber(MazeRoom room);
	void setExitChamber(MazeRoom room);
	
}
