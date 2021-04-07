package com.bins.succession.cmd.commandTabComplete

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.util.StringUtil
import java.util.ArrayList
import java.util.Collections

class SettingsTab : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String> {
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String?> = ArrayList()
        if (args.size == 1) {
            for (p in Bukkit.getOfflinePlayers()) {
                commands.add(p.name)
            }
            StringUtil.copyPartialMatches(args[0], commands, completions)
        } else if (args.size == 2) {
            commands.add("suffix")
            commands.add("prefix")
            StringUtil.copyPartialMatches(args[1], commands, completions)
        } else {
            if (args[1] == "suffix") {
                commands.add("NON")
                commands.add("SPECIAL_OWO")
                commands.add("OWO")
            } else if (args[1] == "prefix") {
                commands.add("DEFAULT")
                commands.add("VIP")
                commands.add("VIPPlus")
                commands.add("MVP")
                commands.add("MVPPlus")
                commands.add("MVPPlusPlus")
            }
            StringUtil.copyPartialMatches(args[2], commands, completions)
        }
        completions.sort()
        return completions
    }
}