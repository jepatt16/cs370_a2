import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Simulation extends Thread {
	public static long avgQueueTime = 0;
	public static long avgTotalTime = 0;


	// a link to simulation panel
	private SimulationUI simulationPanel;

	// constructor that takes the simulation parameters
	public Simulation(int numworker, int qsize, int numrequest, int arate, int srate, SimulationUI simulationUI) {
		simulationPanel = simulationUI;

		// Create all the necessary threads
	}

	public void run() {
		// 1. start all the threads
		// 2. wait (join) all the threads
		// 3. compute the statistics (average queue time, average total time)

		// display the message
		simulationPanel.setConsole("Avg. Queueing Time: " + avgQueueTime + " us"+ "\nAvg. Total Time: " + avgTotalTime + " us");
		simulationPanel.setButtons(true);
	}

}
