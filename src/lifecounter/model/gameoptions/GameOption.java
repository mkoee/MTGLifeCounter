package lifecounter.model.gameoptions;

import lifecounter.model.games.Game;

public interface GameOption {

	/**
	 * Uses the game option on a game.
	 * @param parentGame The game where to apply the option.
	 * @return The new game with the option applied.
	 */
	public Game useOn(Game parentGame);
}
