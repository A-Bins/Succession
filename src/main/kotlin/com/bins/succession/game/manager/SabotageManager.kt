package com.bins.succession.game.manager

import java.util.Arrays
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.info.data.User
import com.bins.succession.Succession
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.data.Lightable
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import java.util.ArrayList

object SabotageManager {
    @JvmStatic
    fun SabotageStart() {
        Sabotage.SabotageStartReady = true
        var finalI = 200
        val b = Location(Bukkit.getWorld("world"), -854.5, 38.5, -1137.5).block
        (b.blockData as Lightable).isLit = false
        var i = 0
        while (220 > i) {
            val finalI1 = finalI
            Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable runTaskLater@{

        //                if(Sabotage.SabotagePlayers.size() < 8){
        //                    return;
        //                }
                if (finalI1 / 20 == 0) {
                    Bukkit.broadcastMessage("Start and EA Sport")
                    Sabotage.SabotageIsStart = true
                    val c = ArrayList(Arrays.asList(*DyeColor.values()))
                    for (p in Sabotage.SabotagePlayers) {
                        User.IsPlaying[p.uniqueId] = true
                        Sabotage.SabotageReadying[p.uniqueId] = false
                        Sabotage.SabotagePlaying[p.uniqueId] = true
                        c.shuffle()
                        val w = c[0]
                        c.remove(w)
                        val result = w!!.color
                        val Helmet = ItemStack(Material.LEATHER_HELMET)
                        val Helmet_Meta = Helmet.itemMeta as LeatherArmorMeta
                        Helmet_Meta.setColor(result)
                        Helmet.setItemMeta(Helmet_Meta)
                        val ChestPlate = ItemStack(Material.LEATHER_CHESTPLATE)
                        val ChestPlate_Meta = ChestPlate.itemMeta as LeatherArmorMeta
                        ChestPlate_Meta.setColor(result)
                        ChestPlate.setItemMeta(ChestPlate_Meta)
                        val Leggings = ItemStack(Material.LEATHER_LEGGINGS)
                        val Leggings_Meta = Leggings.itemMeta as LeatherArmorMeta
                        Leggings_Meta.setColor(result)
                        Leggings.setItemMeta(Leggings_Meta)
                        val Boots = ItemStack(Material.LEATHER_BOOTS)
                        val Boots_Meta = Boots.itemMeta as LeatherArmorMeta
                        Boots_Meta.setColor(result)
                        Boots.setItemMeta(Boots_Meta)
                        p.equipment!!.setItem(EquipmentSlot.HEAD, Helmet)
                        p.equipment!!.setItem(EquipmentSlot.CHEST, ChestPlate)
                        p.equipment!!.setItem(EquipmentSlot.LEGS, Leggings)
                        p.equipment!!.setItem(EquipmentSlot.FEET, Boots)
                    }
                    return@runTaskLater
                }
                Bukkit.broadcastMessage("" + finalI1 / 20)
            }, i.toLong())
            finalI -= 20
            i += 20
        }
    }
}