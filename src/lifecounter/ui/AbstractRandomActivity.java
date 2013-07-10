package lifecounter.ui;

import java.util.Random;

import lifecounter.ui.LifeCounterActivity.CurrentGameHolder;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public abstract class AbstractRandomActivity extends Activity {

	private TextView rand;
	private TextView times;
	private int count = 0;
	protected final Random randomGen = new Random();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.random_dice);

		rand = (TextView) findViewById(R.id.textViewRandom);
		rand.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				
				Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);				
				v.vibrate(150);
				
				setRandom();
			}
		});
		
		times = (TextView) findViewById(R.id.textViewRandomTimes);
		
		
	}

	private void setRandom() {
		count++;
		times.setText(Integer.toString(count));
		rand.setText(getRandom());
		rand.setTextColor(0xFF808080 | randomGen.nextInt(0x00FFFFFF));		
	}

	protected abstract String getRandom();

	public static class RandomDiceActivity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {
			return Integer.toString(randomGen.nextInt(6) + 1);
		}

	}

	public static class RandomPlayerActivity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {
			return CurrentGameHolder.game.randomPlayer(randomGen).getName();
		}

	}

	public static class RandomColorActivity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {
			switch (randomGen.nextInt(5)) {
			case 0:
				return "White";
			case 1:
				return "Red";
			case 2:
				return "Black";
			case 3:
				return "Green";
			case 4:
				return "Blue";
			}
			return "Nothing";

		}

	}

	public static class RandomPlanarActivity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {
			switch (randomGen.nextInt(6)) {
			case 0:
			case 1:
			case 2:
			case 3:
				return "Blank";
			case 4:
				return "Chaos";
			case 5:
				return "Planeswalker";
			}
			return "Nothing";

		}

	}
	
	public static class RandomD20Activity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {			
			return Integer.toString(randomGen.nextInt(20) + 1);
		}
		
	}
	
	public static class RandomFlipCoinActivity extends AbstractRandomActivity {

		@Override
		protected String getRandom() {			
			return randomGen.nextInt(2) == 0 ? "Heads" : "Tails";
		}
		
	}

}
