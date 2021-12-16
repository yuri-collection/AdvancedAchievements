package com.hm.achievement.command.executable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.hm.achievement.utils.FancyMessageSender;

import java.util.Objects;

/**
 * Class in charge of displaying the plugin's help (/ach help).
 *
 * @author Pyves, Yurinann
 * @since 2021/12/15 16:24
 */

@Singleton
@CommandSpec(name = "help", permission = "", minArgs = 0, maxArgs = Integer.MAX_VALUE)
public class HelpCommand extends AbstractCommand {

	private final FancyMessageSender fancyMessageSender;

	private ChatColor configColor;
	private String configIcon;

	private String langCommandList;
	private String langCommandListHover;
	private String langCommandTop;
	private String langCommandTopHover;
	private String langCommandInfo;
	private String langCommandInfoHover;
	private String langCommandBook;
	private String langCommandBookHover;
	private String langCommandWeek;
	private String langCommandWeekHover;
	private String langCommandStats;
	private String langCommandStatsHover;
	private String langCommandMonth;
	private String langCommandMonthHover;
	private String langCommandToggle;
	private String langCommandToggleHover;
	private String langCommandReload;
	private String langCommandReloadHover;
	private String langCommandGenerate;
	private String langCommandGenerateHover;
	private String langCommandInspect;
	private String langCommandInspectHover;
	private String langCommandGive;
	private String langCommandGiveHover;
	private String langCommandAdd;
	private String langCommandAddHover;
	private String langCommandReset;
	private String langCommandResetHover;
	private String langCommandCheck;
	private String langCommandCheckHover;
	private String langCommandDelete;
	private String langCommandDeleteHover;
	private String langTip;

	@Inject
	public HelpCommand(@Named("main") YamlConfiguration mainConfig, @Named("lang") YamlConfiguration langConfig,
			StringBuilder pluginHeader, FancyMessageSender fancyMessageSender) {
		super(mainConfig, langConfig, pluginHeader);
		this.fancyMessageSender = fancyMessageSender;
	}

	@Override
	public void extractConfigurationParameters() {
		super.extractConfigurationParameters();

		configColor = ChatColor.getByChar(Objects.requireNonNull(mainConfig.getString("Color")));
		configIcon = StringEscapeUtils.unescapeJava(mainConfig.getString("Icon"));

		langCommandList = header("/ach list") + langConfig.getString("ach-command-list");
		langCommandListHover = langConfig.getString("ach-command-list-hover");
		langCommandTop = header("/ach top") + langConfig.getString("ach-command-top");
		langCommandTopHover = langConfig.getString("ach-command-top-hover");
		langCommandInfo = header("/ach info") + langConfig.getString("ach-command-info");
		langCommandInfoHover = langConfig.getString("ach-command-info-hover");
		langCommandBook = header("/ach book") + langConfig.getString("ach-command-book");
		langCommandBookHover = langConfig.getString("ach-command-book-hover");
		langCommandWeek = header("/ach week") + langConfig.getString("ach-command-week");
		langCommandWeekHover = langConfig.getString("ach-command-week-hover");
		langCommandStats = header("/ach stats") + langConfig.getString("ach-command-stats");
		langCommandStatsHover = langConfig.getString("ach-command-stats-hover");
		langCommandMonth = header("/ach month") + langConfig.getString("ach-command-month");
		langCommandMonthHover = langConfig.getString("ach-command-month-hover");
		langCommandToggle = header("/ach toggle") + langConfig.getString("ach-command-toggle");
		langCommandToggleHover = langConfig.getString("ach-command-toggle-hover");
		langCommandReload = header("/ach reload") + langConfig.getString("ach-command-reload");
		langCommandReloadHover = langConfig.getString("ach-command-reload-hover");
		langCommandGenerate = header("/ach generate") + langConfig.getString("ach-command-generate");
		langCommandGenerateHover = langConfig.getString("ach-command-generate-hover");
		langCommandGive = header("/ach give &oach player")
				+ translateColorCodes(StringUtils.replaceEach(langConfig.getString("ach-command-give"),
						new String[] { "ACH", "NAME" }, new String[] { "&oach&7", "&oplayer&7" }));
		langCommandGiveHover = langConfig.getString("ach-command-give-hover");
		langCommandInspect = header("/ach inspect &oach")
				+ translateColorCodes(
						StringUtils.replaceOnce(langConfig.getString("ach-command-inspect"), "ACH", "&oach&7"));
		langCommandInspectHover = langConfig.getString("ach-command-inspect-hover");
		langCommandAdd = header("/ach add &ox cat player") + langConfig.getString("ach-command-add");
		langCommandAddHover = langConfig.getString("ach-command-add-hover");
		langCommandReset = header("/ach reset &ocat player")
				+ StringUtils.replaceOnce(langConfig.getString("ach-command-reset"), "CAT", "&ocat&7");
		langCommandResetHover = langConfig.getString("ach-command-reset-hover");
		langCommandCheck = header("/ach check &oach player")
				+ translateColorCodes(StringUtils.replaceEach(langConfig.getString("ach-command-check"),
						new String[] { "ACH", "NAME" }, new String[] { "&oach&7", "&oplayer&7" }));
		langCommandCheckHover = langConfig.getString("ach-command-check-hover");
		langCommandDelete = header("/ach delete &oach player")
				+ translateColorCodes(StringUtils.replaceEach(langConfig.getString("ach-command-delete"),
						new String[] { "ACH", "NAME" }, new String[] { "&oach&7", "&oplayer&7" }));
		langCommandDeleteHover = langConfig.getString("ach-command-delete-hover");
		langTip = ChatColor.GRAY + translateColorCodes(langConfig.getString("ach-tip"));
	}

	private String header(String command) {
		return pluginHeader.toString() + configColor + command + ChatColor.GRAY + " > ";
	}

	@Override
	void onExecute(CommandSender sender, String[] args) {
		// Header.
		sender.sendMessage(configColor + "------------ " + configIcon + translateColorCodes(" &lAdvanced Achievements ")
				+ configColor + configIcon + configColor + " ------------");

		if (sender.hasPermission("achievement.list")) {
			sendJsonClickableHoverableMessage(sender, langCommandList, "/ach list", langCommandListHover);
		}

		if (sender.hasPermission("achievement.top")) {
			sendJsonClickableHoverableMessage(sender, langCommandTop, "/ach top", langCommandTopHover);
		}

		sendJsonClickableHoverableMessage(sender, langCommandInfo, "/ach info", langCommandInfoHover);

		if (sender.hasPermission("achievement.book")) {
			sendJsonClickableHoverableMessage(sender, langCommandBook, "/ach book", langCommandBookHover);
		}

		if (sender.hasPermission("achievement.week")) {
			sendJsonClickableHoverableMessage(sender, langCommandWeek, "/ach week", langCommandWeekHover);
		}

		if (sender.hasPermission("achievement.stats")) {
			sendJsonClickableHoverableMessage(sender, langCommandStats, "/ach stats", langCommandStatsHover);
		}

		if (sender.hasPermission("achievement.month")) {
			sendJsonClickableHoverableMessage(sender, langCommandMonth, "/ach month", langCommandMonthHover);
		}

		if (sender.hasPermission("achievement.toggle")) {
			sendJsonClickableHoverableMessage(sender, langCommandToggle, "/ach toggle", langCommandToggleHover);
		}

		if (sender.hasPermission("achievement.reload")) {
			sendJsonClickableHoverableMessage(sender, langCommandReload, "/ach reload", langCommandReloadHover);
		}

		if (sender.hasPermission("achievement.generate")) {
			sendJsonClickableHoverableMessage(sender, langCommandGenerate, "/ach generate", langCommandGenerateHover);
		}

		if (sender.hasPermission("achievement.inspect")) {
			sendJsonClickableHoverableMessage(sender, langCommandInspect, "/ach inspect ach", langCommandInspectHover);
		}

		if (sender.hasPermission("achievement.give")) {
			sendJsonClickableHoverableMessage(sender, langCommandGive, "/ach give ach name", langCommandGiveHover);
		}

		if (sender.hasPermission("achievement.add")) {
			sendJsonClickableHoverableMessage(sender, langCommandAdd, "/ach add x cat name", langCommandAddHover);
		}

		if (sender.hasPermission("achievement.reset")) {
			sendJsonClickableHoverableMessage(sender, langCommandReset, "/ach reset cat name", langCommandResetHover);
		}

		if (sender.hasPermission("achievement.check")) {
			sendJsonClickableHoverableMessage(sender, langCommandCheck, "/ach check ach name", langCommandCheckHover);
		}

		if (sender.hasPermission("achievement.delete")) {
			sendJsonClickableHoverableMessage(sender, langCommandDelete, "/ach delete ach name", langCommandDeleteHover);
		}

		// Empty line.
		sender.sendMessage(configColor + " ");

		sender.sendMessage(langTip);
	}

	/**
	 * Sends a packet message to the server in order to display a clickable and hoverable message. A suggested command
	 * is displayed in the chat when clicked on, and an additional help message appears when a command is hovered.
	 */
	private void sendJsonClickableHoverableMessage(CommandSender sender, String message, String command, String hover) {
		// Send clickable and hoverable message if sender is a player.
		if (sender instanceof Player) {
			fancyMessageSender.sendHoverableCommandMessage((Player) sender, message, command, hover,
					configColor.name().toLowerCase());
		} else {
			sender.sendMessage(message);
		}
	}
}
