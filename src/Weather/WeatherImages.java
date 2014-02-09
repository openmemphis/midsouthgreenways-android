package Weather;

import org.midsouthgreenways.android.R;

public class WeatherImages {

	public static int getImageResource(String condition) {
		
		// if there is no condition passed in return question mark
		if (condition == null) {
			return R.drawable.cond010;
		}
		
		// otherwise determine the appropriate image to return for the condition
		condition = condition.toUpperCase();
		if (condition.equals("PARTLY SUNNY")) {
			return R.drawable.cond003;
	    } else if (condition.equals("SCATTERED THUNDERSTORMS")) {
	    	return R.drawable.cond006;
	    } else if (condition.equals("SHOWERS")) {
	    	return R.drawable.cond005;
	    } else if (condition.equals("SCATTERED SHOWERS")) {
	    	return R.drawable.cond045;
	    } else if (condition.equals("RAIN AND SNOW")) {
	    	return R.drawable.cond053;
	    } else if (condition.equals("OVERCAST")) {
	    	return R.drawable.cond001;
	    } else if (condition.equals("LIGHT SNOW")) {
	    	return R.drawable.cond032;
	    } else if (condition.equals("FREEZING DRIZZLE")) {
	    	return R.drawable.cond047;
	    } else if (condition.equals("CHANCE OF RAIN")) {
	    	return R.drawable.cond015;
	    } else if (condition.equals("SUNNY")) {
	    	return R.drawable.cond007;
	    } else if (condition.equals("CLEAR")) {
	    	return R.drawable.cond007;
	    } else if (condition.equals("MOSTLY SUNNY")) {
	    	return R.drawable.cond003;
	    } else if (condition.equals("PARTLY CLOUDY")) {
	    	return R.drawable.cond003;
	    } else if (condition.equals("MOSTLY CLOUDY")) {
	    	return R.drawable.cond004;
	    } else if (condition.equals("CHANCE OF STORM")) {
	    	return R.drawable.cond015;
	    } else if (condition.equals("RAIN")) {
	    	return R.drawable.cond005;
	    } else if (condition.equals("CHANCE OF SNOW")) {
	    	return R.drawable.cond011;
	    } else if (condition.equals("CLOUDY") || condition.equals("SCATTERED CLOUDS")) {
	    	return R.drawable.cond001;
	    } else if (condition.equals("MIST")) {
	    	return R.drawable.cond045;
	    } else if (condition.equals("STORM")) {
	    	return R.drawable.cond006;
	    } else if (condition.equals("THUNDERSTORM")) {
	    	return R.drawable.cond006;
	    } else if (condition.equals("CHANCE OF TSTORM")) {
	    	return R.drawable.cond022;
	    } else if (condition.equals("SLEET")) {
	    	return R.drawable.cond047;
	    } else if (condition.equals("SNOW")) {
	    	return R.drawable.cond008;
	    } else if (condition.equals("ICY")) {
	    	return R.drawable.cond047;
	    } else if (condition.equals("DUST")) {
	    	return R.drawable.cond051;
	    } else if (condition.equals("FOG")) {
	    	return R.drawable.cond051;
	    } else if (condition.equals("SMOKE")) {
	    	return R.drawable.cond051;
	    } else if (condition.equals("HAZE")) {
	    	return R.drawable.cond051;
	    } else if (condition.equals("FLURRIES")) {
	    	return R.drawable.cond054;
	    } else if (condition.equals("LIGHT RAIN")) {
	    	return R.drawable.cond045;
	    } else if (condition.equals("SNOW SHOWERS")) {
	    	return R.drawable.cond008;
	    } else if (condition.equals("ICE/SNOW")) {
	    	return R.drawable.cond008;
	    } else if (condition.equals("WINDY")) {
	    	return R.drawable.cond050;
	    } else if (condition.equals("SCATTERED SNOW SHOWERS")) {
	    	return R.drawable.cond032;
	    } else if (condition.equals("HAIL")) {
	    	return R.drawable.cond005;
	    } else {
	        return R.drawable.cond010;
	    }
	}
}
