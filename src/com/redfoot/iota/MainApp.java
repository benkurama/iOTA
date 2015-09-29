package com.redfoot.iota;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.location.LocationManager;
import android.preference.PreferenceManager;

public class MainApp extends Application {
	
	// -- >
	// if debug == 0 disabled gps longlat and textbox txt mapping result and debug mode
	// 0 is disabled : 1 is enabled
	public final static int DEBUG = 1; // range 0-1
	public final static int PHOTOSYNC_MODE = 0; // range 0-1
	public final static int SAVEDRAFT_MODE = 0; // range 0-1
	public final static int OPTIMIZE_SEND_TEXT = 1;	// range 0-1 
	/// -- <  
	public final static String INTENT_ACTION_SENT = ".intent_action_sent";
	public final static String INTENT_DIALOG_CLOSED = ".inte" +
			"nt_dialog_closed";
	public final static String INTENT_SETUP_FINISHED = ".intent_setup_finished";
	public final static int REQUEST_ACTION_SENT = 1;
	public final static int DIALOG_IOTA_NUM_ID = 0;
	public final static int DIALOG_SETTINGS_ID = 1;
	public final static int DIALOG_SETUP = 1;
	
	public final static String SETUP_CLASS = "com.redfoot.iota.Setup";
	public final static String MAIN_MENU_CLASS = "com.redfoot.iota.MainMenu";
	public final static String MOBTEL_ISCHECKED = "mobtel.ischecked";
	
	private static LocationManager m_lmLocMgr;
	private static GPSLocationListener m_gllLoc;
	private static Database m_dDB;
	
	private double m_dGPSLong;
	private double m_dGPSLat;
	private int m_iGPSStatus;

	public Database getDatabase() { return m_dDB; }
	public double getGPSLong() { return m_dGPSLong; }
	public double getGPSLat() { return m_dGPSLat; }
	public double getGPSStatus() { return m_iGPSStatus; }
	public void setGPSLong(double lng) { m_dGPSLong = lng; }
	public void setGPSLat(double lat) { m_dGPSLat = lat; }
	public void setGPSStatus(int status) { m_iGPSStatus = status; }
	// ---------------------- //
	public static boolean isValidUrl;
	
	@Override
	public void onCreate() {
		super.onCreate();
		
        // register location manager
        m_lmLocMgr = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);
        m_gllLoc = new GPSLocationListener();
        m_gllLoc.setContext(this);
        m_lmLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_gllLoc);
        //
	}
	
	public static void createDatabase(Activity context) { 
		m_gllLoc.setDialogContext(context);
		m_dDB = new Database(context);

		m_dDB.createDatabase();
	}
	
	public static void onClose(){
		
		m_lmLocMgr.removeUpdates(m_gllLoc);
		m_dDB.close();
	}
	
	public static void createGPSListner(Activity context){
		m_gllLoc.setDialogContext(context);
	}
	
	public static void setDialogParam(Activity context){
		m_gllLoc.setDialogContext(context);
		m_lmLocMgr.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, m_gllLoc);
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		m_lmLocMgr.removeUpdates(m_gllLoc);
		m_dDB.close();
	}
	// =========================================================================
	// TODO Preferences benkurama codes
	// =========================================================================
	public static int getTextID(Context core){  
		return PreferenceManager.getDefaultSharedPreferences(core).getInt("textid", 1);
	}
	public static void setAndIncrementTextID(Context core){
		
		int counter = PreferenceManager.getDefaultSharedPreferences(core).getInt("textid", 1);
		
		PreferenceManager.getDefaultSharedPreferences(core).edit().putInt("textid", counter + 1).commit();
	}
	// =========================================================================
	public static String getWebconfig(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getString("webconfig", "null");
	}
	public static void setDefaultWebconfig(Context core){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putString("webconfig", "https://iota-cloud.com/").commit();
	}
	public static void setWebconfig(Context core, String url){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putString("webconfig", url).commit();
	}
	// =========================================================================
	public static boolean getUrlValidation(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getBoolean("validurl", false);
	}
	public static void setUrlValidation(Context core, boolean valid){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putBoolean("validurl", valid).commit();
	}
	// =========================================================================
	public static boolean getOnline(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getBoolean("online", false);
	}
	public static void setOnline(Context core, boolean status){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putBoolean("online", status).commit();
	}
	// =========================================================================
	public static boolean getDebug(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getBoolean("debug", false);
	}
	public static void setDebug(Context core, boolean status){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putBoolean("debug", status).commit();
	}
	// =========================================================================
	// benkurama added codes on 10-7-2014 -->
	public static void setCitySpnPos(Context core, int pos){
		PreferenceManager.getDefaultSharedPreferences(core).edit().putInt("spnCityPos", pos).commit();
	}
	public static int getCitySpnPos(Context core){
		return PreferenceManager.getDefaultSharedPreferences(core).getInt("spnCityPos", 0);
	}
	// -- <
	// =========================================================================
	// TODO Final
}
