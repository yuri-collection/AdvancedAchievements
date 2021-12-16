package com.hm.achievement.domain;

import java.util.List;
import java.util.function.Consumer;

import org.bukkit.entity.Player;

/**
 * @author Yurinan
 * @since 2021/12/15 18:04
 */

public class Reward {

	private final List<String> listTexts;
	private final List<String> chatTexts;
	private final Consumer<Player> rewarder;

	public Reward(List<String> listTexts, List<String> chatTexts, Consumer<Player> rewarder) {
		this.listTexts = listTexts;
		this.chatTexts = chatTexts;
		this.rewarder = rewarder;
	}

	public List<String> getListTexts() {
		return listTexts;
	}

	public List<String> getChatTexts() {
		return chatTexts;
	}

	public Consumer<Player> getRewarder() {
		return rewarder;
	}

}
