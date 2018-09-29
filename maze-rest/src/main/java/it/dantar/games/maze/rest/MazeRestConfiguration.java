package it.dantar.games.maze.rest;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import it.dantar.games.maze.grid.MazeGridConfiguration;
import it.dantar.games.maze.master.MazeMasterConfiguration;

@Configuration
@Import({MazeGridConfiguration.class, MazeMasterConfiguration.class})
public class MazeRestConfiguration {

}
