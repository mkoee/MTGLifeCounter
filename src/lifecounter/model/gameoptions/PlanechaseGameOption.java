package lifecounter.model.gameoptions;

import java.util.LinkedList;
import java.util.List;

import lifecounter.model.games.Game;
import lifecounter.model.games.Game.GameBackground;
import lifecounter.model.games.Game.PlaneType;

public class PlanechaseGameOption implements GameOption {

	private static final int[] NUMBER_OF_PLANES = new int[] {45,40};
	
	private final int edition;
	
	public PlanechaseGameOption(int edition) {
		this.edition = edition;
	}
	
	public Game useOn(Game parentGame) {
		return new PlanechaseGame(parentGame,createForEdition(edition));
	}
	
	private List<GameBackground> createForEdition(int edition) {
		
		List<GameBackground> result = new LinkedList<GameBackground>();
		
		for(int i = 1; i <= NUMBER_OF_PLANES[edition]; i++)
			result.add(new GameBackground(PlaneType.planechase,edition,i));
		
		return result;
		
	}

	private static class PlanechaseGame extends BackgroundGame {

		private static final long serialVersionUID = -8530528093897790555L;

		public PlanechaseGame(Game parent, List<GameBackground> edition) {
			super(parent,edition);
		}

	}

}
