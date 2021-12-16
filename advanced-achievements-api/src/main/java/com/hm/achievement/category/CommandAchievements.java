package com.hm.achievement.category;

/**
 * Category driven by the /ach give command rather than statistics.
 *
 * @author Pyves
 */

public class CommandAchievements implements Category {

	public static final CommandAchievements COMMANDS = new CommandAchievements();

	private CommandAchievements() {
		// Not called externally.
	}

	@Override
	public String toString() {
		return "Commands";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toDBName() {
		return "";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String toPermName() {
		return "";
	}

}
