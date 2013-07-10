package lifecounter.ui;

import java.util.Set;

import lifecounter.model.Counter;
import lifecounter.model.exceptions.NoBackgroundAvailableException;
import lifecounter.model.games.Game;
import lifecounter.ui.StartGameDialog.StartGameListener;
import lifecounter.ui.exceptions.NoSuchBackgroundException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class LifeCounterActivity extends Activity implements StartGameListener {

	/**
	 * This class holds the current game that is active. This functionality uses
	 * the extra class to not get deleted upon closing of the activity.
	 * 
	 * @author Mirko
	 * 
	 */
	public static class CurrentGameHolder {
		protected static Game game;
		protected static Set<Game.GameRequirement> requirements;
	}

	private ListView leftTeam;
	private ListView rightTeam;

	//	private LinearLayout backgroundLayout;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.counter_main);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

		leftTeam = (ListView) findViewById(R.id.listViewLeftTeam);
		rightTeam = (ListView) findViewById(R.id.listViewRightTeam);

		//Sidemenu buttons
		final Button sideHideCounters = (Button) findViewById(R.id.buttonSidemenuHideCounters);
		sideHideCounters.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				hideCounters();
			}
		});
		final Button sideShowRandom = (Button) findViewById(R.id.buttonSidemenuShowRandom);
		sideShowRandom.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showRandom("planar");
			}
		});
		final Button sideNextBackground = (Button) findViewById(R.id.buttonSidemenuNextBackground);
		sideNextBackground.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showNextBackground();
			}
		});
		final Button sideLastBackground = (Button) findViewById(R.id.buttonSidemenuLastBackground);
		sideLastBackground.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				showLastBackground();
			}
		});
	}

	@Override
	public void onPostResume() {
		super.onResume();
		if (CurrentGameHolder.game == null) {
			newGameDialog().show();
		} else {
			initialize();
		}
	}

	// Inflate the menu
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.counter_menu, menu);

		return true;
	}

	private Dialog newGameDialog() {
		return new StartGameDialog(LifeCounterActivity.this, this);
	}

	/**
	 * Shows the random dialog.
	 * @param tag The random tab-tag that should be shown at the beginning or null for the default tab.
	 */
	private void showRandom(String tag) {
		if (CurrentGameHolder.game != null) {
			Intent intent = new Intent(this, RandomActivity.class);
			if(tag != null)
				intent.putExtra("random_category", tag);
			startActivity(intent);
		} else
			Toast.makeText(this, R.string.info_no_active_game, Toast.LENGTH_LONG).show();
	}

	/**
	 * Shows the next background-
	 */
	private void showNextBackground() {
		if (CurrentGameHolder.game != null) {
			try {
				CurrentGameHolder.game.nextGameBackground();
				setBackground();
			} catch (NoBackgroundAvailableException e) {
				Toast.makeText(this, R.string.info_nobackground, Toast.LENGTH_LONG).show();
			}
		} else
			Toast.makeText(this, R.string.info_no_active_game, Toast.LENGTH_LONG).show();

	}
	
	/**
	 * Shows the next background-
	 */
	private void showLastBackground() {
		if (CurrentGameHolder.game != null) {
			try {
				CurrentGameHolder.game.lastGameBackground();
				setBackground();
			} catch (NoBackgroundAvailableException e) {
				Toast.makeText(this, R.string.info_nobackground, Toast.LENGTH_LONG).show();
			}
		} else
			Toast.makeText(this, R.string.info_no_active_game, Toast.LENGTH_LONG).show();

	}

	/**
	 * Hides/shows the counters.
	 */
	private void hideCounters() {
		if (leftTeam.getVisibility() == View.INVISIBLE) {
			leftTeam.setVisibility(View.VISIBLE);
			rightTeam.setVisibility(View.VISIBLE);
		} else {
			leftTeam.setVisibility(View.INVISIBLE);
			rightTeam.setVisibility(View.INVISIBLE);
		}
	}

	// Handle click events
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_new_game:
			newGameDialog().show();
			return true;

		case R.id.menu_reset:
			if (CurrentGameHolder.game != null) {
				CurrentGameHolder.game.reset();
				start(CurrentGameHolder.game);

//				Intent intent = new Intent(this, RandomActivity.class);
//				intent.putExtra("random_category", "player");
//				startActivity(intent);
//				
				showRandom("player");

				Toast.makeText(this, R.string.info_game_resetted, Toast.LENGTH_SHORT).show();
			} else {
				Toast.makeText(this, R.string.info_no_active_game, Toast.LENGTH_LONG).show();
			}
			return true;

		case R.id.menu_random:
			showRandom(null);
			return true;		

		case R.id.menu_hidecounters:
			hideCounters();
			return true;

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private class CounterListAdapter extends ArrayAdapter<Counter> {

		public CounterListAdapter(Context context, Counter[] objects) {
			super(context, R.layout.counter_element, objects);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			if (convertView == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = vi.inflate(R.layout.counter_element, null);
			}

			final Counter current = getItem(position);

			//text for life
			TextView viewLife = (TextView) convertView.findViewById(R.id.textViewLife);
			viewLife.setText(Integer.toString(current.getLife()));

			if (current.getThreat() > 0.8)
				viewLife.setTextColor(0xFFFF0000);
			else if (current.getThreat() <= 0.2)
				viewLife.setTextColor(0xFF00FF00);
			else
				viewLife.setTextColor(0xFFFFFFFF);

			if (CurrentGameHolder.game.is1vs1())
				viewLife.setTextSize(72);

			//			int colorGreen = ((int) ((1 - current.getThreat()) * 0xFF)) << 8;
			//			int colorRed = ((int) (current.getThreat() * 0xFF)) << 16;
			//			
			//			viewLife.setTextColor((colorGreen | colorRed) | 0xFF000000);
			//			

			viewLife.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					current.changeLife(-1);
					notifyDataSetChanged();

				}
			});

			//text for name
			final TextView viewName = (TextView) convertView.findViewById(R.id.textViewPlayerName);
			viewName.setText(current.getPlayer().getName());
			viewName.setOnClickListener(new OnClickListener() {

				public void onClick(View arg0) {
					AlertDialog.Builder alert = new AlertDialog.Builder(LifeCounterActivity.this);

					alert.setTitle(R.string.dialog_change_name_title);
					alert.setMessage(R.string.dialog_change_name_info);

					// Set an EditText view to get user input 
					final EditText input = new EditText(LifeCounterActivity.this);
					alert.setView(input);

					alert.setPositiveButton(R.string.dialog_ok,
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog, int which) {
									String value = input.getText().toString();
									current.getPlayer().setName(value);
									notifyDataSetChanged();
								}
							});

					alert.setNegativeButton(R.string.dialog_cancel,
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int whichButton) {

								}
							});

					alert.show();
				}
			});

			//Text for poison
			final TextView viewPoison = (TextView) convertView.findViewById(R.id.textViewPoison);
			viewPoison.setText(Integer.toString(current.getPoison()));
			viewPoison.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					current.changePoison(1);
					notifyDataSetChanged();
				}
			});

			//Increase life for a counter
			final Button buttonLifeIncrease = (Button) convertView
					.findViewById(R.id.buttonIncreaseLife);
			buttonLifeIncrease.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					current.changeLife(1);
					notifyDataSetChanged();
				}
			});
			//Decrease life for a counter
			final Button buttonLifeDecrease = (Button) convertView
					.findViewById(R.id.buttonDecreaseLife);
			buttonLifeDecrease.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					current.changeLife(-1);
					notifyDataSetChanged();
				}
			});

			final SeekBar poisonBar = (SeekBar) convertView.findViewById(R.id.seekBarPoison);
			poisonBar.setMax(current.getBasePoison());
			poisonBar.setProgress(current.getPoison());
			poisonBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

				public void onStopTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub
				}

				public void onStartTrackingTouch(SeekBar arg0) {
					// TODO Auto-generated method stub

				}

				public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
					if (arg2) {
						current.setPoison(arg1);
						notifyDataSetChanged();
					}
				}
			});

			return convertView;

		}
	}

	/**
	 * Sets the background of the life counter to the current background of the
	 * game.
	 */
	private void setBackground() {
		if (CurrentGameHolder.game == null)
			return;

		try {
			getWindow().setBackgroundDrawableResource(
					BackgroundResources.convertFrom(CurrentGameHolder.game.getGameBackground()));
		} catch (NoSuchBackgroundException e) {
			getWindow().setBackgroundDrawableResource(BackgroundResources.standard());
		}
	}
	
	/**
	 * Sets the view visibility so that the requirements are fulfilled.
	 */
	private void setRequirements() {
		RelativeLayout sm = (RelativeLayout) findViewById(R.id.relativeLayoutSideMenu);
				
		if (CurrentGameHolder.game == null || CurrentGameHolder.requirements == null)
			return;
		
		if(CurrentGameHolder.requirements.contains(Game.GameRequirement.PictureSlideshow)) {
			sm.setVisibility(View.VISIBLE);
		} else {
			sm.setVisibility(View.GONE);
		}
		
	}

	/**
	 * Initializes the game. This will be executed if a new game is started or
	 * if the app is resumed.
	 */
	private void initialize() {

		Game g = CurrentGameHolder.game;
		setBackground();
		setRequirements();
		
		if (g.getTeams() != null && g.getTeams().length == 2) {

			leftTeam.setAdapter(new CounterListAdapter(this, g.getTeams()[0].getCounters()));
			rightTeam.setAdapter(new CounterListAdapter(this, g.getTeams()[1].getCounters()));

		} else {

			int count = g.getPlayers().length;
			int countl = (count & 0x1) == 1 ? count / 2 + 1 : count / 2;
			int countr = count / 2;

			Counter[] left = new Counter[countl];
			Counter[] right = new Counter[countr];

			for (int i = 0; i < countl; i++)
				left[i] = g.getCounters()[i * 2];

			for (int i = 0; i < countr; i++)
				right[i] = g.getCounters()[(i * 2) + 1];

			leftTeam.setAdapter(new CounterListAdapter(this, left));
			rightTeam.setAdapter(new CounterListAdapter(this, right));

		}
	}

	/**
	 * Starts a new game.
	 */
	public void start(Game g) {
		CurrentGameHolder.game = g;
		CurrentGameHolder.requirements = g.getRequirements();
		initialize();
	}
}