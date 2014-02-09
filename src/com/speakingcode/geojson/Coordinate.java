package com.speakingcode.geojson;

public class Coordinate {
	float[] coords;

	public Coordinate(float[] coords) {
		super();
		this.coords = coords;
	}

	public float[] getCoords() {
		return coords;
	}

	public void setCoords(float[] coords) {
		this.coords = coords;
	}
}
