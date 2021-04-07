package com.bins.succession.cmd.command

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.Arrays

class Name : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (args.isNotEmpty()) {
            if (sender is Player) {
                val p = sender
                if (!p.isOp) return false
                if (p.inventory.itemInMainHand.type != Material.AIR) {
                    val item = p.inventory.itemInMainHand
                    val str = java.lang.String.join(" ", *args.copyOfRange(0, args.size))
                    val meta = item.itemMeta
                    meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', str))
                    item.itemMeta = meta
                }
            }
        }
        return false
    }
}