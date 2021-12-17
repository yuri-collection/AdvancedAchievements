package com.hm.achievement.command.executable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Class in charge of handling the /ach hca command, which displays a small Easter egg in the in-game chat. Run it and
 * you'll see what all this mess is about
 * 
 * @author Pyves
 */

@Singleton
@CommandSpec(name = "hca", permission = "easteregg", minArgs = 1, maxArgs = 1)
public class EasterEggCommand extends AbstractCommand {

	@Inject
	public EasterEggCommand(@Named("main") YamlConfiguration mainConfig, @Named("lang") YamlConfiguration langConfig,
			StringBuilder pluginHeader) {
		super(mainConfig, langConfig, pluginHeader);
	}

	@Override
	void onExecute(CommandSender sender, String[] args) {
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§4\u2592§4\u2592§c\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§4\u2592§4\u2592§4\u2592§c\u2592§c\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§c\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§0\u2592§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§7\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§7\u2592§0\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§8\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§4\u2592§4\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§8\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§4\u2592§4\u2592§6\u2592§6\u2592§8\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§8\u2592§r");
		sender.sendMessage(
				"§0\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§4\u2592§6\u2592§6\u2592§6\u2592§6\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§8\u2592§r");
		sender.sendMessage(
				"§8\u2592§8\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§6\u2592§6\u2592§6\u2592§6\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§r");
		sender.sendMessage(
				"§7\u2592§0\u2592§8\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§7\u2592§6\u2592§6\u2592§4\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§0\u2592§0\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§0\u2592§8\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§8\u2592§8\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§4\u2592§4\u2592§4\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§0\u2592§8\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§8\u2592§8\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§4\u2592§4\u2592§4\u2592§7\u2592§7\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§8\u2592§8\u2592§f\u2592§7\u2592§f\u2592§7\u2592§f\u2592§f\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§4\u2592§4\u2592§4\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§4\u2592§4\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§f\u2592§f\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§4\u2592§4\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§0\u2592§0\u2592§0\u2592§4\u2592§4\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§f\u2592§f\u2592§7\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§4\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§8\u2592§8\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§0\u2592§0\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§0\u2592§0\u2592§0\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
		sender.sendMessage(
				"§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§7\u2592§5\u2592§5\u2592§5\u2592§5\u2592§7\u2592§r");
	}

}
