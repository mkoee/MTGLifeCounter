package lifecounter.ui;

import java.lang.reflect.Field;

import android.util.Log;
import lifecounter.model.games.Game.GameBackground;
import lifecounter.model.games.Game.PlaneType;
import lifecounter.ui.exceptions.NoSuchBackgroundException;

public class BackgroundResources {
	
	private static int STANDARD_BACKGROUND = R.drawable.background;
	
	public static int standard() {
		return STANDARD_BACKGROUND;
	}
	
	public static int convertFrom(GameBackground gb) throws NoSuchBackgroundException {
	
		if(gb.getType().equals(PlaneType.none)) {
			return standard();
		}
		
		if(gb.getType().equals(PlaneType.planechase)) {
			String baseName;
			
			if(gb.getEdition() == 0)
				baseName = "planechase_ori_plane";
			else if (gb.getEdition() == 1)
				baseName = "planechase_e2012_plane";
			else
				throw new NoSuchBackgroundException();				
				
			try {
				Field picRes = R.drawable.class.getField(baseName + gb.getResourceNumber());
				return picRes.getInt(null);
			} catch (NoSuchFieldException e) {
				throw new NoSuchBackgroundException(e);
			} catch (IllegalArgumentException e) {
				throw new NoSuchBackgroundException(e);
			} catch (IllegalAccessException e) {
				throw new NoSuchBackgroundException(e);
			}
		}
		
		if(gb.getType().equals(PlaneType.archenemy)) {
			String baseName = "archenemy_ori_plane";
				
			try {
				Field picRes = R.drawable.class.getField(baseName + gb.getResourceNumber());
				return picRes.getInt(null);
			} catch (NoSuchFieldException e) {
				throw new NoSuchBackgroundException(e);
			} catch (IllegalArgumentException e) {
				throw new NoSuchBackgroundException(e);
			} catch (IllegalAccessException e) {
				throw new NoSuchBackgroundException(e);
			}
		}
		
		throw new NoSuchBackgroundException();
	}
}
