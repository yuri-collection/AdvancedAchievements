package com.hm.achievement.runnable;

import java.util.*;
import java.util.function.BiConsumer;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.util.NumberConversions;

import com.hm.achievement.category.Category;
import com.hm.achievement.category.NormalAchievements;
import com.hm.achievement.config.AchievementMap;
import com.hm.achievement.db.CacheManager;
import com.hm.achievement.lifecycle.Cleanable;
import com.hm.achievement.utils.StatisticIncreaseHandler;

/**
 * Class used to monitor distances travelled by players for the different available categories.
 * 
 * @author Pyves, Yurinann
 * @since 2021/12/16 14:21
 */

@Singleton
public class AchieveDistanceRunnable extends StatisticIncreaseHandler implements Cleanable, Runnable {

	private final Map<UUID, Location> playerLocations = new HashMap<>();
	private final Map<EntityType, BiConsumer<Integer, Player>> entityTypeBiConsumerMap = new HashMap<>();
	private final Set<Category> disabledCategories;

	private boolean configIgnoreVerticalDistance;

	@Inject
	public AchieveDistanceRunnable(@Named("main") YamlConfiguration mainConfig, AchievementMap achievementMap,
			CacheManager cacheManager, Set<Category> disabledCategories) {
		super(mainConfig, achievementMap, cacheManager);
		this.disabledCategories = disabledCategories;
		entityTypeBiConsumerMap.put(EntityType.HORSE, (dist, p) -> updateDistance(dist, p, NormalAchievements.DISTANCEHORSE));
		entityTypeBiConsumerMap.put(EntityType.PIG, (dist, p) -> updateDistance(dist, p, NormalAchievements.DISTANCEPIG));
		entityTypeBiConsumerMap.put(EntityType.MINECART, (dist, p) -> updateDistance(dist, p, NormalAchievements.DISTANCEMINECART));
		entityTypeBiConsumerMap.put(EntityType.BOAT, (dist, p) -> updateDistance(dist, p, NormalAchievements.DISTANCEBOAT));
		entityTypeBiConsumerMap.put(EntityType.LLAMA, (dist, p) -> updateDistance(dist, p, NormalAchievements.DISTANCELLAMA));
	}

	@Override
	public void extractConfigurationParameters() {
		super.extractConfigurationParameters();

		configIgnoreVerticalDistance = mainConfig.getBoolean("IgnoreVerticalDistance");
	}

	@Override
	public void cleanPlayerData() {
		playerLocations.keySet().removeIf(player -> !Bukkit.getOfflinePlayer(player).isOnline());
	}

	@Override
	public void run() {
		Bukkit.getOnlinePlayers().forEach(this::validateMovementAndUpdateDistance);
	}

	public void updateLocation(UUID uuid, Location location) {
		playerLocations.put(uuid, location);
	}

	/**
	 * Update distances and store them into server's memory until player disconnects.
	 * 
	 * @param player
	 */
	private void validateMovementAndUpdateDistance(Player player) {
		Location currentLocation = player.getLocation();
		Location previousLocation = playerLocations.put(player.getUniqueId(), currentLocation);

		// If player location not found or if player has changed world, ignore previous location.
		// Evaluating distance would give an exception.
		if (previousLocation == null || !Objects.requireNonNull(previousLocation.getWorld()).getUID().equals(player.getWorld().getUID())) {
			return;
		}

		int difference = getDistanceDifference(previousLocation, currentLocation);
		// Player has not moved.
		if (difference == 0L) {
			return;
		}

		if (player.isInsideVehicle()) {
			EntityType vehicleType = Objects.requireNonNull(player.getVehicle()).getType();
			if (entityTypeBiConsumerMap.containsKey(vehicleType)) {
				entityTypeBiConsumerMap.get(vehicleType).accept(difference, player);
			}
		} else if (player.isGliding()) {
			updateDistance(difference, player, NormalAchievements.DISTANCEGLIDING);
		} else if (player.isSneaking()) {
			updateDistance(difference, player, NormalAchievements.DISTANCESNEAKING);
		} else if (!player.isFlying()) {
			updateDistance(difference, player, NormalAchievements.DISTANCEFOOT);
		}
	}

	/**
	 * Calculates the difference between the player's last location and his current one. May ignore the vertical axis or
	 * not depending on configuration.
	 * 
	 * @param previousLocation
	 * @param currentLocation
	 * 
	 * @return difference
	 */
	private int getDistanceDifference(Location previousLocation, Location currentLocation) {
		if (configIgnoreVerticalDistance) {
			double xSquared = NumberConversions.square(previousLocation.getX() - currentLocation.getX());
			double zSquared = NumberConversions.square(previousLocation.getZ() - currentLocation.getZ());
			return (int) Math.sqrt(xSquared + zSquared);
		} else {
			return (int) previousLocation.distance(currentLocation);
		}
	}

	/**
	 * Updates distance if all conditions are met and awards achievements if necessary.
	 * 
	 * @param difference
	 * @param player
	 * @param category
	 */
	private void updateDistance(int difference, Player player, NormalAchievements category) {
		if (!shouldIncreaseBeTakenIntoAccount(player, category) || disabledCategories.contains(category)) {
			return;
		}

		long distance = cacheManager.getAndIncrementStatisticAmount(category, player.getUniqueId(), difference);
		checkThresholdsAndAchievements(player, category, distance);
	}

}
