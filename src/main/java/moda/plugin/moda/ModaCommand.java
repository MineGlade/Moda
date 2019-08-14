package moda.plugin.moda;

import java.util.stream.Collectors;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import moda.plugin.moda.menu.ModaMenu;
import moda.plugin.moda.modules.Module;
import moda.plugin.moda.modules.Modules;
import moda.plugin.moda.utils.storage.ModuleStorageHandler;

public class ModaCommand implements CommandExecutor {

	String prefix = Moda.getPrefix();

	@Override
	public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
		if (args.length == 0) {
			if (sender instanceof Player) {
				new ModaMenu(((Player) sender)).open();
			} else {
				sender.sendMessage("You cannot open the GUI from the console");
			}
		}
		if (args.length == 1 && args[0].equals("loaded")) {
			sender.sendMessage(String.join(", ",
					Modules.LOADED.stream().map(Module::getName).collect(Collectors.toList()).toArray(new String[] {})));
		} else if (args.length == 1 && args[0].equals("enabled")) {
				sender.sendMessage(String.join(", ",
						Modules.ENABLED.stream().map(Module::getName).collect(Collectors.toList()).toArray(new String[] {})));
		} else if (args.length == 2 && args[0].equals("enable")) {
			final Module<? extends ModuleStorageHandler> module = Modules.getLoadedModuleByName(args[1]);
			if (module == null) {
				sender.sendMessage("This module does not exist or is not loaded");
				return true;
			}

			if (Modules.ENABLED.contains(module)) {
				sender.sendMessage("This module is already enabled");
				return true;
			}

			try {
				module.enable();
				sender.sendMessage("Module enabled");
			} catch (final Exception e) {
				sender.sendMessage("An error occured while enabling the module. Check console for more info.");
				sender.sendMessage(e.toString());
				e.printStackTrace();
			}
		} else if (args.length == 2 && args[0].equals("disable")) {
			final Module<? extends ModuleStorageHandler> module = Modules.getLoadedModuleByName(args[1]);
			if (module == null) {
				sender.sendMessage("This module does not exist or is not loaded");
				return true;
			}

			if (!Modules.ENABLED.contains(module)) {
				sender.sendMessage("This module is already disabled");
				return true;
			}

			try {
				module.disable();
				sender.sendMessage("Module disabled");
			} catch (final Exception e) {
				sender.sendMessage("An error occured while disabling the module. Check console for more info.");
				sender.sendMessage(e.toString());
				e.printStackTrace();
			}
		} else if (args.length == 2 && args[0].equals("load")) {
			try {
				Modules.load(args[1]);
			} catch (final Exception e) {
				sender.sendMessage(e.getMessage());
				e.printStackTrace();
			}
		} else {
			return false;
		}
		return true;
	}



}
