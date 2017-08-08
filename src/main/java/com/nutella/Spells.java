package com.nutella;

import java.util.Map;
import java.util.HashMap;

public class Spells {
	public static final int SLAM = 0x1;
	public static final int BARRAGE = 0x2;
	public static final int PUMMEL = 0x4;
	public static final int HARDEN = 0x8;

	private static Map<Integer, Integer> spellCosts;

	static {
		spellCosts = new HashMap<Integer, Integer>();
		spellCosts.put(SLAM, 4);
		spellCosts.put(BARRAGE, 6);
		spellCosts.put(PUMMEL, 8);
		spellCosts.put(HARDEN, 2);
	}

	public static int cost(int spell) {
		return spellCosts.get(spell);
	}
}
