package lifecounter.model.gameoptions;

import java.util.Arrays;
import java.util.List;

import lifecounter.model.games.Game;
import lifecounter.model.games.Game.GameBackground;
import lifecounter.model.games.Game.PlaneType;

public class ArchenemyGameOption implements GameOption {

	private final int deckNumber;

	private final static List<GameBackground> DECK_ASSEMBLE_THE_DOOMSDAY_MACHINE = Arrays
			.asList(new GameBackground[] { new GameBackground(PlaneType.archenemy, 0, 2),
					new GameBackground(PlaneType.archenemy, 0, 5),
					new GameBackground(PlaneType.archenemy, 0, 9),
					new GameBackground(PlaneType.archenemy, 0, 13),
					new GameBackground(PlaneType.archenemy, 0, 13),
					new GameBackground(PlaneType.archenemy, 0, 16),
					new GameBackground(PlaneType.archenemy, 0, 17),
					new GameBackground(PlaneType.archenemy, 0, 17),
					new GameBackground(PlaneType.archenemy, 0, 18),
					new GameBackground(PlaneType.archenemy, 0, 18),
					new GameBackground(PlaneType.archenemy, 0, 21),
					new GameBackground(PlaneType.archenemy, 0, 42),
					new GameBackground(PlaneType.archenemy, 0, 42),
					new GameBackground(PlaneType.archenemy, 0, 27),
					new GameBackground(PlaneType.archenemy, 0, 32),
					new GameBackground(PlaneType.archenemy, 0, 43),
					new GameBackground(PlaneType.archenemy, 0, 43),
					new GameBackground(PlaneType.archenemy, 0, 36),
					new GameBackground(PlaneType.archenemy, 0, 47),
					new GameBackground(PlaneType.archenemy, 0, 49)

			});

	private final static List<GameBackground> DECK_TRAMPLE_CIVILIZATION_UNDERFOOT = Arrays
			.asList(new GameBackground[] { new GameBackground(PlaneType.archenemy, 0, 11),
					new GameBackground(PlaneType.archenemy, 0, 11),
					new GameBackground(PlaneType.archenemy, 0, 12),
					new GameBackground(PlaneType.archenemy, 0, 15),
					new GameBackground(PlaneType.archenemy, 0, 16),
					new GameBackground(PlaneType.archenemy, 0, 20),
					new GameBackground(PlaneType.archenemy, 0, 21),
					new GameBackground(PlaneType.archenemy, 0, 24),
					new GameBackground(PlaneType.archenemy, 0, 30),
					new GameBackground(PlaneType.archenemy, 0, 30),
					new GameBackground(PlaneType.archenemy, 0, 31),
					new GameBackground(PlaneType.archenemy, 0, 31),
					new GameBackground(PlaneType.archenemy, 0, 36),
					new GameBackground(PlaneType.archenemy, 0, 37),
					new GameBackground(PlaneType.archenemy, 0, 37),
					new GameBackground(PlaneType.archenemy, 0, 44),
					new GameBackground(PlaneType.archenemy, 0, 47),
					new GameBackground(PlaneType.archenemy, 0, 49),
					new GameBackground(PlaneType.archenemy, 0, 50),
					new GameBackground(PlaneType.archenemy, 0, 50) });

	private final static List<GameBackground> DECK_SCORCH_THE_WORLD_WITH_DRAGONFIRE = Arrays
			.asList(new GameBackground[] { new GameBackground(PlaneType.archenemy, 0, 3),
					new GameBackground(PlaneType.archenemy, 0, 3),
					new GameBackground(PlaneType.archenemy, 0, 4),
					new GameBackground(PlaneType.archenemy, 0, 41),
					new GameBackground(PlaneType.archenemy, 0, 14),
					new GameBackground(PlaneType.archenemy, 0, 14),
					new GameBackground(PlaneType.archenemy, 0, 16),
					new GameBackground(PlaneType.archenemy, 0, 21),
					new GameBackground(PlaneType.archenemy, 0, 22),
					new GameBackground(PlaneType.archenemy, 0, 23),
					new GameBackground(PlaneType.archenemy, 0, 23),
					new GameBackground(PlaneType.archenemy, 0, 26),
					new GameBackground(PlaneType.archenemy, 0, 29),
					new GameBackground(PlaneType.archenemy, 0, 29),
					new GameBackground(PlaneType.archenemy, 0, 36),
					new GameBackground(PlaneType.archenemy, 0, 45),
					new GameBackground(PlaneType.archenemy, 0, 46),
					new GameBackground(PlaneType.archenemy, 0, 46),
					new GameBackground(PlaneType.archenemy, 0, 47),
					new GameBackground(PlaneType.archenemy, 0, 49) });

	private final static List<GameBackground> DECK_BRING_ABOUT_THE_UNDEAD_APOCALYPSE = Arrays
			.asList(new GameBackground[] { new GameBackground(PlaneType.archenemy, 0, 6),
					new GameBackground(PlaneType.archenemy, 0, 6),
					new GameBackground(PlaneType.archenemy, 0, 7),
					new GameBackground(PlaneType.archenemy, 0, 40),
					new GameBackground(PlaneType.archenemy, 0, 40),
					new GameBackground(PlaneType.archenemy, 0, 1),
					new GameBackground(PlaneType.archenemy, 0, 10),
					new GameBackground(PlaneType.archenemy, 0, 10),
					new GameBackground(PlaneType.archenemy, 0, 16),
					new GameBackground(PlaneType.archenemy, 0, 21),
					new GameBackground(PlaneType.archenemy, 0, 25),
					new GameBackground(PlaneType.archenemy, 0, 28),
					new GameBackground(PlaneType.archenemy, 0, 33),
					new GameBackground(PlaneType.archenemy, 0, 33),
					new GameBackground(PlaneType.archenemy, 0, 36),
					new GameBackground(PlaneType.archenemy, 0, 38),
					new GameBackground(PlaneType.archenemy, 0, 38),
					new GameBackground(PlaneType.archenemy, 0, 39),
					new GameBackground(PlaneType.archenemy, 0, 47),
					new GameBackground(PlaneType.archenemy, 0, 49) });

	@SuppressWarnings("rawtypes")
	private final static List[] DECKS = new List[] { DECK_ASSEMBLE_THE_DOOMSDAY_MACHINE,
			DECK_TRAMPLE_CIVILIZATION_UNDERFOOT, DECK_SCORCH_THE_WORLD_WITH_DRAGONFIRE,
			DECK_BRING_ABOUT_THE_UNDEAD_APOCALYPSE };

	public ArchenemyGameOption(int deckNumber) {

		this.deckNumber = deckNumber;
	}

	@SuppressWarnings("unchecked")
	public Game useOn(Game parentGame) {
		return new ArchenemyGame(parentGame, DECKS[deckNumber]);
	}

	private static class ArchenemyGame extends BackgroundGame {

		private static final long serialVersionUID = -4177427682371849079L;

		public ArchenemyGame(Game parent, List<GameBackground> cards) {
			super(parent, cards);
		}
	}

}
