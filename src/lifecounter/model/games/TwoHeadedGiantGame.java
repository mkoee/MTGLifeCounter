package lifecounter.model.games;

import lifecounter.model.CounterType;
import lifecounter.model.Player;

public class TwoHeadedGiantGame extends TeamGame {

	private static final long serialVersionUID = -6838730808890834099L;

	public TwoHeadedGiantGame(int players) {
		super(counterForPlayers(players), new Player[] { new Player("Team 1") },
				new Player[] { new Player("Team 2") });
	}

	private static CounterType counterForPlayers(int players) {
		if (players < 2)
			players = 2;

		return new CounterType(30 + (players - 2) * 15, 15 + (players - 2) * 5);
	}

}
