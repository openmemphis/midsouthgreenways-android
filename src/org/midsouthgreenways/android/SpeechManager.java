package org.midsouthgreenways.android;

import org.ispeech.SpeechSynthesis;
import org.ispeech.error.BusyException;
import org.ispeech.error.InvalidApiKeyException;
import org.ispeech.error.NoNetworkException;
import org.midsouthgreenways.android.android.map.MapViewerActivity;


import android.app.Activity;

public class SpeechManager {

	private SpeechSynthesis synthesis;
	private Settings settings;
	
	public SpeechManager() {
	}
	
	// Initialize the speech engine by passing in the main activitiy
	public void initSpeechEngine(MapViewerActivity activity) {
		try {
			synthesis = SpeechSynthesis.getInstance(activity);
			settings = new Settings(activity);
		} catch (InvalidApiKeyException e) {
			e.printStackTrace();
		}
	}
	
	// speak the text passed in
	public void speak(String text) {
		try {
			if (isVoiceFeedbackEnabled()) {
				synthesis.speak(text);
			}
		} catch (BusyException e) {
			e.printStackTrace();
		} catch (NoNetworkException e) {
			e.printStackTrace();
		}
	}
		
	// Determine if voice feedback is enabled. This will determine if we should speak or ot
	private boolean isVoiceFeedbackEnabled() {
		return settings.getBoolean("voiceFeedback");
	}
}
