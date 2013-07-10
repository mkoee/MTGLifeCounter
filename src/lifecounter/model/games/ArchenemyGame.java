package lifecounter.model.games;

import java.util.HashSet;
import java.util.Set;

import lifecounter.model.Counter;
import lifecounter.model.CounterType;
import lifecounter.model.Player;
import lifecounter.model.Team;
import lifecounter.model.exceptions.NoBackgroundAvailableException;

public class ArchenemyGame extends Game{

	private static final long serialVersionUID = -1516058717215914589L;
	
	private Counter archenemy;
	private Team opponents;
	
	public ArchenemyGame(Player archenemy, Player... opponents) {	
		
		this.archenemy = CounterType.DEFAULT.createCounter(archenemy);
		this.opponents = new Team(CounterType.DEFAULT, opponents);
	}
	
	@Override
	public Team[] getTeams() {		
		return new Team[] {new Team(archenemy), opponents};
	}

	@Override
	public void reset() {
		archenemy.reset();
		opponents.reset();		
				
	}

	@Override
	public boolean is1vs1() {
		return opponents.countPlayers() == 1;
	}

	@Override
	public void nextGameBackground() throws NoBackgroundAvailableException {
		throw new NoBackgroundAvailableException();		
	}
	
	@Override
	public void lastGameBackground() throws NoBackgroundAvailableException {
		throw new NoBackgroundAvailableException();		
	}

	@Override
	public GameBackground getGameBackground() {
		return Game.NO_BACKGROUND;
	}

	@Override
	public Set<GameRequirement> getRequirements() {		
		return new HashSet<GameRequirement>();
	}

	@Override
	public Counter[] getCounters() {
		
		Counter[] result = new Counter[opponents.countPlayers() + 1];
		System.arraycopy(opponents.getCounters(), 0, result, 1, opponents.countPlayers());
		result[0] = archenemy;
		return result;

	}

}
