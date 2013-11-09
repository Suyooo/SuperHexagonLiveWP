package net.greencouchgames.hexagonwp;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import net.rbgrn.android.glwallpaperservice.*;

// Original code provided by Robert Green
// http://www.rbgrn.net/content/354-glsurfaceview-adapted-3d-live-wallpapers
public class HexagonService extends GLWallpaperService {
	public static final String	SHARED_PREFS_NAME	= "hexagonwallpaper_settings";
	public SharedPreferences preferences;
	
	public int prefs_colors;
	public int prefs_perspective;
	public int prefs_zoom;
	public int prefs_pulse;
	public int prefs_rotation;
	public int prefs_walls;
	
	public HexagonService() {
		super();
	}

	public Engine onCreateEngine() {
		MyEngine engine = new MyEngine(this);
		return engine;
	}

	class MyEngine extends GLEngine implements
	SharedPreferences.OnSharedPreferenceChangeListener {
		HexagonRenderer renderer;
		HexagonService	service;
		public MyEngine(HexagonService s) {
			super();
			// handle prefs, other initialization
			renderer = new HexagonRenderer(s);
			service = s;
			setRenderer(renderer);
			setRenderMode(RENDERMODE_CONTINUOUSLY);
			
			preferences = HexagonService.this.getSharedPreferences(SHARED_PREFS_NAME, 0);
			preferences.registerOnSharedPreferenceChangeListener((OnSharedPreferenceChangeListener) this);
			if (!preferences.contains("colors")) {
				Editor e = preferences.edit();
				e.putString("colors","1");
				e.putString("perspective","-1");
				e.putString("zoom","-1");
				e.putString("pulse","1");
				e.putString("rotation","-1");
				e.putString("walls","3");
				e.commit();
			}
			onSharedPreferenceChanged(preferences, null);
		}

		public void onDestroy() {
			super.onDestroy();
			if (renderer != null) {
				renderer.release();
			}
			renderer = null;
		}

		public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
			service.prefs_colors = Integer.parseInt(prefs.getString("colors","1"));
			service.prefs_perspective = Integer.parseInt(prefs.getString("perspective","-1"));
			service.prefs_zoom = Integer.parseInt(prefs.getString("zoom","-1"));
			service.prefs_pulse = Integer.parseInt(prefs.getString("pulse","1"));
			service.prefs_rotation = Integer.parseInt(prefs.getString("rotation","-1"));
			service.prefs_walls = Integer.parseInt(prefs.getString("walls","3"));
		}
	}
}
