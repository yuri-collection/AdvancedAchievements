package com.hm.achievement.listener;

import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.config.AchievementMap;
import com.hm.achievement.db.AbstractDatabaseManager;
import com.hm.achievement.db.CacheManager;
import com.hm.achievement.domain.Achievement;
import com.hm.achievement.domain.Achievement.AchievementBuilder;
import com.hm.achievement.utils.FancyMessageSender;
import com.hm.achievement.utils.PlayerAdvancedAchievementEvent;
import org.bukkit.Server;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.InputStreamReader;
import java.util.*;
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Class for testing PlayerAdvancedAchievementListener. Currently covers AllAchievementsReceivedRewards usage.
 *
 * @author Pyves
 */

@ExtendWith(MockitoExtension.class)
class PlayerAdvancedAchievementListenerTest {

	private static final String PLUGIN_HEADER = "[HEADER]";
	private static final UUID PLAYER_UUID = UUID.randomUUID();

	@Mock
	private Server server;
	@Mock
	private Player player;
	@Mock
	private AbstractDatabaseManager abstractDatabaseManager;
	@Mock
	private AdvancedAchievements plugin;

	PlayerAdvancedAchievementListenerTest() {
	}

	@Test
	void itShouldRegisterNewAchievementInDatabase() {
		AchievementMap achievementMap = new AchievementMap();
		achievementMap.put(new AchievementBuilder().name("connect_1").displayName("Good Choice").build());
		achievementMap.put(new AchievementBuilder().name("place_500_smooth_brick").displayName("Stone Brick Layer").build());
		YamlConfiguration mainConfig = YamlConfiguration
				.loadConfiguration(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/config-reception.yml"))));
		YamlConfiguration langConfig = YamlConfiguration
				.loadConfiguration(new InputStreamReader(Objects.requireNonNull(getClass().getResourceAsStream("/lang.yml"))));
		PlayerAdvancedAchievementListener underTest = new PlayerAdvancedAchievementListener(mainConfig, langConfig, mock(Logger.class),
				new StringBuilder(PLUGIN_HEADER), new CacheManager(plugin, abstractDatabaseManager), plugin, null,
				achievementMap, abstractDatabaseManager, null, new FancyMessageSender(16));
		underTest.extractConfigurationParameters();
		when(player.getUniqueId()).thenReturn(PLAYER_UUID);
		when(player.getName()).thenReturn("DarkPyves");
		when(plugin.getServer()).thenReturn(server);
		doReturn(Collections.singletonList(player)).when(server).getOnlinePlayers();
		Set<String> receivedAchievements = new HashSet<>();
		receivedAchievements.add("connect_1");
		when(abstractDatabaseManager.getPlayerAchievementNames(PLAYER_UUID)).thenReturn(receivedAchievements);
		Achievement achievement = new AchievementBuilder()
				.name("connect_1")
				.displayName("Good Choice")
				.message("Connected for the first time!")
				.build();

		underTest.onPlayerAdvancedAchievementReception(new PlayerAdvancedAchievementEvent(player, achievement));

		verify(abstractDatabaseManager).registerAchievement(eq(PLAYER_UUID), eq("connect_1"), anyLong());
	}

}
