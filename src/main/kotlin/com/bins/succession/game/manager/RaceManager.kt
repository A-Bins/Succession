package com.bins.succession.game.manager

import com.bins.succession.game.vars.race.Race
import com.bins.succession.info.data.User
import com.bins.succession.Succession
import net.minecraft.server.v1_16_R3.EntityPlayer
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityStatus
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

object RaceManager {
    fun RaceValueClear(me: Player, roomID: Int?) {
        Race.Hearts[me.uniqueId] = 3
        Race.RaceRoomID[me.uniqueId] = 0
        Race.RaceyouDisplayName.remove(me.uniqueId)
        Race.Raceyou.remove(me.uniqueId)
        Race.RacePlaying[me.uniqueId] = false
        Race.RaceImInChannel[me.uniqueId] = false
        Race.MyAreaLocation.remove(me.uniqueId)
        when (roomID) {
            1 -> Race.Room1.remove(me)
            2 -> Race.Room2.remove(me)
            3 -> Race.Room3.remove(me)
            4 -> Race.Room4.remove(me)
            5 -> Race.Room5.remove(me)
            6 -> Race.Room6.remove(me)
            7 -> Race.Room7.remove(me)
            8 -> Race.Room8.remove(me)
        }
    }

    fun RaceChannelClear(loser: Player, winner: Player, loserDisplayName: String, winnerDisplayName: String, roomID: Int
    ) {
        User.IsPlaying[loser.uniqueId] = false
        User.IsPlaying[winner.uniqueId] = false
        for (loc in Race.PlacedBlockLocation[loser.uniqueId]!!) {
            if (loc.block.type == Material.AIR) continue
            loc.block.type = Material.AIR
        }
        for (loc in Race.PlacedBlockLocation[winner.uniqueId]!!) {
            if (loc.block.type == Material.AIR) continue
            loc.block.type = Material.AIR
        }
        StreetManager.LoseWinCount(winner, loser)
        User.RaceWin[winner.uniqueId] = User.RaceBlockPlace[winner.uniqueId]!! + 1
        Bukkit.broadcastMessage("§a§l경주 §7$winnerDisplayName §7님이 $loserDisplayName§7님을 이기고 승리를 쟁취했습니다!")
        RaceValueClear(loser, Race.RaceRoomID[loser.uniqueId])
        RaceValueClear(winner, Race.RaceRoomID[winner.uniqueId])
        loser.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5))
        winner.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5))
        winner.health = 20.0
        val ep: EntityPlayer = (winner as CraftPlayer).handle
        val status = PacketPlayOutEntityStatus(ep, 35.toByte())
        ep.playerConnection.sendPacket(status)
        loser.inventory.clear()
        winner.inventory.clear()
        when (roomID) {
            1 -> {
                Race.Room1.clear()
                Race.Room1State = false
                Race.Room1SubState = false
            }
            2 -> {
                Race.Room2.clear()
                Race.Room2State = false
                Race.Room2SubState = false
            }
            3 -> {
                Race.Room3.clear()
                Race.Room3State = false
                Race.Room3SubState = false
            }
            4 -> {
                Race.Room4.clear()
                Race.Room4State = false
                Race.Room4SubState = false
            }
            5 -> {
                Race.Room5.clear()
                Race.Room5State = false
                Race.Room5SubState = false
            }
            6 -> {
                Race.Room6.clear()
                Race.Room6State = false
                Race.Room6SubState = false
            }
            7 -> {
                Race.Room7.clear()
                Race.Room7State = false
                Race.Room7SubState = false
            }
            8 -> {
                Race.Room8.clear()
                Race.Room8State = false
                Race.Room8SubState = false
            }
        }
    }

    fun RaceHotbar(p: Player) {
        val Exit = ItemStack(Material.STRUCTURE_VOID)
        val ExitMeta = Exit.itemMeta
        ExitMeta.setDisplayName("§c채널 퇴장")
        ExitMeta.lore = listOf("§7대기중인 채널에서 퇴장합니다.")
        Exit.setItemMeta(ExitMeta)
        p.inventory.setItem(3, Exit)
    }

    fun RaceHotbarRemove(p: Player) {
        val Exit = ItemStack(Material.STRUCTURE_VOID)
        val ExitMeta = Exit.itemMeta
        ExitMeta.setDisplayName("§c채널 퇴장")
        ExitMeta.lore = listOf("§7대기중인 채널에서 퇴장합니다.")
        Exit.setItemMeta(ExitMeta)
        p.inventory.remove(Exit)
    }

    fun RaceStart(value: String, me: Player, your: Player, roomID: Int) {
        Bukkit.broadcastMessage(me.name + " | " + your.name + " | " + value + " | " + roomID)
        User.IsPlaying[your.uniqueId] = true
        User.IsPlaying[me.uniqueId] = true
        when (value) {
            "수평" -> {
                User.RaceHorizontal[me.uniqueId] = User.RaceHorizontal[me.uniqueId]!! + 1
                User.RaceHorizontal[your.uniqueId] = User.RaceHorizontal[your.uniqueId]!! + 1
                when (roomID) {
                    1 -> {
                        Race.Room1State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(1)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 3
                        Race.Hearts[me.uniqueId] = 3
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §71번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수평 경주를 시작했습니다"
                        )
                        Race.Room1SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.RandomBlocks())
                            me.inventory.setItem(4, Race.RandomBlocks())
                            Race.Room1SubState = false
                            Race.Room1State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                    if (!Race.Room1State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                Bukkit.broadcastMessage("와! " + me.name + " 가 골인함!")
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    2 -> {
                        Race.Room2State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(2)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 3
                        Race.Hearts[me.uniqueId] = 3
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §72번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수평 경주를 시작했습니다"
                        )
                        Race.Room2SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.RandomBlocks())
                            me.inventory.setItem(4, Race.RandomBlocks())
                            Race.Room2SubState = false
                            Race.Room2State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room2State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    3 -> {
                        Race.Room3State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(3)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 3
                        Race.Hearts[me.uniqueId] = 3
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §73번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수평 경주를 시작했습니다"
                        )
                        Race.Room3SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.RandomBlocks())
                            me.inventory.setItem(4, Race.RandomBlocks())
                            Race.Room3SubState = false
                            Race.Room3State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room3State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    4 -> {
                        Race.Room4State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(4)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 3
                        Race.Hearts[me.uniqueId] = 3
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §74번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수평 경주를 시작했습니다"
                        )
                        Race.Room4SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.RandomBlocks())
                            me.inventory.setItem(4, Race.RandomBlocks())
                            Race.Room4SubState = false
                            Race.Room4State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room4State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                }
            }
            "수직" -> {
                User.RaceVertical[me.uniqueId] = User.RaceVertical[me.uniqueId]!! + 1
                User.RaceVertical[your.uniqueId] = User.RaceVertical[your.uniqueId]!! + 1
                when (roomID) {
                    5 -> {
                        Race.Room5State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(5)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 1
                        Race.Hearts[me.uniqueId] = 1
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §71번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수직 경주를 시작했습니다"
                        )
                        Race.Room5SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.VerticalRandomBlocks())
                            me.inventory.setItem(4, Race.VerticalRandomBlocks())
                            Race.Room5SubState = false
                            Race.Room5State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room5State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                Bukkit.broadcastMessage("와! " + me.name + " 가 골인함!")
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    6 -> {
                        Race.Room6State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(6)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 1
                        Race.Hearts[me.uniqueId] = 1
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §72번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수직 경주를 시작했습니다"
                        )
                        Race.Room6SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.VerticalRandomBlocks())
                            me.inventory.setItem(4, Race.VerticalRandomBlocks())
                            Race.Room6SubState = false
                            Race.Room6State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room6State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    7 -> {
                        Race.Room7State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(7)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 1
                        Race.Hearts[me.uniqueId] = 1
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §73번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수직 경주를 시작했습니다"
                        )
                        Race.Room7SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.VerticalRandomBlocks())
                            me.inventory.setItem(4, Race.VerticalRandomBlocks())
                            Race.Room7SubState = false
                            Race.Room7State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room7State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                    8 -> {
                        Race.Room8State = true
                        RaceHotbarRemove(me)
                        RaceHotbarRemove(your)
                        val array: ArrayList<Location> = Race().getRandomRaceLocation(8)!!
                        array.shuffle()
                        val youloc = array[0]
                        val meloc = array[1]
                        Race.Hearts[your.uniqueId] = 1
                        Race.Hearts[me.uniqueId] = 1
                        Race.RaceyouDisplayName[me.uniqueId] = your.displayName
                        Race.RaceyouDisplayName[your.uniqueId] = me.displayName
                        Race.Raceyou[me.uniqueId] = your
                        Race.Raceyou[your.uniqueId] = me
                        Race.RacePlaying[me.uniqueId] = true
                        Race.RacePlaying[your.uniqueId] = true
                        your.teleport(youloc)
                        Race.MyAreaLocation[your.uniqueId] = youloc
                        me.teleport(meloc)
                        Race.MyAreaLocation[me.uniqueId] = meloc
                        Bukkit.broadcastMessage(
                            "§a§l경주 §74번 채널에서 "
                                    + your.displayName +
                                    "§7님과 "
                                    + me.displayName +
                                    "§7님이 수직 경주를 시작했습니다"
                        )
                        Race.Room8SubState = true
                        me.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        your.sendTitle("§e§l5초후 게임이 시작됩니다..", "§7블록을 이어 목표 지점까지 먼저 도달하세요", 5, 30, 5)
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            var i = 0
                            while (i < 60) {
                                val Seconds = if (i == 0) 3 else if (i == 20) 2 else 1
                                val color = if (i == 0) "a" else if (i == 20) "e" else "c"
                                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                    your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 0f)
                                    your.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                    me.sendTitle("§$color§l$Seconds", "", 5, 10, 5)
                                }, i.toLong())
                                i += 20
                            }
                        }, (20 * 2).toLong())
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            your.sendTitle("", "§a§l시작!", 0, 10, 5)
                            me.sendTitle("", "§a§l시작!", 0, 10, 5)
                            your.inventory.setItem(4, Race.VerticalRandomBlocks())
                            me.inventory.setItem(4, Race.VerticalRandomBlocks())
                            Race.Room8SubState = false
                            Race.Room8State = true
                            your.playSound(your.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            me.playSound(me.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 2f, 1f)
                            val loclist: ArrayList<Location> =
                                Race().getRaceFinalLocation(Race.RaceRoomID[me.uniqueId]!!)!!
                            object : BukkitRunnable() {
                                override fun run() {
                                    if (!Race.Room8State) {
                                        cancel()
                                        me.sendActionBar("")
                                        your.sendActionBar("")
                                        return
                                    }
                                    for (loc in loclist) {
                                        for (p in loc.getNearbyPlayers(1.0, 0.2, 1.0)) {
                                            if (p.name == me.name) {
                                                RaceChannelClear(
                                                    your,
                                                    me,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceRoomID[me.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            } else if (p.name == your.name) {
                                                RaceChannelClear(
                                                    me,
                                                    your,
                                                    Race.RaceyouDisplayName[your.uniqueId]!!,
                                                    Race.RaceyouDisplayName[me.uniqueId]!!,
                                                    Race.RaceRoomID[your.uniqueId]!!
                                                )
                                                cancel()
                                                return
                                            }
                                        }
                                    }
                                    me.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[me.uniqueId])
                                    your.sendActionBar("§a§l남은 목숨 : §c" + Race.Hearts[your.uniqueId])
                                }
                            }.runTaskTimer(Succession.instance, 0, 1)
                        }, (20 * 5 + 5).toLong())
                    }
                }
            }
        }
    }
}