package lifecounter.model;

import java.io.Serializable;

public class Team implements Serializable {
	
	private static final long serialVersionUID = 3868670797047528608L;
	
	private Counter[] teamCounters;
	
	public Team(CounterType type, Player... players) {
		teamCounters = type.createCounters(players);
	}	
	
	public Team(Counter... counters) {
		teamCounters = counters;
	}
	
	public Counter[] getCounters() {
		return teamCounters;
	}

	public Counter getCounter(int index) {
		return teamCounters[index];
	}
	
	public int countPlayers() {
		return teamCounters.length;
	}
	
	public void reset() {
		for(Counter c : teamCounters)
			c.reset();
	}
	
}
