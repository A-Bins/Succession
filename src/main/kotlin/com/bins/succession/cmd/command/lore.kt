package com.bins.succession.cmd.command

import net.md_5.bungee.api.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*

class lore : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        try {
            if (args.size > 1) {
                if (sender is Player) {
                    val p = sender
                    if (!p.isOp) return false
                    if (p.inventory.itemInMainHand.type != Material.AIR) {
                        if (p.inventory.itemInMainHand.itemMeta != null) {
                            val item = p.inventory.itemInMainHand
                            val str = java.lang.String.join(" ", *args.copyOfRange(1, args.size))
                            val i = args[0].toInt()
                            if (item.lore == null) {
                                item.lore = ArrayList(listOf(""))
                            }
                            val li: ArrayList<String> = ArrayList(item.lore)
                            for (a in 0 until i) {
                                if (item.lore!!.size - 1 < i - 1) {
                                    li.add("")
                                    item.lore = li
                                }
                            }
                            li[i - 1] = ChatColor.translateAlternateColorCodes('&', str)
                            item.lore = li
                        }
                    }
                }
            }
        } catch (ex: Exception) {
            sender.sendMessage("§c다시..입력하세욧!")
            ex.printStackTrace()
            return false
        }
        return false
    }
}