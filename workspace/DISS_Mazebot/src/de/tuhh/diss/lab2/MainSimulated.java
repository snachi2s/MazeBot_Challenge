package de.tuhh.diss.lab2;

import MazebotSim.MazebotSimulation;
import MazebotSim.Visualization.GuiMazeVisualization;

public class MainSimulated {
	
	/**
	 *  This class is only intended to set up and start a simulation. Don't put
	 *  task specific information into this class. Just call the main function,
	 *  e.g., Task1.main() of the program you want to simulate in here. 
	 *  
	 *  You may, however, change the maze, or starting position here. For detailed
	 *  information about the simulator and how it can be configured, visit
	 *  https://collaborating.tuhh.de/e-24/public/courses/diss/diss-mazebot-simulation/-/wikis/home
	 */
	public static void main(String[] args) {
		// see mazes/README.txt for how to change the maze
		MazebotSimulation sim = new MazebotSimulation("mazes/TestArea.png", 1.5,  1.5); // (map_file, width [m], height [m])
		GuiMazeVisualization gui = new GuiMazeVisualization(1.5, sim.getStateAccessor());
		sim.scaleSpeed(1); // simulation speed, 1 = "real" time

		// start position (x-coordinate [m], y-position [m], orientation [deg])
		sim.setRobotPosition(0.75, 0.75, 90); 
		
		sim.startSimulation();
		gui.startVisualization();
				
		Task1.main(args);
		Task2.main(args);
		Task3.main(args);
				
		sim.stopSimulation();
	}

}
