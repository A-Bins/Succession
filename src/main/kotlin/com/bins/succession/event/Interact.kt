package com.bins.succession.event

import com.bins.succession.game.manager.RaceManager
import com.bins.succession.game.manager.StreetManager
import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.info.data.User
import com.bins.succession.Succession
import com.bins.succession.utilities.util
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

/*
BlockData data = Bukkit.createBlockData("minecraft:campfire[facing="
        +b.getBlockData().getAsString().split("facing=")[1].split(",")[0]
        +",lit=false,signal_fire=false,waterlogged=false]");
b.setBlockData(data);
*/
class Interact : Listener {
    @EventHandler
    fun onClick(e: PlayerInteractEvent) {
        val p = e.player
        val uuid: UUID = p.uniqueId
        if (User.IsClick[uuid]!!) return
        User.IsClick[uuid] = true
        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable { User.IsClick[uuid] = false }, 2)
        if (e.clickedBlock != null) {
            val b = e.clickedBlock
            if (b!!.type == Material.CAMPFIRE) {
                if (b.blockData.asString.contains("lit=false")) {
                    if (Sabotage.EmbersFailed[uuid]!!) {
                        p.sendActionBar("§7조금 지친듯 하다..")
                        p.playSound(p.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
                        return
                    }
                    if (Sabotage.EmbersIsUse) {
                        if (Sabotage.EmbersWhois === p) {
                            val i: Int = Sabotage.EmbersProcessesValue[uuid]!!
                            Sabotage.EmbersProcessesValue[uuid] = i + 2
                            p.playSound(p.location, Sound.ENTITY_FISHING_BOBBER_RETRIEVE, 1f, 2f)
                            return
                        } else {
                            p.sendActionBar("§7누군가 불씨를 살리고 있다....")
                            p.playSound(p.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
                        }
                        return
                    } else {
                        Sabotage.EmbersIsUse = true
                        Sabotage.EmbersWhois = p
                        Sabotage.EmbersProcessesValue[uuid] = 18
                        Sabotage.EmbersFailed[uuid] = false
                        Sabotage.EmbersSuccesses[uuid] = false
                        p.sendTitle("", "§c10 §f< §f" + Sabotage.EmbersProcessesValue[uuid] + " §f< §a23", 0, 2, 0)
                        object : BukkitRunnable() {
                            override fun run() {
                                val i: Int = Sabotage.EmbersProcessesValue[uuid]!!
                                if (Sabotage.EmbersSuccesses[uuid]!!) {
                                    cancel()
                                    return
                                }
                                if (!b.location.toCenterLocation().getNearbyPlayers(2.0)
                                        .contains(p) || i !in 11..22
                                ) {
                                    p.sendTitle("", "§cYou Failed..", 5, 40, 5)
                                    p.sendActionBar("§cYou Failed..")
                                    p.playSound(p.location, Sound.ENTITY_VILLAGER_NO, 1f, 1f)
                                    p.playSound(p.location, Sound.BLOCK_FIRE_EXTINGUISH, 0.5f, 1f)
                                    Sabotage.EmbersFailed[uuid] = true
                                    Sabotage.EmbersIsUse = false
                                    Sabotage.EmbersWhois = null
                                    Bukkit.getScheduler().runTaskLater(
                                        Succession.instance,
                                        Runnable { Sabotage.EmbersFailed[uuid] = false },
                                        (20 * 10).toLong()
                                    )
                                    cancel()
                                    return
                                }
                                //                                p.sendActionBar("§c10 §f< §e" + EmbersProcessesValue.get(uuid) + " §f< §a23");
                                p.sendTitle(
                                    "",
                                    "§c10 §f< §f" + Sabotage.EmbersProcessesValue[uuid] + " §f< §a23",
                                    0,
                                    2,
                                    0
                                )
                            }
                        }.runTaskTimer(Succession.instance, 0, 1)
                        object : BukkitRunnable() {
                            override fun run() {
                                if ((Sabotage.EmbersSuccesses[uuid]!!) or (Sabotage.EmbersFailed[uuid]!!)) {
                                    cancel()
                                    return
                                }
                                Sabotage.EmbersProcessesValue[uuid] = Sabotage.EmbersProcessesValue[uuid]!! - 1
                            }
                        }.runTaskTimer(Succession.instance, 1, 3)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            if (!Sabotage.EmbersFailed[p.uniqueId]!!) {
                                p.sendTitle("", "§aYou Successes!", 5, 40, 5)
                                p.sendActionBar("§aYou Successes!")
                                p.playSound(p.location, Sound.ENTITY_VILLAGER_YES, 1f, 1f)
                                p.playSound(p.location, Sound.BLOCK_FIRE_AMBIENT, 1f, 1f)
                                p.playSound(p.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                                Sabotage.EmbersIsUse = false
                                Sabotage.EmbersWhois = null
                                Sabotage.EmbersSuccesses[uuid] = true
                                b.blockData =
                                    Bukkit.createBlockData(b.blockData.asString.replace("lit=false", "lit=true"))
                            }
                        }, (20 * 8).toLong())
                    }
                }
            } else if (b.type == Material.PLAYER_HEAD) {
                if (!Sabotage.SabotagePlaying.containsKey(p.uniqueId) and !Sabotage.SabotagePlaying[p.uniqueId]!!) return
                val head: ItemStack =
                    util.getCustomSkull("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjYyNGM5MjdjZmVhMzEzNTU0Mjc5OTNkOGI3OTcxMmU4NmY5NGQ1OTUzNDMzZjg0ODg0OWEzOWE2ODc5In19fQ==")
                for (i in b.drops) {
                    for (profile in (i.itemMeta as SkullMeta).playerProfile!!
                        .properties) {
                        for (profile2 in (head.itemMeta as SkullMeta).playerProfile!!
                            .properties) {
                            if (profile2.value == profile.value) {
                                b.type = Material.AIR
                                val array: List<ItemStack> = ArrayList(Sabotage.SABOTAGE_ITEM_STACK_LIST())
                                array.shuffled()
                                b.world.dropItem(b.location.toCenterLocation(), array[0])
                                p.playSound(b.location.toCenterLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                                return
                            }
                        }
                    }
                }
            }
            return
        }
        if (e.item == null) return
        if (e.item!!.itemMeta == null) return
        if (e.item!!.type != Material.STRUCTURE_VOID) return
        if (e.item!!.itemMeta.displayName.contains("대기열")) {
            if (!Street1vs1.queueList.contains(p.name + ", " + Street1vs1.queueType[uuid])) {
                return
            }
            if ((e.action == Action.RIGHT_CLICK_AIR) or (e.action == Action.RIGHT_CLICK_BLOCK)) {
                e.isCancelled = true
                p.sendMessage("§c§l대기열 취소 §7시가전 대전 대기열에서 나갔습니다")
                StreetManager.Street1v1Clear(p)
                StreetManager.Street1v1HotbarRemove(p)
            }
        } else if (e.item!!.itemMeta.displayName.contains("채널")) {
            if (!Race.RaceImInChannel[uuid]!!) {
                return
            }
            if ((e.action == Action.RIGHT_CLICK_AIR) or (e.action == Action.RIGHT_CLICK_BLOCK)) {
                RaceManager.RaceHotbarRemove(p)
                RaceManager.RaceValueClear(p, Race.RaceRoomID[uuid])
                e.isCancelled = true
                p.sendMessage("§a§l경주 §7경주 채널에서 나갔습니다")
            }
        }
    }
}