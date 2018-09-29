package it.dantar.games.maze.rest;

import java.util.ArrayList;
import java.util.List;

public class MazeGridDto {

	private String name;
	private int sizeX;
	private int sizeY;
	private List<MazeRoomDto> rooms = new ArrayList<>();
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSizeX() {
		return sizeX;
	}
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	public int getSizeY() {
		return sizeY;
	}
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	public List<MazeRoomDto> getRooms() {
		return rooms;
	}
	public void setRooms(List<MazeRoomDto> rooms) {
		this.rooms = rooms;
	}
	
}
