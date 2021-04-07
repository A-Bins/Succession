package com.bins.succession.cmd.commandTabComplete

import com.bins.succession.friends.Friend

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.util.StringUtil
import java.util.*

class FriendTabComplete : TabCompleter {
    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<String>
    ): List<String> {
        val completions: MutableList<String> = ArrayList()
        val commands: MutableList<String> = ArrayList()
        if (args.size == 1) {
            commands.add("추가")
            commands.add("삭제")
            commands.add("요청")
            commands.add("목록")
            commands.add("수락")
            commands.add("거절")
            StringUtil.copyPartialMatches(args[0], commands, completions)
        }

        else if(args.isNotEmpty()) {
            val f = Friend(Friend.getHash()[(sender as Player).uniqueId])

            when(args[0]){
                "추가","요청" ->{
                    Arrays.stream(Bukkit.getOfflinePlayers()).filter { s ->
                        !f.friendUUIDList.contains("${s.uniqueId}") && !s.name.equals(sender.getName())
                    }.forEach { s ->
                        commands.add(s.name!!)
                    }
                    StringUtil.copyPartialMatches(args[1], commands, completions)
                }
                "삭제" -> {
                    f.friendUUIDList.forEach{s -> commands.add(Bukkit.getOfflinePlayer(UUID.fromString(s)).name!!)}
                    StringUtil.copyPartialMatches(args[1], commands, completions)
                }
                "수락", "거절" -> {
                    f.accepts.forEach{s -> commands.add(Bukkit.getOfflinePlayer(UUID.fromString(s)).name!!)}
                    StringUtil.copyPartialMatches(args[1], commands, completions)
                }

            }

        }

        return completions
    }
}
