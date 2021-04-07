package com.bins.succession.friends

import com.bins.succession.utilities.util.bb
import org.bukkit.OfflinePlayer
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class Friend(var hash: HashMap<String, LinkedList<String>>?) {
    val isIt: Boolean
        get() = hash != null

    fun containFriend(p: OfflinePlayer): Boolean {
        return hash!!["list"]!!.contains("" + p.uniqueId)
    }

    fun containBan(Target: OfflinePlayer): Boolean {
        return hash!!["ban"]!!.contains("" + Target.uniqueId)
    }

    fun containAccept(p: OfflinePlayer): Boolean {
        return hash!!["accept"]!!.contains("" + p.uniqueId)
    }

    fun containAwait(p: OfflinePlayer): Boolean {
        bb("containAwait : " + hash!!["await"] + ", " + p.name)
        return hash!!["await"]!!.contains("" + p.uniqueId)
    }

    fun isHaveDate(p: OfflinePlayer): Boolean {
        return hash!!["date"]!!.stream().anyMatch { `$`: String ->
            if ((`$` + "").contains(", ")) {
                return@anyMatch (`$` + "").split(", ").toTypedArray()[1].contains(p.name!!)
            }
            false
        }
    }

    fun hasDate(): Boolean {
        return !hash!!["date"]!!.isEmpty()
    }

    fun hasAccept(): Boolean {
        return !hash!!["accept"]!!.isEmpty()
    }

    fun hasAwait(): Boolean {
        return !hash!!["await"]!!.isEmpty()
    }

    val dates: List<String>
        get() = hash!!["date"]!!
    val accepts: List<String>
        get() = hash!!["accept"]!!
    val awaits: List<String>
        get() = hash!!["await"]!!
    val friendUUIDList: List<String>
        get() = hash!!["list"]!!

    fun removeFriend(p: OfflinePlayer): Boolean {
        return if (!containFriend(p)) false else hash!!["list"]!!.remove("" + p.uniqueId)
    }

    fun removeBan(p: OfflinePlayer): Boolean {
        return if (!containBan(p)) false else hash!!["ban"]!!.remove("" + p.uniqueId)
    }

    fun removeAccept(p: OfflinePlayer): Boolean {
        return if (!containAccept(p)) false else hash!!["accept"]!!.remove("" + p.uniqueId)
    }

    fun removeAwait(p: OfflinePlayer): Boolean {
        if (!containAwait(p)) return false
        return hash!!["await"]!!.remove("" + p.uniqueId)
    }

    fun removeDate(p: OfflinePlayer): Boolean {
        if (!isHaveDate(p)) return false
        hash!!["date"]!!.removeAt(hash!!["date"]!!.indexOf(p.uniqueId.toString() + ""))
        return true
    }

    fun addFriend(p: OfflinePlayer): Boolean {
        if (containFriend(p)) return false
        hash!!["list"]!!.add("" + p.uniqueId)
        return true
    }

    fun addBan(p: OfflinePlayer): Boolean {
        return if (containBan(p)) false else hash!!["ban"]!!.add("" + p.uniqueId)
    }

    fun addAwait(p: OfflinePlayer): Boolean {
        if (containAwait(p)) return false
        hash!!["await"]!!.add("" + p.uniqueId)
        bb("addAwait : " + hash!!["await"] + ", " + p.name)
        return true
    }

    fun addAccept(p: OfflinePlayer): Boolean {
        return if (containAccept(p)) false else hash!!["accept"]!!.add("" + p.uniqueId)
    }

    fun addDate(p: OfflinePlayer): Boolean {
        return if (isHaveDate(p)) false else hash!!["date"]!!.add(LocalDateTime.now().toString() + ", " + p.uniqueId)
    }

    companion object {
        private val Friends = HashMap<UUID, HashMap<String, LinkedList<String>>>()
        fun getHash(): HashMap<UUID, HashMap<String, LinkedList<String>>> {
            return Friends
        }
    }
}