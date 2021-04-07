package com.bins.succession.game.vars.Street

import java.util.HashMap
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Item
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object StreetSuv {
    val StreetWarJoin = Bukkit.createInventory(null, 27, "시가전")
    val StreetSingleWarChannelInv = Bukkit.createInventory(null, 27, "채널")
    val DropItemLocation = ArrayList<String>()
    val DropItems = ArrayList<Item>()
    val StreetSingleWarChannel1 = ArrayList<Player>()
    val StreetSingleWarChannel2 = ArrayList<Player>()
    var StreetSingleWarStateChannel1 = false
    var StreetSingleWarStateChannel2 = false
    val Boolean = HashMap<UUID, Boolean>()
    val StreetSingleWarKillChannel1 = HashMap<UUID, Int>()
    val Items = ArrayList(
        listOf(
            ItemStack(Material.GOLDEN_CARROT, 3),
            ItemStack(Material.IRON_SWORD),
            ItemStack(Material.SHIELD),
            ItemStack(Material.IRON_HELMET),
            ItemStack(Material.IRON_CHESTPLATE),
            ItemStack(Material.IRON_LEGGINGS),
            ItemStack(Material.IRON_BOOTS)
        )
    )
    var StreetSingleWarTimerChannel1 = 20
    var StreetSingleWarSupplyChannel1 = 120
    var StreetSingleWarSupplyStateChannel1 = false
    var StreetSingleWarGlowChannel1 = 60
    var StreetSingleWarSubStateChannel1 = false
    var StreetSingleWarSubStateChannel2 = false
    var StreetSingleWarInvincibility = false
    val StreetSingleWarSpawnLocationChannel1 = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 1126.5, 55.0 + 70.0, 690.5)
        )
    ) //    public static ArrayList<Location> StreetSingleWarSpawnLocationChannel2 = new ArrayList<>(listOf(new Location()));
}