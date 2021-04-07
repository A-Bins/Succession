package com.bins.succession.utilities

import com.bins.succession.Succession
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import com.bins.succession.info.rank.Rank
import com.bins.succession.info.rank.Type
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import com.comphenix.protocol.wrappers.WrappedDataWatcher
import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import net.md_5.bungee.api.ChatColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.scoreboard.*
import java.lang.reflect.InvocationTargetException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.experimental.and
import kotlin.experimental.or


object util {
    fun bb(str: Any) {
        Bukkit.broadcastMessage(str.toString() + "")
    }

    fun getCustomSkull(url: String): ItemStack {
        val head = ItemStack(Material.PLAYER_HEAD)
        if (url.isEmpty()) return head
        val skullMeta = head.itemMeta as SkullMeta
        val profile = GameProfile(UUID.randomUUID(), null)
        profile.properties.put("textures", Property("textures", url))
        try {
            val mtd = skullMeta.javaClass.getDeclaredMethod("setProfile", GameProfile::class.java)
            mtd.isAccessible = true
            mtd.invoke(skullMeta, profile)
        } catch (ex: IllegalAccessException) {
            ex.printStackTrace()
        } catch (ex: InvocationTargetException) {
            ex.printStackTrace()
        } catch (ex: NoSuchMethodException) {
            ex.printStackTrace()
        }
        head.itemMeta = skullMeta
        return head
    }

    fun SecondPerRegular(seconds: Int): String {
        return if (seconds % 60 != 0) {
            val i = seconds / 60
            val subtract = i * 60
            val second = seconds - subtract
            if (second.toString().length == 1) {
                "$i:0$second"
            } else "$i:$second"
        } else {
            val minute = (seconds / 60).toString()
            val second = ":00"
            minute + second
        }
    }

    fun Scoreboard() {
        //스코어보드
        Bukkit.getScheduler().runTaskTimer(Succession.instance, Runnable {
            for (p in Bukkit.getOnlinePlayers()) {
                val uuid: UUID = p.uniqueId
                if (Sabotage.SabotagePlaying.containsKey(uuid) && Sabotage.SabotagePlaying[uuid]!!) {
                    p.scoreboard = Bukkit.getScoreboardManager().newScoreboard
                    continue
                }
                val now = Date()
                val format = SimpleDateFormat("yy/MM/dd")
                val board: Scoreboard = Bukkit.getScoreboardManager().newScoreboard
                if (StreetSuv.StreetSingleWarChannel1.contains(p)) {
                    val street = board.registerNewObjective("Street", "Street", "  §6§l시가전  ")
                    street.displaySlot = DisplaySlot.SIDEBAR
                    if (StreetSuv.StreetSingleWarStateChannel1) {
                        val score = street.getScore("§7" + format.format(now))
                        score.score = 8
                        val empty = street.getScore("플레이어: §a" + StreetSuv.StreetSingleWarChannel1.size + "/8")
                        empty.score = 7
                        val score1 = street.getScore("")
                        score1.score = 6
                        val score2 = street.getScore(
                            if (StreetSuv.StreetSingleWarSupplyChannel1 != 0) "§a" + SecondPerRegular(StreetSuv.StreetSingleWarSupplyChannel1) + " 후 보급 I " else if (StreetSuv.StreetSingleWarGlowChannel1 != 0) "§a" + SecondPerRegular(
                                StreetSuv.StreetSingleWarGlowChannel1
                            ) + " 후 전체 발광 효과 지급 " else "§a남은 이벤트가 없습니다."
                        )
                        score2.score = 5
                        val score3 = street.getScore("  ")
                        score3.score = 4
                        val empty1 = street.getScore("§f킬: §a" + StreetSuv.StreetSingleWarKillChannel1[p.uniqueId] + "         ")
                        empty1.score = 3
                        val score4 = street.getScore("     ")
                        score4.score = 2
                        val score5 = street.getScore("§6Agora.kro.kr       ")
                        score5.score = 1
                    } else {
                        val score = street.getScore("§7" + format.format(now))
                        score.score = 8
                        val empty = street.getScore("플레이어: §a" + StreetSuv.StreetSingleWarChannel1.size + "/8")
                        empty.score = 7
                        val score1 = street.getScore("")
                        score1.score = 6
                        val score2 =
                            street.getScore(if (StreetSuv.StreetSingleWarSubStateChannel1) "§a" + StreetSuv.StreetSingleWarTimerChannel1 + "§f초 후 시작" else "§6게임 대기중")
                        score2.score = 5
                        val score3 = street.getScore("  ")
                        score3.score = 4
                        val empty1 = street.getScore("§f모드: §a서바이벌 개인전")
                        empty1.score = 3
                        val score4 = street.getScore("     ")
                        score4.score = 2
                        val score5 = street.getScore("§6Agora.kro.kr")
                        score5.score = 1
                    }
                }
                else if (Sabotage.SabotageReadying.containsKey(p.uniqueId) && Sabotage.SabotageReadying[p.uniqueId]!!) {
                    val MainTitle = "${ChatColor.of("#a9f1f5")}§l사보추어"
                    val sabotage = board.registerNewObjective("Sabotage", "Sabotage", MainTitle)
                    sabotage.displaySlot = DisplaySlot.SIDEBAR
                    val score1 = sabotage.getScore("§7" + format.format(now))
                    score1.score = 7
                    val score1_1 = sabotage.getScore(" ")
                    score1_1.score = 6
                    val score2 = sabotage.getScore("플레이어: §a" + Sabotage.SabotagePlayers.size + "         ")
                    score2.score = 5
                    val score3 = sabotage.getScore("  ")
                    score3.score = 4
                    val score4 = sabotage.getScore("§6게임 대기중")
                    score4.score = 3
                    val score5 = sabotage.getScore("   ")
                    score5.score = 2
                    val score = sabotage.getScore("§eAgora.kro.kr")
                    score.score = 1
                }
                else {
                    val MainTitle = "${ChatColor.of("#ed9dba")}§lAgora"
                    val agora = board.registerNewObjective("Agora", "Agora", MainTitle)
                    agora.displaySlot = DisplaySlot.SIDEBAR
                    val score1 = agora.getScore("§7" + format.format(now))
                    score1.score = 14
                    val score1_1 = agora.getScore("")
                    score1_1.score = 13
                    //                    ChatColor color = ChatColor.of("#"+ Rank.get(p.getUniqueId()).getName());
                    val PR: Type = Rank.Rank[p.uniqueId]!!
                    val Ranks =
                        if (PR == Type.DEFAULT) "§7기본" else if (PR == Type.VIP) "§aVIP" else if (PR == Type.VIPPlus) "§aVIP+" else if (PR == Type.MVP) "§bMVP" else if (PR == Type.MVPPlus) "§3MVP+" else if (PR == Type.MVPPlusPlus) "§9MVP++" else "모르겠어..너뭐얌.."
                    val score2 = agora.getScore("등급: $Ranks")
                    score2.score = 12
                    val score3 = agora.getScore("도전과제: §a0")
                    score3.score = 11
                    val score3_1 = agora.getScore("총 우승: §a" + User.WinCount[uuid])
                    score3_1.score = 10
                    val score3_2 = agora.getScore(" ")
                    score3_2.score = 9
                    val score4 = agora.getScore("전리품 상자: §e" + User.ChestAmount[uuid])
                    score4.score = 8
                    val score5 = agora.getScore("상자 열쇠: §e0")
                    score5.score = 7
                    val score6 = agora.getScore("  ")
                    score6.score = 6
                    val score7 = agora.getScore("소지금: §60")
                    score7.score = 5
                    val score7_1 = agora.getScore("   ")
                    score7_1.score = 4
                    val score8 = agora.getScore("플레이어: §a" + Bukkit.getOnlinePlayers().size + "         ")
                    score8.score = 3
                    val score9 = agora.getScore("    ")
                    score9.score = 2
                    val score = agora.getScore("§eAgora.kro.kr")
                    score.score = 1
                }
                p.scoreboard = board
            }
        }, 0, 20)
    }

    fun setGlow(p: Player, e: Entity, glow: Boolean) {
        val pm: ProtocolManager = ProtocolLibrary.getProtocolManager()
        val watcher = WrappedDataWatcher()
        val serializer: WrappedDataWatcher.Serializer = WrappedDataWatcher.Registry.get(Byte::class.java)
        watcher.entity = e
        if (glow) {
            if (watcher.getObject(0) == null) watcher.setObject(0, serializer, 0x40.toByte()) else watcher.setObject(
                0,
                serializer,
                watcher.getObject(0) as Byte or (1.inv() shl 6)
            )
        } else {
            if (watcher.getObject(0) == null) watcher.setObject(0, serializer, 0.toByte()) else watcher.setObject(
                0,
                serializer,
                watcher.getObject(0) as Byte and (1.inv() shl 6)
            )
        }
        val packet: PacketContainer = pm.createPacket(PacketType.Play.Server.ENTITY_METADATA)
        packet.integers.write(0, e.entityId)
        packet.watchableCollectionModifier.write(0, watcher.watchableObjects)
        try {
            pm.sendServerPacket(p, packet)
        } catch (i: InvocationTargetException) {
            i.printStackTrace()
        }
    }
}