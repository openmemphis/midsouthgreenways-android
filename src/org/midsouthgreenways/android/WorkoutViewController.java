package org.midsouthgreenways.android;

import org.midsouthgreenways.android.android.map.MapViewerActivity;

import android.app.Activity;
import android.graphics.Color;
import android.location.Location;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class WorkoutViewController {

	public enum TimerState {
		STOP, START, PAUSE, RESUME, RESET
	}
	
	private final double INCREMENT_TO_SPEAK = .5;
	
	private MapViewerActivity mainActivity;
	private Handler mHandler = new Handler();
	//private long startTime = 0;
	private boolean isRecording = false;
	private float totalDistance = 0;
	private double lastSpokenDistance = 0;
	private Location previousLocation;
	private String currentGreenway;
	private TimerState currentState = null;
	private SpeechManager speechManager = null;
	//private int secondsEllapsed = 0;
	private long lastPause;
	
	/*private Runnable mUpdateTimeTask = new Runnable() {
		   public void run() {
			   if (isRecording) {
				   secondsEllapsed++;
				   int minutes = secondsEllapsed / 60;
				   int seconds = secondsEllapsed % 60;
			  

	           		TextView workoutTime = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutTime);
	           		//workoutTime.setText(String.format("%d:%02d", minutes, seconds));
			   }
	           
		       //mHandler.postAtTime(this, startTime + (((minutes * 60) + seconds + 1) * 1000));
		       mHandler.postDelayed(mUpdateTimeTask, 1000);
		   }
	};*/
	
	public WorkoutViewController(MapViewerActivity view, SpeechManager manager) {
		mainActivity = view;
		speechManager = manager;
	}
	
//	public OnClickListener startTimerListener = new OnClickListener() {
//	   public void onClick(View v) {
		   
//		   advanceToNextState();
//		   
//		   Chronometer workoutTimer = (Chronometer)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutTime);
//		   if (currentState == TimerState.START) {
//			   //startTime = System.currentTimeMillis();
//			   //mHandler.removeCallbacks(mUpdateTimeTask);
//		       //mHandler.postDelayed(mUpdateTimeTask, 1000);
//		       isRecording = true;
//		       
//		       workoutTimer.setBase(SystemClock.elapsedRealtime()); 
//		       workoutTimer.start();
//		       
//		       // speak that the user is about to start a workout
//		       String startRunning = null;
//		       if (currentGreenway != null) {
//	                startRunning =  String.format("You are about to start a workout on %s. Good luck.", currentGreenway);
//		       } else {
//		    	   startRunning = "You are about to start a workout. Good luck.";
//		       }
//		       speechManager.speak(startRunning);
//		       
//		   } else if (currentState == TimerState.PAUSE) {
//			   //mHandler.removeCallbacks(mUpdateTimeTask);
//			   lastPause = SystemClock.elapsedRealtime();
//			   workoutTimer.stop();
//			   isRecording = false;
//		   } else if (currentState == TimerState.RESUME) {
//			   
//			   //mHandler.removeCallbacks(mUpdateTimeTask);
//		       //mHandler.postDelayed(mUpdateTimeTask, 1000);
//			   workoutTimer.setBase(workoutTimer.getBase() + SystemClock.elapsedRealtime() - lastPause);
//			   workoutTimer.start();
//			   isRecording = true;
//		   } else if (currentState == TimerState.RESET) {
//			   isRecording = false;
//			   //secondsEllapsed = 0;
//			   // set time and distance to 0
//			   //TextView workoutTime = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutTime);
//			   TextView distanceTextView = (TextView)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutDistance);
//			   //workoutTime.setText("00:00");
//			   //distanceTextView.setText("0.00 miles");
//			   workoutTimer.setBase(SystemClock.elapsedRealtime());
//		   }
//		   
//		   updateButtonForCurrentState();
//	   }
//	};
//	
//	public OnClickListener stopTimerListener = new OnClickListener() {
//	   public void onClick(View v) {
//		   
//		   Chronometer workoutTimer = (Chronometer)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutTime);
//		   workoutTimer.stop();
//		   
//		   //mHandler.removeCallbacks(mUpdateTimeTask);
//		   currentState = TimerState.STOP;
//		   updateButtonForCurrentState();
//		   isRecording = false;
//	   }
//	};
//	
//	public void onClick(View v) {
//		
//		// This will be responsible for managing the start and stop timers
//		//startTime = System.currentTimeMillis();
//		//mHandler.removeCallbacks(mUpdateTimeTask);
//        //mHandler.postDelayed(mUpdateTimeTask, 1000);
//			
//		
//	}
//	
//	// Update the view with information on the current greenway
//	public void updateCurrentGreenway(String currentGreenway) {
//		RelativeLayout workoutView = (RelativeLayout)mainActivity.findViewById(R.id.workoutView);
//		ImageView greenwayMarkerImage = (ImageView)workoutView.findViewById(R.id.greenwayMarkerImage);
//		TextView greenwayText = (TextView)workoutView.findViewById(R.id.greenwayText);
//		
//		if (currentGreenway != null) {
//			greenwayMarkerImage.setImageResource(R.drawable.green_marker_map);
//			greenwayText.setTextColor(Color.parseColor("#006400"));
//			greenwayText.setText(currentGreenway);
//		} else {
//			greenwayMarkerImage.setImageResource(R.drawable.grey_marker);
//			greenwayText.setTextColor(Color.parseColor("#999999"));
//			greenwayText.setText("No Greenway Found");
//		}
//	}
//	
//	public void incrementDistance(Location location) {
//				
//		if (previousLocation != null) {
//			float newDistance = previousLocation.distanceTo(location);
//			totalDistance += newDistance;
//			
//			// update the distance label TODO ADD BACK
//			RelativeLayout workoutView = (RelativeLayout)mainActivity.findViewById(R.id.workoutView);
//			TextView distanceTextView = (TextView)workoutView.findViewById(R.id.workoutDistance);
//			distanceTextView.setText(String.format("%.2f miles", (totalDistance/1000) * .6214));
//		}
//		
//		previousLocation = location;
//		
//		// determine if we need to speak the location
//		double totalDistanceMiles = (totalDistance/1000) * .6214;
//		if (totalDistanceMiles >= (lastSpokenDistance + INCREMENT_TO_SPEAK)) {
//			
//			// get minutes and seconds
//			Chronometer workoutTimer = (Chronometer)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutTime);
//			long elapsedMillis = SystemClock.elapsedRealtime() - workoutTimer.getBase();
//			long ellapsedSeconds = elapsedMillis/1000;
//			int minutes = (int)ellapsedSeconds / 60;
//			int seconds = (int)ellapsedSeconds % 60;
//	        
//	        String roundedDistance = String.format("%.2f", (totalDistance/1000) * .6214);
//	        String stringToSpeak = null;
//	        if (currentGreenway != null) {
//                stringToSpeak = String.format("You have completed %s miles on %s in %d minutes and %d seconds", roundedDistance, currentGreenway, minutes, seconds);
//            } else {
//                stringToSpeak = String.format("You have completed %s miles in %d minutes and %d seconds", roundedDistance, minutes, seconds);
//            }
//	        speechManager.speak(stringToSpeak);
//	        lastSpokenDistance = totalDistanceMiles;
//		}
//		
//	}
//	
//	// get the view state for the workout view
//	public int getViewState() {
//		return mainActivity.findViewById(R.id.workoutView).getVisibility();
//	}
//	
//	public boolean getIsRecording() {
//		return isRecording;
//	}
//	
//	private void advanceToNextState() {
//		if (currentState == null) {
//			currentState = TimerState.START;
//		} else if (currentState == TimerState.START) {
//			currentState = TimerState.PAUSE;
//		} else if (currentState == TimerState.PAUSE) {
//			currentState = TimerState.RESUME;
//		} else if (currentState == TimerState.RESUME) {
//			currentState = TimerState.PAUSE;
//		} else if (currentState == TimerState.RESET) {
//			currentState = TimerState.START;
//		} else if (currentState == TimerState.STOP) {
//			currentState = TimerState.RESET;
//		}
//	}
//	
//	private void updateButtonForCurrentState() {
//		Button workoutButton = (Button)((RelativeLayout)mainActivity.findViewById(R.id.workoutView)).findViewById(R.id.workoutStart);
//		if (currentState == TimerState.START) {
//			workoutButton.setText("Pause");
//	        workoutButton.setBackgroundResource(R.drawable.gray_button);
//		} else if (currentState == TimerState.PAUSE) {
//			workoutButton.setText("Resume");
//	        workoutButton.setBackgroundResource(R.drawable.gray_button);
//		} else if (currentState == TimerState.RESUME) {
//			workoutButton.setText("Pause");
//	        workoutButton.setBackgroundResource(R.drawable.gray_button);
//		} else if (currentState == TimerState.RESET) {
//			workoutButton.setText("Start");
//	        workoutButton.setBackgroundResource(R.drawable.green_button);
//		} else if (currentState == TimerState.STOP) {
//			workoutButton.setText("Reset");
//	        workoutButton.setBackgroundResource(R.drawable.gray_button);
//		}
//	}
}
