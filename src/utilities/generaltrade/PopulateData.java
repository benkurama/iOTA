package utilities.generaltrade;

import java.util.ArrayList;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.redfoot.iota.CodeDescPair;
import com.redfoot.iota.MainApp;
import com.redfoot.iota.R;

public class PopulateData {
	
	public static void fillChecklist(Context core, ListView lvNearEstablishment){
		
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(core, 
				R.layout.checklistitem,	core.getResources().getStringArray(R.array.establishments));

        lvNearEstablishment.setAdapter(adapter);
        lvNearEstablishment.setItemChecked(7, true);
	}
	
	public static void fillProvince(Context core, MainApp mainApp, Spinner spnProvinces){
		
		ArrayList<CodeDescPair> allProvince = mainApp.getDatabase().getProvinces();
    	ArrayAdapter<CodeDescPair> aaAdapter = new ArrayAdapter<CodeDescPair>(core, 
    			android.R.layout.simple_spinner_item);
    	
    	aaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	for (CodeDescPair cdp : allProvince) {
    		aaAdapter.add(cdp);
    	}
    	spnProvinces.setAdapter(aaAdapter);
	}
	
	public static void fillCities(Context core, MainApp mainApp, String codeProvince, Spinner spnCities){
		
		ArrayList<CodeDescPair> allCities = mainApp.getDatabase().getCitiesByCode(codeProvince);
    	ArrayAdapter<CodeDescPair> aaAdapter = new ArrayAdapter<CodeDescPair>(core, android.R.layout.simple_spinner_item);
    	
    	aaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	
    	aaAdapter.add(new CodeDescPair("0", "Select City"));
    	
    	for (CodeDescPair cdp : allCities) {
    		aaAdapter.add(cdp);
    	}
    	spnCities.setAdapter(aaAdapter);
    	// benkurama added codes on 10-7-2014 -->
    	int pos = MainApp.getCitySpnPos(core);
    	if(pos != 0){
    		spnCities.setSelection(pos);	
    	}
    	// -- <
	}
	
	public static void fillBarangay(Context core, String codeCity, MainApp mainApp, Spinner spnBarangay){
		
		if(!codeCity.equals("0")){
			
			ArrayList<CodeDescPair> allBarangay = mainApp.getDatabase().getBarangays(codeCity);
	    	ArrayAdapter<CodeDescPair> aaAdapter = new ArrayAdapter<CodeDescPair>(core, android.R.layout.simple_spinner_item);
	    	
	    	aaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    	
	    	allBarangay.add(0,new CodeDescPair("0", "Select Barangay"));
	    	
	    	for (CodeDescPair cdp : allBarangay) {
	    		aaAdapter.add(cdp);
	    	}
	    	spnBarangay.setAdapter(aaAdapter);
	    	//
			
		} else {
			ArrayAdapter<CodeDescPair> aaAdapter = new ArrayAdapter<CodeDescPair>(core, android.R.layout.simple_spinner_item);
			aaAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			
			aaAdapter.add(new CodeDescPair("0", "Select Barangay"));
	    	spnBarangay.setAdapter(aaAdapter);
		}
	}
}
