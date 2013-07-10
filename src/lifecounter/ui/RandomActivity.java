package lifecounter.ui;

import lifecounter.ui.AbstractRandomActivity.RandomColorActivity;
import lifecounter.ui.AbstractRandomActivity.RandomD20Activity;
import lifecounter.ui.AbstractRandomActivity.RandomDiceActivity;
import lifecounter.ui.AbstractRandomActivity.RandomFlipCoinActivity;
import lifecounter.ui.AbstractRandomActivity.RandomPlanarActivity;
import lifecounter.ui.AbstractRandomActivity.RandomPlayerActivity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TabHost;

/**
 * 
 * Extras: <br>
 * - random_index: the index of the tab that should be shown at the beginning. <br>
 * - random_category: the name of the tab that should be shown at the beginning.
 * 
 * @author Mirko
 * 
 */
public class RandomActivity extends TabActivity {

	private static class RandomEntry {
		Class<?> activity;
		String title;

		RandomEntry(Class<?> activity, String title) {
			this.activity = activity;
			this.title = title;
		}

	}

	private static final RandomEntry[] randoms = {
			new RandomEntry(RandomDiceActivity.class, "Die 6"),
			new RandomEntry(RandomD20Activity.class, "Die 20"),
			new RandomEntry(RandomFlipCoinActivity.class, "Coin"),
			new RandomEntry(RandomPlayerActivity.class, "Player"),
			new RandomEntry(RandomColorActivity.class, "Color"),
			new RandomEntry(RandomPlanarActivity.class, "Planar"), };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.random_dialog);

		getWindow().setLayout(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT);

		TabHost tabHost = getTabHost(); // The activity TabHost
		TabHost.TabSpec spec; // Resusable TabSpec for each tab
		Intent intent; // Reusable Intent for each tab

		for (RandomEntry r : randoms) {
			// Create an Intent to launch an Activity for the tab (to be reused)
			intent = new Intent().setClass(this, r.activity);
			// Initialize a TabSpec for each tab and add it to the TabHost
			spec = tabHost.newTabSpec(r.title.toLowerCase()).setIndicator(r.title).setContent(intent);
			tabHost.addTab(spec);
		}

		Bundle extras = getIntent().getExtras();
		if (extras != null)
			if (extras.getInt("random_index") != 0)
				tabHost.setCurrentTab(extras.getInt("random_index"));
			else if (extras.getString("random_category") != null) {
				tabHost.setCurrentTabByTag(extras.getString("random_category"));
			} else
				tabHost.setCurrentTab(0);

		Button returnToGame = (Button) findViewById(R.id.buttonRandomReturn);
		returnToGame.setOnClickListener(new OnClickListener() {

			public void onClick(View arg0) {
				finish();
			}
		});
	}
}
