package lifecounter.model.gameoptions;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import lifecounter.model.Counter;
import lifecounter.model.Team;
import lifecounter.model.games.Game;


public abstract class BackgroundGame extends Game{
	
	private static final long serialVersionUID = 3065578882181286770L;
	
	private final List<GameBackground> cards;
	
	private final Game parent;
	
	private int currentCard;

	public BackgroundGame(Game parent, List<GameBackground> cards) {
		if (parent == null)
			throw new NullPointerException("Game may not be null.");
				
		this.parent = parent;
		
		

		this.cards = cards;
		Collections.shuffle(cards);
		currentCard = -1;
	}
	
	@Override
	public void reset() {
		Collections.shuffle(cards);
		currentCard = -1;
		parent.reset();
	}

	@Override
	public boolean is1vs1() {
		return parent.is1vs1();
	}

	@Override
	public void nextGameBackground() {
		currentCard = (currentCard + 1);
		
		if (currentCard >= cards.size())
			currentCard = 0;
	}
	
	@Override
	public void lastGameBackground() {
		currentCard = (currentCard - 1);		
		
		if (currentCard < 0)
			currentCard = cards.size() - 1;
	}

	@Override
	public GameBackground getGameBackground() {
		if (currentCard >= 0)
			return cards.get(currentCard);
		else
			return Game.NO_BACKGROUND;
	}
	
	@Override
	public Team[] getTeams() {
		return parent.getTeams();
	}

	@Override
	public Counter[] getCounters() {
		return parent.getCounters();
	}

	@Override
	public Set<GameRequirement> getRequirements() {			
		Set<GameRequirement> result = parent.getRequirements();
		result.add(GameRequirement.PictureSlideshow);			
		return result;
	}

}
