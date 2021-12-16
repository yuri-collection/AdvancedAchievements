package com.hm.achievement.lifecycle;

import java.util.Set;

import javax.inject.Inject;

/**
 * @author Yurinan
 * @since 2021/12/15 17:27
 */

public class Cleaner implements Runnable {

	private final Set<Cleanable> cleanables;

	@Inject
	public Cleaner(Set<Cleanable> cleanables) {
		this.cleanables = cleanables;
	}

	@Override
	public void run() {
		cleanables.forEach(Cleanable::cleanPlayerData);
	}

}
