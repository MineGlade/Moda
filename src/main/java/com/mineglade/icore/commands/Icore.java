package com.mineglade.icore.commands;

import java.util.Arrays;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.mineglade.icore.Main;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class Icore implements CommandExecutor {
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		if (args.length != 1) {
			Bukkit.dispatchCommand(sender, label + " help");
			return true;
		}
		
		if (args[0].equalsIgnoreCase("help") || args[0].equalsIgnoreCase("h")) {
			if (!(sender instanceof Player)) {
				sender.sendMessage(Main.getCommandPrefix() + ChatColor.DARK_GRAY+ "==== " + ChatColor.GREEN + "All iCore Commands"+ ChatColor.DARK_GRAY + " ====");
				sender.sendMessage("/icore help");
				sender.sendMessage("/icore reload");
			} else {
				Player player = (Player) sender;
				player.sendMessage(Main.getCommandPrefix() + ChatColor.DARK_GRAY+ "==== " + ChatColor.GREEN + "All iCore Commands"+ ChatColor.DARK_GRAY + " ====");
				iCoreHelpEntry(player, label, "help", "returns a list of all iCore Commands", "icore.help", "help", "h", "");
				iCoreHelpEntry(player, label, "reload", "reloads all iCore configs.", "icore.reload", "reload ", "rl");
				
			}
		}
		
		if (args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")) {
			Main.instance.reloadConfig();
			sender.sendMessage(Main.getCommandPrefix() + ChatColor.GREEN + "all iCore configs have been reloaded.");
		}
		
		return true;
	}
	
	private void iCoreHelpEntry(Player player, String label, String subcommand, String description, String permission, String... aliases) {
		player.spigot().sendMessage(
				new ComponentBuilder("")
				.append(TextComponent.fromLegacyText(Main.getCommandPrefix()))
				.event((HoverEvent) null)
				.append("/" + label + " " + subcommand).color(ChatColor.GREEN)
				.event(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/" + label + " " + subcommand))
				.event(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
						new ComponentBuilder("")
								.append("/" + label + " " + subcommand).bold(true).color(ChatColor.GREEN)
								.append("\n").bold(false).color(ChatColor.RESET)
								.append(TextComponent.fromLegacyText(ChatColor.GREEN + "  Description: " + ChatColor.GRAY + description))
								.append("\n").color(ChatColor.RESET)
								.append(TextComponent.fromLegacyText(ChatColor.GREEN + "  Usage: " + ChatColor.GRAY + "/" + label + " " + subcommand))
								.append("\n").color(ChatColor.RESET)
								.append(TextComponent.fromLegacyText(ChatColor.GREEN + "  Aliases: " + ChatColor.GRAY + String.join(", ", Arrays.asList(aliases).stream().map((s) -> String.format("/%s %s", label, s)).collect(Collectors.toSet()))))
								.append("\n").color(ChatColor.RESET)
								.append(TextComponent.fromLegacyText(ChatColor.GREEN + "  Permission: " + ChatColor.WHITE + permission))
								.create()))
					.create());
		
	}
	
}