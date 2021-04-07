package com.bins.succession.event

import com.bins.succession.game.manager.RaceManager
import com.bins.succession.game.manager.StreetManager
import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.Server
import com.bins.succession.info.rank.Rank
import com.bins.succession.info.rank.Type
import com.bins.succession.Succession
import com.bins.succession.friends.Friend
import com.bins.succession.info.data.User
import net.md_5.bungee.api.ChatColor
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityMetadata
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntityLiving
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

class QuitAndJoin : Listener {
    @EventHandler
    fun Quit(e: PlayerQuitEvent) {
        e.quitMessage = ""
        val p = e.player
        if (Race.RaceImInChannel[p.uniqueId]!!) {
            if (Race.RacePlaying[p.uniqueId]!!) {
                RaceManager.RaceChannelClear(
                    p,
                    Race.Raceyou[p.uniqueId]!!,
                    Race.RaceyouDisplayName[Race.Raceyou[p.uniqueId]!!.uniqueId]!!,
                    Race.RaceyouDisplayName[p.uniqueId]!!,
                    Race.RaceRoomID[Race.Raceyou[p.uniqueId]!!.uniqueId]!!
                )
                return
            }
            RaceManager.RaceValueClear(p, Race.RaceRoomID[p.uniqueId])
            RaceManager.RaceHotbarRemove(p)
        } else if (StreetSuv.StreetSingleWarChannel1.contains(p)) {
            p.health = 0.0
        } else if (Street1vs1.playing[p.uniqueId]!!) {
            p.health = 0.0
        } else if (Street1vs1.queueList.contains(p.name + ", " + Street1vs1.queueType[p.uniqueId])) {
            p.sendMessage("§c§l대기열 취소 §7시가전 대전 대기열에서 나갔습니다")
            StreetManager.Street1v1Clear(p)
            StreetManager.Street1v1HotbarRemove(p)
        } else if (Sabotage.SabotageReadying[p.uniqueId]!!) {
            Sabotage.SabotagePlayers.remove(p)
            Sabotage.SabotageReadying[p.uniqueId] = false
        }
    }

    //ff3399 eb4799
    @EventHandler(priority = EventPriority.MONITOR)
    fun Join(e: PlayerJoinEvent) {
        e.joinMessage = ""
        val p = e.player

        p.scoreboard = Succession.showHealth
        p.health = p.health-0.00001
        Bukkit.getScheduler().scheduleSyncDelayedTask(Succession.instance, {
            p.health = p.health + 0.00001
        }, 1.toLong())


        run {
            StreetSuv.StreetSingleWarKillChannel1.putIfAbsent(p.uniqueId, 0)
            Street1vs1.queueType.putIfAbsent(p.uniqueId, "NONE")
            Street1vs1.playing.putIfAbsent(p.uniqueId, false)
            Street1vs1.youDisplayName.putIfAbsent(p.uniqueId, "NONE")
            Race.RaceyouDisplayName.putIfAbsent(p.uniqueId, "NONE")
            Street1vs1.youRoomID.putIfAbsent(p.uniqueId, 0)
            Race.RaceRoomID.putIfAbsent(p.uniqueId, 0)
            Race.Hearts.putIfAbsent(p.uniqueId, 0)
            User.WinCount.putIfAbsent(p.uniqueId, 0)
            User.LoseCount.putIfAbsent(p.uniqueId, 0)
            User.ChestAmount.putIfAbsent(p.uniqueId, 0)
            Sabotage.EmbersProcessesValue.putIfAbsent(p.uniqueId, 0)
            Race.RacePlaying.putIfAbsent(p.uniqueId, false)
            Race.RaceImInChannel.putIfAbsent(p.uniqueId, false)
            Sabotage.SabotageReadying.putIfAbsent(p.uniqueId, false)
            Sabotage.WoodIsBreaking.putIfAbsent(p.uniqueId, false)
            Sabotage.SabotagePlaying.putIfAbsent(p.uniqueId, false)
            User.IsClick.putIfAbsent(p.uniqueId, false)
            Sabotage.WoodComplete.putIfAbsent(p.uniqueId, false)
            Sabotage.EmbersFailed.putIfAbsent(p.uniqueId, false)
            Sabotage.EmbersSuccesses.putIfAbsent(p.uniqueId, false)
            Race.PlacedBlockLocation.putIfAbsent(p.uniqueId, ArrayList<Location>())
            Rank.Rank.putIfAbsent(p.uniqueId, Type.DEFAULT)
            Rank.Suffix.putIfAbsent(p.uniqueId, Type.NON)
            Sabotage.Items.putIfAbsent(p.uniqueId, object : HashMap<String, Int>() {
                init {
                    putIfAbsent("HardWood", 0)
                    putIfAbsent("Metal", 0)
                    putIfAbsent("Fuel", 0)
                    putIfAbsent("MW", 0)
                    putIfAbsent("EW", 0)
                }
            })
            User.IsPlaying.putIfAbsent(p.uniqueId, false)
            User.RaceBlockPlace.putIfAbsent(p.uniqueId, 0)
            User.RaceHorizontal.putIfAbsent(p.uniqueId, 0)
            User.RaceInterference.putIfAbsent(p.uniqueId, 0)
            User.RaceVertical.putIfAbsent(p.uniqueId, 0)
            User.RaceSnowBall.putIfAbsent(p.uniqueId, 0)
            User.RaceWin.putIfAbsent(p.uniqueId, 0)
            User.Street1V1Death.putIfAbsent(p.uniqueId, 0)
            User.Street1V1Kill.putIfAbsent(p.uniqueId, 0)
            User.StreetDamage.putIfAbsent(p.uniqueId, 0.0)
            User.StreetWarDeath.putIfAbsent(p.uniqueId, 0)
            User.StreetWarKill.putIfAbsent(p.uniqueId, 0)
            User.StreetTakeDamage.putIfAbsent(p.uniqueId, 0.0)
            User.StreetWarWin.putIfAbsent(p.uniqueId, 0)
            User.StreetOpenCount.putIfAbsent(p.uniqueId, 0)
            Friend.getHash().putIfAbsent(p.uniqueId, object : HashMap<String, LinkedList<String>>() {
                init {
                    put("list", LinkedList<String>())
                    put("ban", LinkedList<String>())
                    put("accept", LinkedList<String>())
                    put("await", LinkedList<String>())
                    put("date", LinkedList<String>())
                }
            })
        }
        for (armorStand in Server.HoloArmorStand) {
            val packet = PacketPlayOutSpawnEntityLiving(armorStand)
            val meta = PacketPlayOutEntityMetadata(armorStand.id, armorStand.dataWatcher, true)
            (p as CraftPlayer).handle.playerConnection.sendPacket(packet)
            p.handle.playerConnection.sendPacket(meta)
        }
        if ((Rank.Rank[p.uniqueId] == Type.MVPPlus) or (Rank.Rank[p.uniqueId] == Type.MVPPlusPlus)) {
            e.joinMessage = JoinMessage(p)
        }
        p.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5, 90F, 1F))
        p.setPlayerListHeaderFooter(
            """
                     ${ChatColor.of("#a99cc8")}Project ${ChatColor.of("#ed9dba")}Agora                     
                     """,
            """
                
                §f더 많은 정보를 위해 ${ChatColor.of("#e4d89e")}https://discord.gg/aNfF4P7 §f에 접속해보세요
                
                """.trimIndent()
        )
        Bukkit.getScheduler()
            .runTaskLater(Succession.instance, Runnable { p.setPlayerListName(Rank.getRankofPlayer(p)) }, 5)
    }

    private fun JoinMessage(p: Player): String {
        val original = "${ChatColor.of("#5fa8ff")}"+ ChatColor.stripColor(Rank.getRankofPlayer(p))
        val T = (Math.random() * (17 - 1 + 1) + 1).toInt() - 1
        val str = arrayOf(
            "님이 로비로 배달되었습니다",
            "님이 로비로 잘못 찾아왔습니다",
            "님이 로비로 피자를 들고왔습니다",
            "님이 로비로 멋지게 착지했습니다",
            "님이 기분전환을 위해 로비로 왔습니다",
            "님이 발을 헛디디다 로비로 굴러떨어졌습니다",
            "님이 미끄러져 로비로 왔습니다",
            "님이 먹을 것을 찾아 로비로 왔습니다",
            "님이 로비에서 깨어났습니다",
            "님이 눈을 떠보니 로비였습니다",
            "님이 잘못된 포탈을 타고 로비로 왔습니다",
            "님이 파티장소를 착각해 로비로 왔습니다",
            "님이 모험을 위해 로비로 왔습니다",
            "님이 산책을 하다 로비로 왔습니다",
            "님이 공을 차며 로비로 찾아왔습니다",
            "님이 우주선을 타고 로비로 찾아왔습니다",
            "${ChatColor.of("#c7e8ff")}야생의 " + original + ChatColor.of("#c7e8ff") + "님이 로비로 나타났습니다"
        )
        return if (str[T].contains("야생")) {
            (ChatColor.of("#38bdff")
                .toString() + ">" + ChatColor.of("#89e4ff") + ">" + ChatColor.of("#38bdff") + "> "
                    + str[T]
                    + ChatColor.of("#38bdff") + " <" + ChatColor.of("#89e4ff") + "<" + ChatColor.of(
                "#38bdff"
            ) + "<")
        } else  "${ChatColor.of("#38bdff")}>${ChatColor.of("#89e4ff")}>" +
                "${ChatColor.of("#38bdff")}> $original${ChatColor.of("#c7e8ff")}${str[T]}${ChatColor.of("#38bdff")}" +
                " <${ChatColor.of("#89e4ff")}<${ChatColor.of("#38bdff")}"
    }
}

