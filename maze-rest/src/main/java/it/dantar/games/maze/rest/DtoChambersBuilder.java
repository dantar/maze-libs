package it.dantar.games.maze.rest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.dantar.games.maze.grid.MazeGrid;
import it.dantar.games.maze.grid.MazeMap;
import it.dantar.games.maze.grid.MazeRoom;
import it.dantar.games.maze.master.ChambersBuilder;

public class DtoChambersBuilder implements ChambersBuilder {

	MazeGridDto grid;
	MazeGrid maze;
	Map<MazeRoom, MazeRoomDto> dtos = new HashMap<>();
	List<MazeRoom> rooms = new ArrayList<>();

	public DtoChambersBuilder(MazeGrid maze) {
		super();
		this.maze = maze;
		grid = new MazeGridDto();
		grid.setSizeX(maze.getSizeX());
		grid.setSizeY(maze.getSizeY());
	}

	@Override
	public void setEntranceChamber(MazeRoom room) {
		MazeRoomDto dto = this.dtos.get(room);
		dto.getKeywords().add("entrance");
	}

	@Override
	public void addCorridorChamber(MazeRoom room) {
		addCorridor(room);
	}

	@Override
	public void addCrossroadChamber(MazeRoom room) {
		for (MazeRoom mazeRoom : new MazeMap(this.maze).connectedRooms(room)) {
			if (this.rooms.contains(mazeRoom)) {
				addCorridor(room);
				return;
			}
		}
		this.rooms.add(room);
		addRoom(room);
	}

	@Override
	public void setCriticalPathChamber(MazeRoom room) {
		MazeRoomDto dto = this.dtos.get(room);
	}

	@Override
	public void addDeadEndChamber(MazeRoom room) {
		MazeRoomDto dto = addRoom(room);
	}

	@Override
	public void setExitChamber(MazeRoom room) {
		MazeRoomDto dto = this.dtos.get(room);
		dto.getKeywords().add("skeleton");
		dto.getKeywords().add("exit");
	}

	public MazeGridDto getGrid() {
		Collections.shuffle(this.rooms);
		for (int i = 0; i < Math.min(rooms.size(), 12); i++) {			
			this.dtos.get(this.rooms.get(i)).getKeywords().add("skeleton");
		}
		return grid;
	}

	public void setGrid(MazeGridDto grid) {
		this.grid = grid;
	}

	private MazeRoomDto addRoom(MazeRoom mazeRoom) {
		return newMazeRoomDto(mazeRoom, "room");
	}

	private MazeRoomDto addCorridor(MazeRoom mazeRoom) {
		return newMazeRoomDto(mazeRoom, "corridor");
	}

	private MazeRoomDto newMazeRoomDto(MazeRoom mazeRoom, String shape) {
		MazeRoomDto room = new MazeRoomDto();
		room.setNorth(maze.hasRoom(mazeRoom.northRoom()) && !maze.hasWall(mazeRoom.northWall()));
		room.setSouth(maze.hasRoom(mazeRoom.southRoom()) && !maze.hasWall(mazeRoom.southWall()));
		room.setEast(maze.hasRoom(mazeRoom.eastRoom()) && !maze.hasWall(mazeRoom.eastWall()));
		room.setWest(maze.hasRoom(mazeRoom.westRoom()) && !maze.hasWall(mazeRoom.westWall()));
		room.setPosX(mazeRoom.getX()/2);
		room.setPosY(mazeRoom.getY()/2);
		grid.getRooms().add(room);
		room.setShape(shape);
		this.dtos.put(mazeRoom, room);
		return room;
	}
	
}
