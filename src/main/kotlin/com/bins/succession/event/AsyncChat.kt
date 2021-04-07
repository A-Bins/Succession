package com.bins.succession.event

import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.info.rank.Rank
import com.bins.succession.info.rank.Type
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerChatEvent
import java.lang.Exception
import java.util.ArrayList

class AsyncChat : Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    fun onChat(e: AsyncPlayerChatEvent) {
        val p = e.player
        val removes = ArrayList<Player>()
        if (!Sabotage.SabotagePlaying.containsKey(p.uniqueId)) return
        if (Sabotage.SabotagePlaying[e.player.uniqueId]!!) {
            for (listen in e.recipients) {
                if (!Sabotage.SabotagePlaying[listen.uniqueId]!!) removes.add(listen)
            }
        } else {
            for (listen in e.recipients) {
                if (Sabotage.SabotagePlaying[listen.uniqueId]!!) removes.add(listen)
            }
        }
        e.recipients.removeAll(removes)
        e.format = e.format.replace("[", "")
        e.format = e.format.replace("]", "")
        e.format = "[@pre] @suf | @name%1\$s " + ChatColor.of("#7b7b7b") + ">> §r@chat%2\$s"
        val pre = if (Rank.Rank[p.uniqueId] === Type.DEFAULT) "NON" else if (Rank.Rank[p.uniqueId] === Type.VIP
        ) "VIP" else if (Rank.Rank[p.uniqueId] === Type.VIPPlus) "VIP+" else if (Rank.Rank[p.uniqueId] === Type.MVP
        ) "MVP" else if (Rank.Rank[p.uniqueId] === Type.MVPPlus) "MVP+" else if (Rank.Rank[p.uniqueId] === Type.MVPPlusPlus
        ) "MVP++" else "미정"
        val pre_color = ChatColor.of("#" + Rank.Rank[p.uniqueId]!!.value)
        var preNon = false
        var sufNon = false
        if (Rank.Rank[p.uniqueId] === Type.DEFAULT) {
            e.format = e.format.replace("[@pre] ", "")
            preNon = true
        } else {
            e.format = e.format.replace("[@pre]", "$pre_color[$pre]")
            e.format = e.format.replace("@name", pre_color.toString() + "")
        }
        val suf = if (Rank.Suffix[p.uniqueId] === Type.OWO) "owo" else if (Rank.Suffix[p.uniqueId] === Type.SPECIAL_OWO
        ) "owo" else "미정"
        val suf_color = ChatColor.of("#" + Rank.Suffix[p.uniqueId]!!.value)
        if (Rank.Suffix[p.uniqueId]!! === Type.NON) {
            sufNon = true
            e.format = e.format.replace("@suf ", "")
            e.format = e.format.replace("@name", ChatColor.of("#" + Type.DEFAULT_NAME.value).toString() + "")
        } else {
            e.format = e.format.replace("@suf", suf_color.toString() + suf)
            e.format = e.format.replace("@name", suf_color.toString() + "")
        }
        if (sufNon and preNon) {
            e.format = e.format.replace("| ", "")
            e.format = e.format.replace("@name", ChatColor.of("#" + Type.DEFAULT_NAME.value).toString() + "")
            e.format = e.format.replace("@chat", ChatColor.of("#" + Type.DEFAULT.value).toString() + "")
        }
        if ((Rank.Suffix[p.uniqueId]!! === Type.SPECIAL_OWO) or (pre != "NON")) {
            e.format = e.format.replace("@chat", ChatColor.of("#FFFFFF").toString() + "")
        } else {
            e.format = e.format.replace("@chat", pre_color.toString() + "")
        }
        var message = e.message
        if (message.contains("<") and message.contains(">")) {
            var i = 0
            val output = CharArray(message.toCharArray().size)
            val input = message.toCharArray()
            for ((i2, c) in message.toCharArray().withIndex()) {
                if (c == '<') {
                    output[i] = message.toCharArray()[i2 + 1]
                    input[i2 + 1] = (i + '0'.toInt()).toChar()
                    i++
                }
            }
            message = String(input)
            var hex: String
            var after: String
            try {
                for (a in 0 until i) {
                    hex = message.split("<").toTypedArray()[1].split(">").toTypedArray()[0]
                    hex = "" + output[a] + hex[1] + hex[2] + hex[3] + hex[4] + hex[5]
                    after = "<" + message.split("<").toTypedArray()[1].split(">").toTypedArray()[0] + ">"
                    message = message.replace(after, "" + ChatColor.of("#$hex"))
                    e.message = message
                }
            } catch (w: Exception) {
                e.isCancelled = true
                p.sendMessage("다시..적어요..ㅎㅎ;;")
            }
        }
    }
}