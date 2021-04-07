package com.bins.succession.cmd.command

import com.bins.succession.info.rank.Rank.getRankofPlayer
import com.bins.succession.info.rank.Rank
import com.bins.succession.info.rank.Type
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class Settings : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        val you = sender as Player
        if (!you.isOp) return false
        if (args.size > 2) {
            val result = Bukkit.getOfflinePlayerIfCached(args[0]) ?: return false
            if (args[1] == "prefix") {
                Rank.Rank[result.uniqueId] = Type.valueOf(args[2])
                you.sendMessage(Rank.Rank[result.uniqueId].toString() + "")
            }
            if (args[1] == "suffix") {
                Rank.Suffix[result.uniqueId] = Type.valueOf(args[2])
                you.sendMessage(Rank.Suffix[result.uniqueId].toString() + "")
            }
            if (!result.isOnline) return false
            val a = Bukkit.getPlayer(args[0]) ?: return false
            a.setPlayerListName(getRankofPlayer(a))
        }
        return false
    }
}