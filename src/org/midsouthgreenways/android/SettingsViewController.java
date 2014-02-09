package org.midsouthgreenways.android;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;

public class SettingsViewController {

	// Private variables

	private MapViewerActivity mainActivity;
	private Settings settings;
	
	// Constructors
	
	public SettingsViewController(MapViewerActivity activity) {
		mainActivity = activity;
		settings = new Settings(mainActivity);
	}
	
	public void loadSettings() {
		RelativeLayout settingsView = (RelativeLayout)mainActivity.findViewById(R.layout.settingsview);
		
		CheckBox voiceFeedbackEnabled = (CheckBox)settingsView.findViewById(R.id.voiceFeedbackEnabled);
		voiceFeedbackEnabled.setChecked(getVoiceFeedbackEnabled());
		/*RadioButton voiceFeedbackDisabled = (RadioButton)settingsView.findViewById(R.id.voiceFeedbackDisabled);
		if (getVoiceFeedbackEnabled()) {
			voiceFeedbackEnabled.setChecked(true);
		} else {
			voiceFeedbackDisabled.setChecked(true);
		}*/
	}
	
	// Events
	
	public OnCheckedChangeListener voiceFeedbackCheckChanged = new OnCheckedChangeListener() {
	   public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		   //RadioGroup voiceFeedback = (RadioGroup)((RelativeLayout)mainActivity.findViewById(R.id.settingsView)).findViewById(R.id.voiceFeedbackRadioGroup);
		   //int checkId = voiceFeedback.getCheckedRadioButtonId();
		   //boolean isCheckEnabled = checkId == R.id.voiceFeedbackEnabled; 
		   
		   settings.putBoolean("voiceFeedback", isChecked);
	   }
	};
	
	// Public Methods
	
	public boolean getVoiceFeedbackEnabled() {
		return settings.getBoolean("voiceFeedback");
	}
}
