package com.bins.succession.cmd.command

import com.bins.succession.friends.Friend
import com.bins.succession.friends.Friend.Companion.getHash
import net.md_5.bungee.api.chat.BaseComponent
import net.md_5.bungee.api.chat.HoverEvent
import net.md_5.bungee.api.chat.TextComponent
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.util.*
import java.util.stream.Collectors

class Friends : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val p: Player = sender
            val f = Friend(getHash()[p.uniqueId])
            if (args.size <= 1) {
                if (args[0] == "목록") {
                    p.sendMessage("§a§m                                                     ")
                    p.sendMessage("                      §a친구 목록                        ")
                    val w = f.friendUUIDList
                    w.forEach { s ->
                        val stringList = f.dates.stream().filter { q : String -> q.contains(", ") && q.split(", ").toTypedArray()[1].contains(q + "") }.collect(Collectors.toList())
                        val date = stringList[0].split(", ").toTypedArray()[0]


                        val isOnline = if (Bukkit.getOfflinePlayer(UUID.fromString(s + "")).isOnline) "§a" + Bukkit.getPlayer(UUID.fromString(s + ""))!!.displayName else "§7" + Bukkit.getOfflinePlayer(UUID.fromString(s + "")).name + "은(는) 오프라인입니다"

                        val player = TextComponent(
                            "*$isOnline"
                        )
                        val playerhover = arrayOf<BaseComponent>(
                            TextComponent(
                                """§a처음 친구를 맺은 날: §7${date.replace("T", " ").replace("\\..*".toRegex(), "")}     
UUID : §7$s"""
                            )
                        )
                        player.hoverEvent = HoverEvent(HoverEvent.Action.SHOW_TEXT, playerhover)

                        val component = TextComponent("      ")
                        component.hoverEvent = null
                        val lastLogin = TextComponent("§a | §a마지막 접속: ")
                        lastLogin.hoverEvent = null


                        component.addExtra(player)
                        component.addExtra(lastLogin)
                        p.sendMessage(component)
                        p.sendMessage("§a§m                                                     ")
                    }
                    return false
                }
            }
        }
        return false
    }
}