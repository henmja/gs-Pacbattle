package com.github.henmja.bachelor.pacbattle.game;

import java.util.Random;
import java.util.ArrayList;
import java.util.List;

class RandGen {// list with ranges. (ranges used for spawning point for NPCS to
				// avoid overlaping on spawn)
	private List<Integer> rangeList = new ArrayList<>();

	RandGen(int min, int max) {
		this.addRange(min, max);
	}

	int getRandom() {
		return this.rangeList.get(new Random().nextInt(this.rangeList.size()));
	}

	public void addRange(int min, int max) {
		for (int i = min; i <= max; i++) {
			this.rangeList.add(i);
		}
	}

}
