package lifecounter.ui;

import lifecounter.model.CounterType;
import lifecounter.model.Player;
import lifecounter.model.gameoptions.ArchenemyGameOption;
import lifecounter.model.gameoptions.GameOption;
import lifecounter.model.gameoptions.PlanechaseGameOption;
import lifecounter.model.games.ArchenemyGame;
import lifecounter.model.games.FreeForAllGame;
import lifecounter.model.games.Game;
import lifecounter.model.games.TeamGame;
import lifecounter.model.games.TwoHeadedGiantGame;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;

public class StartGameDialog extends Dialog implements OnClickListener {

	private final RadioButton oneVsOne;
	private final RadioButton allVsAll;
	private final RadioButton teamMatch;
	private final RadioButton twoHeaded;
	private final RadioButton archenemy;

	private final Spinner gameOption;

	private final EditText allVsAllPlayers;
	private final EditText twoHeadedPlayers;
	private final EditText teamPlayersLeft;
	private final EditText teamPlayersRight;
	private final EditText archenemyPlayers;

	private final Spinner planechaseEdition;
	private final Spinner archenemyDeck;

	private final Button startGame;

	private StartGameListener[] listener;

	private class ViewContainer {
		private View[] views;

		public ViewContainer(View... views) {
			this.views = views;
		}

		public void setVisibility(int flag) {
			for (View v : views)
				v.setVisibility(flag);
		}
	}

	public StartGameDialog(Context c, StartGameListener... listener) {
		super(c);
		setContentView(R.layout.start_game_dialog);
		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);
		setTitle(R.string.start_title);
		setCancelable(true);

		oneVsOne = (RadioButton) findViewById(R.id.radioButtonOneVsOne);
		allVsAll = (RadioButton) findViewById(R.id.radioButtonAllVsAll);
		teamMatch = (RadioButton) findViewById(R.id.radioButtonStartTeamMatch);
		twoHeaded = (RadioButton) findViewById(R.id.radioButtonTwoHeadedGiant);
		archenemy = (RadioButton) findViewById(R.id.radioButtonArchenemy);

		gameOption = (Spinner) findViewById(R.id.spinnerGameOption);

		allVsAllPlayers = (EditText) findViewById(R.id.editTextStartAllVsAllPlayers);
		teamPlayersLeft = (EditText) findViewById(R.id.editTextStartTeamPlayersLeft);
		teamPlayersRight = (EditText) findViewById(R.id.editTextStartTeamPlayersRight);
		twoHeadedPlayers = (EditText) findViewById(R.id.editTextStartTwoHeadedGiantPlayers);
		archenemyPlayers = (EditText) findViewById(R.id.editTextStartArchenemyPlayers);

		planechaseEdition = (Spinner) findViewById(R.id.spinnerPlanechaseEdition);
		archenemyDeck = (Spinner) findViewById(R.id.spinnerArchenemyDeck);

		final ViewContainer allVsAllCont = new ViewContainer(allVsAllPlayers,
				findViewById(R.id.textViewStartAllVsAllPlayers));
		final ViewContainer teamMatchCont = new ViewContainer(teamPlayersLeft, teamPlayersRight,
				findViewById(R.id.textViewStartTeamPlayers));
		final ViewContainer twoHeadedCont = new ViewContainer(twoHeadedPlayers,
				findViewById(R.id.textViewStartTwoHeadedGiantPlayers));
		final ViewContainer archenemyCont = new ViewContainer(archenemyPlayers,
				findViewById(R.id.textViewStartArchenemyPlayers));

		final ViewContainer[] radioConts = new ViewContainer[] { new ViewContainer(), allVsAllCont,
				teamMatchCont, twoHeadedCont, archenemyCont };

		setContainerVisibility(radioConts, 0);

		RadioGroup radgrp = (RadioGroup) findViewById(R.id.RadioGroupStartGame);
		radgrp.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup arg0, int arg1) {

				switch (arg1) {
				case R.id.radioButtonOneVsOne:
					setContainerVisibility(radioConts, 0);
					break;
				case R.id.radioButtonAllVsAll:
					setContainerVisibility(radioConts, 1);
					break;
				case R.id.radioButtonStartTeamMatch:
					setContainerVisibility(radioConts, 2);
					break;
				case R.id.radioButtonTwoHeadedGiant:
					setContainerVisibility(radioConts, 3);
					break;
				case R.id.radioButtonArchenemy:
					setContainerVisibility(radioConts, 4);
					break;
				}
			}
		});

		final ViewContainer planechaseEditionCont = new ViewContainer(planechaseEdition,
				findViewById(R.id.textViewStartPlanechaseEdition));
		final ViewContainer archenemyDeckCont = new ViewContainer(archenemyDeck,
				findViewById(R.id.textViewStartArchenemyDeck));

		final ViewContainer[] gameOptionConts = new ViewContainer[] { new ViewContainer(),
				planechaseEditionCont, archenemyDeckCont };

		setContainerVisibility(gameOptionConts, 0);

		gameOption.setOnItemSelectedListener(new OnItemSelectedListener() {

			public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
				setContainerVisibility(gameOptionConts, position);
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				setContainerVisibility(gameOptionConts, 0);
			}

		});

		this.listener = listener;

		startGame = (Button) findViewById(R.id.buttonStartGame);
		startGame.setOnClickListener(this);

	}

	private static void setContainerVisibility(ViewContainer[] conts, int checked) {
		for (int i = 0; i < conts.length; i++) {
			if (i == checked)
				conts[i].setVisibility(View.VISIBLE);
			else
				conts[i].setVisibility(View.GONE);
		}
	}

	private static interface GameOptionSelecter {
		public boolean isSelected();

		public GameOption getGameOption();
	}

	private GameOptionSelecter[] GAME_OPTIONS = new GameOptionSelecter[] { new GameOptionSelecter() {
		public boolean isSelected() {
			return gameOption.getSelectedItemPosition() == 1;
		}

		public GameOption getGameOption() {
			return new PlanechaseGameOption(planechaseEdition.getSelectedItemPosition());
		}
	}, new GameOptionSelecter() {
		public boolean isSelected() {
			return gameOption.getSelectedItemPosition() == 2;
		}

		public GameOption getGameOption() {
			return new ArchenemyGameOption(archenemyDeck.getSelectedItemPosition());
		}
	} };

	interface StartGameListener {
		public void start(Game g);
	}

	private void notifyListeners(Game g) {
		for (StartGameListener gl : listener)
			gl.start(g);
	}

	public void onClick(View v) {

		Game result = null;

		if (v.equals(startGame)) {
			if (oneVsOne.isChecked()) {
				result = new TeamGame(new Player("Player 1"), new Player("Player 2"));
			} else if (allVsAll.isChecked()) {
				int count = Integer.parseInt(allVsAllPlayers.getText().toString());

				Player[] players = new Player[count];

				for (int i = 1; i <= count; i++) {
					players[i - 1] = new Player("Player " + Integer.toString(i));
				}

				result = new FreeForAllGame(players);
			} else if (teamMatch.isChecked()) {
				final int leftCount = Integer.parseInt(teamPlayersLeft.getText().toString());
				final int rightCount = Integer.parseInt(teamPlayersRight.getText().toString());

				final Player[] playersLeft = new Player[leftCount];
				final Player[] playersRight = new Player[rightCount];

				for (int i = 1; i <= leftCount; i++) {
					playersLeft[i - 1] = new Player("Player " + Integer.toString(i));
				}
				for (int i = leftCount + 1; i <= leftCount + rightCount; i++) {
					playersRight[i - 1 - leftCount] = new Player("Player " + Integer.toString(i));
				}

				result = new TeamGame(CounterType.DEFAULT, playersLeft, playersRight);
			} else if (twoHeaded.isChecked()) {
				result = new TwoHeadedGiantGame(Integer.parseInt(twoHeadedPlayers.getText()
						.toString()));
			} else if (archenemy.isChecked()) {
				Player[] opponents = new Player[Integer.parseInt(archenemyPlayers.getText()
						.toString())];
				for (int i = 0; i < opponents.length; i++) {
					opponents[i] = new Player("Player " + i);
				}

				result = new ArchenemyGame(new Player("Archenemy"), opponents);
			}

			if (result != null) {

				for (GameOptionSelecter gos : GAME_OPTIONS) {
					if (gos.isSelected())
						result = gos.getGameOption().useOn(result);
				}

				Log.d("StartGameDialog", result.getClass().toString());

				notifyListeners(result);

			}

			dismiss();

		}
	}
}
