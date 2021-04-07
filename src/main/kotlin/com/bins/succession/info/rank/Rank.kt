package com.bins.succession.info.rank

import java.util.HashMap
import java.util.UUID
import net.md_5.bungee.api.ChatColor
import org.bukkit.entity.Player

object Rank {
    var Rank = HashMap<UUID, Type>()
    var Suffix = HashMap<UUID, Type>()
    fun getRank(uuid: String?): String {
        return Type.valueOf(uuid!!).value
    }

    fun eqauls(to: Type, from: Type): Boolean {
        return to == from
    }

    fun getRankofPlayer(p: Player): String {
        var format = "[@pre] @name" + p.displayName + " [@suf]"
        val pre = when {
            Rank[p.uniqueId] == Type.DEFAULT -> "NON"
            Rank[p.uniqueId] == Type.VIP -> "VIP"
            Rank[p.uniqueId] == Type.VIPPlus -> "VIP+"
            Rank[p.uniqueId] == Type.MVP -> "MVP"
            Rank[p.uniqueId] == Type.MVPPlus -> "MVP+"
            Rank[p.uniqueId] == Type.MVPPlusPlus -> "MVP++"
            else -> "미정"
        }
        val preColor = ChatColor.of("#" + Rank[p.uniqueId]!!.value)
        var preNon = false
        var sufNon = false
        if (Rank[p.uniqueId] == Type.DEFAULT) {
            format = format.replace("[@pre] ", "")
            preNon = true
        } else {
            format = format.replace("[@pre]", "$preColor[$pre]")
            format = format.replace("@name", preColor.toString() + "")
        }
        val suf = if (Suffix[p.uniqueId] == Type.OWO) "owo" else if (Suffix[p.uniqueId] == Type.SPECIAL_OWO
        ) "owo" else "미정"
        val sufColor = ChatColor.of("#" + Suffix[p.uniqueId]!!.value)
        if (Suffix[p.uniqueId] == Type.NON) {
            sufNon = true
            format = format.replace("[@suf]", "")
            format = format.replace("@name", "${ChatColor.of("#" + Type.DEFAULT_NAME.value)}")
        } else {
            format = format.replace("[@suf]", "$sufColor[$suf]")
            format = format.replace("@name", sufColor.toString() + "")
        }
        if (sufNon and preNon) {
            format = format.replace("@name", "${ChatColor.of("#" + Type.DEFAULT_NAME.value)}")
            format = format.replace("@chat", "${ChatColor.of("#" + Type.DEFAULT.value)}")
        }
        format = if ((Suffix[p.uniqueId] == Type.SPECIAL_OWO) or (pre != "NON")) {
            format.replace("@chat", "${ChatColor.of("#FFFFFF")}")
        } else {
            format.replace("@chat", "$preColor")
        }
        return format
    }
}