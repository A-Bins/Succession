package com.bins.succession

import com.bins.succession.Brand.Brand
import com.bins.succession.event.*
import com.bins.succession.event.Block
import com.bins.succession.friends.Friend
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.Server
import com.bins.succession.info.data.User
import com.bins.succession.info.rank.Rank
import com.bins.succession.packet.Advance
import com.bins.succession.packet.EntityHider
import com.bins.succession.cmd.command.*
import com.bins.succession.cmd.commandTabComplete.FriendTabComplete
import com.bins.succession.cmd.commandTabComplete.NameTab
import com.bins.succession.cmd.commandTabComplete.SettingsTab
import com.bins.succession.cmd.commandTabComplete.loreTab
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.race.Race
import com.bins.succession.info.rank.Type
import com.bins.succession.utilities.util
import com.bins.succession.utilities.utilJson
import net.md_5.bungee.api.ChatColor
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scoreboard.DisplaySlot
import org.bukkit.scoreboard.RenderType
import org.bukkit.scoreboard.Scoreboard
import java.io.File
import java.io.IOException
import java.util.*

class Succession : JavaPlugin() {
    fun makeFile(f: File) {
        if (!f.exists() || !f.isFile) {
            try {
                f.createNewFile()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onEnable() {
        Bukkit.getScheduler().runTaskLater(this, Runnable { b = true }, 5)
        Advance().removeAdvanceMaps(this)
        entityHider = EntityHider(this, EntityHider.Policy.BLACKLIST)
        instance = this
        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN.toString() + "Succession Plugin Activated by A_bins.")
        dataFolder.mkdir()
        server.pluginManager.registerEvents(NPCRightClick(), this)
        server.pluginManager.registerEvents(InventoryClick(), this)
        server.pluginManager.registerEvents(QuitAndJoin(), this)
        server.pluginManager.registerEvents(Death(), this)
        server.pluginManager.registerEvents(AsyncChat(), this)
        server.pluginManager.registerEvents(Block(), this)
        server.pluginManager.registerEvents(Move(), this)
        server.pluginManager.registerEvents(Swap(), this)
        server.pluginManager.registerEvents(Interact(), this)
        server.pluginManager.registerEvents(GamemodeChange(), this)
        server.pluginManager.registerEvents(RegionLeave(), this)
        server.pluginManager.registerEvents(Pickup(), this)
        utilJson.loadFriend(this, Friend.getHash())
        utilJson.loadArrayLocationString(this, StreetSuv.DropItemLocation, "DropItemLocation")
        utilJson.loadRank(this, Rank.Suffix, "Suffixw")
        utilJson.loadRank(this, Rank.Rank, "Rank")
        utilJson.loadInt(this, User.WinCount, "WinCount")
        utilJson.loadInt(this, User.LoseCount, "LoseCount")
        utilJson.loadInt(this, User.ChestAmount, "ChestAmount")
        utilJson.loadStrInt(this, Server.ReloadAmount, "ReloadAmount")
        utilJson.loadString(this, Server.HoloArmorStandStr, "HoloArmorStandStr")
        utilJson.loadloc(this, Server.HoloArmorStandLoc, "HoloArmorStandLoc")
        utilJson.loadUUID(this, Server.HoloArmorStandId, "HoloArmorStandId")
        utilJson.loadInt(this, User.RaceBlockPlace, "RaceBlockPlace")
        utilJson.loadInt(this, User.RaceHorizontal, "RaceHorizontal")
        utilJson.loadInt(this, User.RaceInterference, "RaceInterference")
        utilJson.loadInt(this, User.RaceVertical, "RaceVertical")
        utilJson.loadInt(this, User.RaceSnowBall, "RaceSnowBall")
        utilJson.loadInt(this, User.RaceWin, "RaceWin")
        utilJson.loadInt(this, User.Street1V1Death, "Street1V1Death")
        utilJson.loadInt(this, User.Street1V1Kill, "Street1V1Kill")
        utilJson.loadDouble(this, User.StreetDamage, "StreetDamage")
        utilJson.loadDouble(this, User.StreetTakeDamage, "StreetTakeDamage")
        utilJson.loadInt(this, User.StreetWarDeath, "StreetWarDeath")
        utilJson.loadInt(this, User.StreetWarKill, "StreetWarKill")
        utilJson.loadInt(this, User.StreetWarWin, "StreetWarWin")
        utilJson.loadInt(this, User.StreetOpenCount, "StreetOpenCount")
        getCommand("lobby")?.setExecutor(lobby())
        getCommand("lore")?.setExecutor(lore())
        getCommand("lore")?.tabCompleter = loreTab()
        getCommand("name")?.setExecutor(Name())
        getCommand("name")?.tabCompleter = NameTab()
        getCommand("Test")?.setExecutor(Test())
        getCommand("st")?.setExecutor(Settings())
        getCommand("st")?.tabCompleter = SettingsTab()
        getCommand("친구")?.setExecutor(Friends())
        getCommand("친구")?.tabCompleter = FriendTabComplete()
        getCommand("drop")?.setExecutor(DropLocation())
        Bukkit.getMessenger().registerOutgoingPluginChannel(this, "minecraft:brand")
        Bukkit.getMessenger().registerIncomingPluginChannel(this, "minecraft:brand", Brand())
        util.Scoreboard()
        Bukkit.getScheduler().runTaskTimer(this, Runnable {
            utilJson.saveFriend(this, Friend.getHash())
            utilJson.save(this, Rank.Suffix, "Suffix")
            utilJson.save(this, Rank.Rank, "Rank")
            utilJson.save(this, User.WinCount, "WinCount")
            utilJson.save(this, User.LoseCount, "LoseCount")
            utilJson.save(this, User.ChestAmount, "ChestAmount")
            utilJson.save(this, Server.ReloadAmount, "ReloadAmount")
            utilJson.save(this, Server.HoloArmorStandStr, "HoloArmorStandStr")
            utilJson.save(this, Server.HoloArmorStandLoc, "HoloArmorStandLoc")
            utilJson.saveArray(this, Server.HoloArmorStandId, "HoloArmorStandId")
            utilJson.save(this, User.RaceBlockPlace, "RaceBlockPlace")
            utilJson.save(this, User.RaceHorizontal, "RaceHorizontal")
            utilJson.save(this, User.RaceInterference, "RaceInterference")
            utilJson.save(this, User.RaceVertical, "RaceVertical")
            utilJson.save(this, User.RaceSnowBall, "RaceSnowBall")
            utilJson.save(this, User.RaceWin, "RaceWin")
            utilJson.save(this, User.Street1V1Death, "Street1V1Death")
            utilJson.save(this, User.Street1V1Kill, "Street1V1Kill")
            utilJson.save(this, User.StreetDamage, "StreetDamage")
            utilJson.save(this, User.StreetWarDeath, "StreetWarDeath")
            utilJson.save(this, User.StreetWarKill, "StreetWarKill")
            utilJson.save(this, User.StreetWarWin, "StreetWarWin")
            utilJson.save(this, User.StreetTakeDamage, "StreetTakeDamage")
            utilJson.save(this, User.StreetOpenCount, "StreetOpenCount")
        }, 0, (20 * 10).toLong())
        Bukkit.getScheduler().runTaskLater(this, Runnable {
            val a2: ArrayList<UUID> = ArrayList<UUID>()
            Server.HoloArmorStand.clear()
            for (uuid in Server.HoloArmorStandId) {
                val loc = Server.HoloArmorStandLoc[uuid]
                val str = Server.HoloArmorStandStr[uuid]
                val s: WorldServer = (loc!!.world as CraftWorld).handle
                val armorStand = EntityArmorStand(s, loc.x, loc.y, loc.z)
                armorStand.customNameVisible = true
                armorStand.customName = ChatComponentText(str)
                armorStand.isNoGravity = true
                armorStand.isInvisible = true
                armorStand.isMarker = true
                val packet = PacketPlayOutSpawnEntityLiving(armorStand)
                val meta = PacketPlayOutEntityMetadata(armorStand.id, armorStand.dataWatcher, true)
                for (a in Bukkit.getOnlinePlayers()) {
                    (a as CraftPlayer).handle.playerConnection.sendPacket(packet)
                    a.handle.playerConnection.sendPacket(meta)
                }
                Server.HoloArmorStand.add(armorStand)
                a2.add(armorStand.bukkitEntity.uniqueId)
                Server.HoloArmorStandLoc[armorStand.bukkitEntity.uniqueId] =
                    armorStand.bukkitEntity.location
                Server.HoloArmorStandStr[armorStand.bukkitEntity.uniqueId] =
                    armorStand.customName?.text.toString()
                Server.HoloArmorStandLoc.remove(uuid)
                Server.HoloArmorStandStr.remove(uuid)
            }
            Server.HoloArmorStandId.clear()
            Server.HoloArmorStandId.addAll(a2)
            Bukkit.getScheduler().runTaskTimerAsynchronously(this, Runnable {
                for (e in Server.HoloArmorStand) {
                    val uuid: UUID = e.uniqueID
                    val str = Server.HoloArmorStandStr[uuid]
                    if (str!!.contains("소지중인")) {
                        for (a in Bukkit.getOnlinePlayers()) {
                            e.customName = (ChatComponentText("§7소지중인 전리품 상자: §a" + User.ChestAmount[a.uniqueId] + "개"))
                            val meta = PacketPlayOutEntityMetadata(e.id, e.dataWatcher, true)
                            (a as CraftPlayer).handle.playerConnection.sendPacket(meta)
                        }
                    }
                }
            }, 5, 5)
        }, 20)
        Server.ReloadAmount.putIfAbsent("Server", 1)
        Server.ReloadAmount["Server"] = Server.ReloadAmount["Server"]!! + 1
        Bukkit.getScheduler().runTaskLater(this, Runnable { Bukkit.broadcastMessage(Server.ReloadAmount["Server"].toString() + "") }, 20)
        for(p in Bukkit.getOnlinePlayers()){

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
    }

    override fun onDisable() {
        for (e in Server.HoloArmorStand) {
            for (p in Bukkit.getOnlinePlayers()) {
                val packet = PacketPlayOutEntityDestroy(e.id)
                (p as CraftPlayer).handle.playerConnection.sendPacket(packet)
            }
        }
    }

    companion object {
        lateinit var instance: Succession
        val showHealth : Scoreboard = Bukkit.getScoreboardManager().mainScoreboard
        init {
            Bukkit.getScoreboardManager().mainScoreboard.getObjective("showhealth")?.unregister()
            val healthObj = this.showHealth.registerNewObjective(
                "showhealth",
                "health",
                StringBuilder().append(ChatColor.RED).append('\u2764').toString(),
                RenderType.HEARTS
            )
            healthObj.displaySlot = DisplaySlot.BELOW_NAME
        }
        lateinit var entityHider: EntityHider
        var b = false
    }
}