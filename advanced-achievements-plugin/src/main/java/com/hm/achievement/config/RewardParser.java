package com.hm.achievement.config;

import java.util.*;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.hm.achievement.AdvancedAchievements;
import com.hm.achievement.domain.Reward;
import com.hm.achievement.utils.StringHelper;

import net.milkbowl.vault.economy.Economy;

/**
 * Class in charge of handling the rewards for achievements.
 *
 * @author Pyves, Yurinann
 * @since 2021/12/15 17:04
 */

@Singleton
public class RewardParser {

	private static final Pattern MULTIPLE_REWARDS_SPLITTER = Pattern.compile(";\\s*");

	private final YamlConfiguration mainConfig;
	private final YamlConfiguration langConfig;
	private final Server server;

	/**
	 * Used for Vault plugin integration.
	 */
	private Economy economy;

	@Inject
	public RewardParser(@Named("main") YamlConfiguration mainConfig, @Named("lang") YamlConfiguration langConfig,
			AdvancedAchievements advancedAchievements) {
		this.mainConfig = mainConfig;
		this.langConfig = langConfig;
		this.server = advancedAchievements.getServer();
		// Try to retrieve an Economy instance from Vault.
		if (server.getPluginManager().isPluginEnabled("Vault")) {
			RegisteredServiceProvider<Economy> rsp = server.getServicesManager().getRegistration(Economy.class);
			if (rsp != null) {
				economy = rsp.getProvider();
			}
		}
	}

	public Economy getEconomy() {
		return economy;
	}

	public List<Reward> parseRewards(String path) {
		ConfigurationSection configSection = mainConfig.getConfigurationSection(path);
		List<Reward> rewards = new ArrayList<>();
		if (configSection != null) {
			if (economy != null && configSection.contains("Money")) {
				rewards.add(parseMoneyReward(configSection));
			}
			if (configSection.contains("Item") || configSection.contains("Items")) {
				rewards.add(parseItemReward(configSection));
			}
			if (configSection.contains("Experience")) {
				rewards.add(parseExperienceReward(configSection));
			}
			if (configSection.contains("IncreaseMaxHealth")) {
				rewards.add(parseIncreaseMaxHealthReward(configSection));
			}
			if (configSection.contains("IncreaseMaxOxygen")) {
				rewards.add(parseIncreaseMaxOxygenReward(configSection));
			}
			if (configSection.contains("Command") || configSection.contains("Commands")) {
				rewards.add(parseCommandReward(configSection));
			}
		}
		return rewards;
	}

	private Reward parseMoneyReward(ConfigurationSection configSection) {
		int amount = configSection.getInt("Money");
		String currencyName = amount > 1 ? economy.currencyNamePlural() : economy.currencyNameSingular();
		String listText = StringUtils.replaceOnce(langConfig.getString("list-reward-money"), "AMOUNT",
				amount + " " + currencyName);
		String chatText = ChatColor.translateAlternateColorCodes('&',
				Objects.requireNonNull(StringUtils.replaceOnce(langConfig.getString("money-reward-received"), "AMOUNT",
						amount + " " + currencyName)));
		Consumer<Player> rewarder = player -> economy.depositPlayer(player, amount);
		return new Reward(Collections.singletonList(listText), Collections.singletonList(chatText), rewarder);
	}

	private Reward parseItemReward(ConfigurationSection configSection) {
		List<String> listTexts = new ArrayList<>();
		List<String> chatTexts = new ArrayList<>();
		List<ItemStack> itemStacks = new ArrayList<>();

		String itemPath = configSection.contains("Item") ? "Item" : "Items";
		ItemStack itemStack = new ItemStack(Material.STONE);

		for (String item : getOneOrManyConfigStrings(configSection, itemPath)) {
			String[] parts = Arrays.stream(item.split(";")).map(String::trim).toArray(String[]::new);
			int amount = 1;
			String name = "";
			for (String part : parts) {
				if (part.matches("(material: *)(?<material>[A-Za-z_]+) *")) {
					Matcher matcher = Pattern.compile("(material: *)(?<material>[A-Za-z_]+) *").matcher(part);
					if (matcher.find()) {
						itemStack.setType(Objects.requireNonNull(Material.matchMaterial(matcher.group("material"))));
					}
				} else if (part.matches("(enchantments: *)(?<name>[A-Za-z_]+=\\d+.*)")) {
					Matcher matcher = Pattern.compile("(?<name>[A-Za-z_]+)=(?<level>\\d+)").matcher(part);
					while (matcher.find()) {
						String enchantName = matcher.group("name");
						Enchantment enchantment = Enchantment.getByKey(new NamespacedKey(NamespacedKey.MINECRAFT, enchantName.toLowerCase()));
						if (enchantment == null) {
							Bukkit.getLogger().warning("Error when parsing Enchantment: " + enchantName + " in " + part);
							continue;
						}
						itemStack.addUnsafeEnchantment(enchantment, Integer.parseInt(matcher.group("level")));
					}
				} else if (part.matches("lore:.*")) {
					Matcher matcher = Pattern.compile("lore:(?<lines>.*)").matcher(part);
					if (matcher.find()) {
						ItemMeta itemMeta = itemStack.getItemMeta();
						List<String> lines = Arrays.stream(matcher.group("lines").split(",")).collect(Collectors.toList());
						if (itemMeta != null) {
							itemMeta.setLore(lines);
						}
						itemStack.setItemMeta(itemMeta);
					}
				} else if (part.matches("name:.*")) {
					Matcher matcher = Pattern.compile("name:(?<name>.*)").matcher(part);
					if (matcher.find()) {
						ItemMeta itemMeta = itemStack.getItemMeta();
						name = matcher.group("name");
						if (itemMeta != null) {
							itemMeta.setDisplayName(name);
						}
						itemStack.setItemMeta(itemMeta);
					}
				} else if (part.matches("amount:\\d+")) {
					Matcher matcher = Pattern.compile("amount:(?<amount>\\d+)").matcher(part);
					if (matcher.find()) {
						amount = Integer.parseInt(matcher.group("amount"));
						itemStack.setAmount(amount);
					}
				} else {
					Bukkit.getLogger().warning("Error when parsing part of reward-item: " + part);
				}
			}

			if (amount == 1) {
				listTexts.add(StringUtils.replaceEach(langConfig.getString("list-reward-single-item"),
						new String[]{"AMOUNT", "ITEM"}, new String[]{Integer.toString(amount), name}));
			} else {
				listTexts.add(StringUtils.replaceEach(langConfig.getString("list-reward-item"),
						new String[]{"AMOUNT", "ITEM"}, new String[]{Integer.toString(amount), name}));
			}
			chatTexts.add(StringUtils.replaceEach(langConfig.getString("item-reward-received"),
					new String[]{"AMOUNT", "ITEM"}, new String[]{Integer.toString(amount), name}));
			itemStacks.add(itemStack);
		}

		Consumer<Player> rewarder = player -> itemStacks.forEach(item -> {
			ItemStack playerItem = item.clone();
			ItemMeta itemMeta = playerItem.getItemMeta();
			if (itemMeta != null && itemMeta.hasDisplayName()) {
				itemMeta.setDisplayName(StringHelper.replacePlayerPlaceholders(itemMeta.getDisplayName(), player));
				playerItem.setItemMeta(itemMeta);
			}

			Map<Integer, ItemStack> leftoverItem = player.getInventory().addItem(playerItem);
			for (ItemStack itemToDrop : leftoverItem.values()) {
				player.getWorld().dropItem(player.getLocation(), itemToDrop);
			}
		});
		return new Reward(listTexts, chatTexts, rewarder);
	}

	private Reward parseExperienceReward(ConfigurationSection configSection) {
		int amount = configSection.getInt("Experience");
		String listText = StringUtils.replaceOnce(langConfig.getString("list-reward-experience"), "AMOUNT",
				Integer.toString(amount));
		String chatText = ChatColor.translateAlternateColorCodes('&',
				Objects.requireNonNull(StringUtils.replaceOnce(langConfig.getString("experience-reward-received"), "AMOUNT",
						Integer.toString(amount))));
		Consumer<Player> rewarder = player -> player.giveExp(amount);
		return new Reward(Collections.singletonList(listText), Collections.singletonList(chatText), rewarder);
	}

	private Reward parseIncreaseMaxHealthReward(ConfigurationSection configSection) {
		int amount = configSection.getInt("IncreaseMaxHealth");
		String listText = StringUtils.replaceOnce(langConfig.getString("list-reward-increase-max-health"), "AMOUNT",
				Integer.toString(amount));
		String chatText = ChatColor.translateAlternateColorCodes('&',
				Objects.requireNonNull(StringUtils.replaceOnce(langConfig.getString("increase-max-health-reward-received"), "AMOUNT",
						Integer.toString(amount))));
		Consumer<Player> rewarder = player -> {
			AttributeInstance playerAttribute = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
			assert playerAttribute != null;
			playerAttribute.setBaseValue(playerAttribute.getBaseValue() + amount);
		};
		return new Reward(Collections.singletonList(listText), Collections.singletonList(chatText), rewarder);
	}

	private Reward parseIncreaseMaxOxygenReward(ConfigurationSection configSection) {
		int amount = configSection.getInt("IncreaseMaxOxygen");
		String listText = StringUtils.replaceOnce(langConfig.getString("list-reward-increase-max-oxygen"), "AMOUNT",
				Integer.toString(amount));
		String chatText = ChatColor.translateAlternateColorCodes('&',
				Objects.requireNonNull(StringUtils.replaceOnce(langConfig.getString("increase-max-oxygen-reward-received"), "AMOUNT",
						Integer.toString(amount))));
		Consumer<Player> rewarder = player -> player.setMaximumAir(player.getMaximumAir() + amount);
		return new Reward(Collections.singletonList(listText), Collections.singletonList(chatText), rewarder);
	}

	private Reward parseCommandReward(ConfigurationSection configSection) {
		String displayPath = configSection.contains("Command") ? "Command.Display" : "Commands.Display";
		List<String> listTexts = getOneOrManyConfigStrings(configSection, displayPath);
		List<String> chatTexts = listTexts.stream()
				.map(message -> StringUtils.replace(langConfig.getString("custom-command-reward"), "MESSAGE", message))
				.collect(Collectors.toList());
		String executePath = configSection.contains("Command") ? "Command.Execute" : "Commands.Execute";
		Consumer<Player> rewarder = player -> getOneOrManyConfigStrings(configSection, executePath).stream()
				.map(command -> StringHelper.replacePlayerPlaceholders(command, player))
				.forEach(command -> server.dispatchCommand(server.getConsoleSender(), command));
		return new Reward(listTexts, chatTexts, rewarder);
	}

	private List<String> getOneOrManyConfigStrings(ConfigurationSection configSection, String path) {
		if (configSection.isList(path)) {
			// Real YAML list.
			return configSection.getStringList(path);
		}
		String configString = configSection.getString(path);
		if (configString != null) {
			// Either a list of strings separate by "; " (old configuration style), or a single string.
			return Arrays.asList(MULTIPLE_REWARDS_SPLITTER.split(StringUtils.normalizeSpace(configString)));
		}
		return Collections.emptyList();
	}

}
