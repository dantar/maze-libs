package it.dantar.games.maze.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.dantar.games.maze.grid.MazeGeneratorService;
import it.dantar.games.maze.grid.MazeGrid;
import it.dantar.games.maze.grid.MazeMap;
import it.dantar.games.maze.master.MazeMasterService;

@RestController
@CrossOrigin
public class MazeRestController {
	
	@Autowired
	MazeGeneratorService mazeGeneratorService;
	
	@Autowired
	MazeMasterService mazeMasterService;
	
	@RequestMapping("/newMaze")
	public MazeGridDto newMaze(@RequestParam(required=false, defaultValue="10") int sizeX, @RequestParam(required=false, defaultValue="10") int sizeY) {
		MazeGrid maze = new MazeGrid(sizeX, sizeY);
		mazeGeneratorService.shuffleMaze(maze);
		DtoChambersBuilder builder = new DtoChambersBuilder(maze);
		mazeMasterService.buildChambers(new MazeMap(maze), builder);
		return builder.getGrid();
	}

}

		