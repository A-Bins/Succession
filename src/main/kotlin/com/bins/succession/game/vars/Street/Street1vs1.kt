package com.bins.succession.game.vars.Street

import java.util.HashMap
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.entity.Player
import java.util.ArrayList

object Street1vs1 {
    val street1v1inv = Bukkit.createInventory(null, 27, "§71 vs 1 대전")
    val queueList = ArrayList<String>()
    val queueType = HashMap<UUID, String>()
    val youDisplayName = HashMap<UUID, String>()
    val youRoomID = HashMap<UUID, Int>()
    val you = HashMap<UUID, Player>()
    val playing = HashMap<UUID, Boolean>()
    var normalRoomState1 = false
    var normalRoomState2 = false
    val normalRoom1 = ArrayList<Player>()
    val normalRoom2 = ArrayList<Player>()
    val normalWait = ArrayList<Player>()
    val normalLocation1 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1016.5),
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1070.5)
        )
    )
    val normalLocation2 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 990.5, 37.5, 1014.5),
            Location(Bukkit.getWorld("world"), 990.5, 37.5, 1070.5)
        )
    )
    var singleRoomState1 = false
    var singleRoomState2 = false
    val singleRoom1 = ArrayList<Player>()
    val singleRoom2 = ArrayList<Player>()
    val singleWait = ArrayList<Player>()
    val singleLocation1 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1123.5),
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1176.5)
        )
    )
    val singleLocation2 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 992.5, 37.5, 1124.5),
            Location(Bukkit.getWorld("world"), 992.5, 37.5, 1176.5)
        )
    )
    var comboRoomState1 = false
    var comboRoomState2 = false
    val comboRoom1 = ArrayList<Player>()
    val comboRoom2 = ArrayList<Player>()
    val comboWait = ArrayList<Player>()
    val comboLocation1 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1283.5),
            Location(Bukkit.getWorld("world"), 878.5, 37.5, 1232.5)
        )
    )
    val comboLocation2 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 990.5, 37.5, 1283.5),
            Location(Bukkit.getWorld("world"), 990.5, 37.5, 1229.5)
        )
    )
}