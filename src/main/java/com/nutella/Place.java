package com.nutella;

import com.nutella.place.Hospital;
import com.nutella.place.JarCentral;
import com.nutella.place.Jarena;
import com.nutella.place.Shop;

public enum Place {
	JAR_CENTRAL("Jar Central", "You can reach most places from here - wherever your jar desires.",
			JarCentral.getInstance()),

	JARENA("Jarena", "Spreads from all over the shelf come here to duke it out. Will you be vicjarious?",
			Jarena.getInstance()),

	HAZELNUT_HOSPITAL("Hazelnut Hospital", "Is your jar all cracked up? Are you running low on SP? Nurse Jar can help!",
			Hospital.getInstance()),

	SHOP("Shop", "This is no ordinary shop. With gold, you can upgrade any of your stats!", Shop.getInstance()),

	;

	private final String name;
	private final String info;
	private final Location location;

	private Place(String name, String info, Location location) {
		this.name = name;
		this.info = info;
		this.location = location;
	}

	public String getName() {
		return name;
	}

	public String getInfo() {
		return info;
	}

	public Location getLocation() {
		return location;
	}

	// public static final int JAR_CENTRAL = 0;
	// public static final int JARENA = 1;
	// public static final int HAZELNUT_HOSPITAL = 2;
	// public static final int SHOP = 3;
}
