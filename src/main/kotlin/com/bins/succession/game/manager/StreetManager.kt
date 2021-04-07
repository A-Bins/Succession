package com.bins.succession.game.manager

import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import com.bins.succession.Succession
import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.ProtocolManager
import com.comphenix.protocol.events.PacketContainer
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Chicken
import org.bukkit.entity.Player
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.Vector
import java.lang.reflect.InvocationTargetException
import java.util.ArrayList

object StreetManager {
    fun StreetSingleWarReset() {
        StreetSuv.StreetSingleWarChannel1.clear()
        StreetSuv.StreetSingleWarChannel2.clear()
        for (i in StreetSuv.DropItems) {
            i.remove()
        }
        StreetSuv.StreetSingleWarInvincibility = false
        StreetSuv.StreetSingleWarStateChannel1 = false
        StreetSuv.StreetSingleWarStateChannel2 = false
        StreetSuv.StreetSingleWarTimerChannel1 = 20
        StreetSuv.StreetSingleWarSupplyChannel1 = 120
        StreetSuv.StreetSingleWarGlowChannel1 = 60
        StreetSuv.StreetSingleWarSubStateChannel1 = false
        StreetSuv.StreetSingleWarSubStateChannel2 = false
        StreetSuv.StreetSingleWarKillChannel1.clear()
        StreetSuv.StreetSingleWarSupplyStateChannel1 = false
    }

    private var time: Long = 20
    fun StreetSingleWarStart(str: String) {
        if (if (str == "1") StreetSuv.StreetSingleWarSubStateChannel1 else StreetSuv.StreetSingleWarSubStateChannel2) {
            return
        }
        if (str == "1") {
            StreetSuv.StreetSingleWarSubStateChannel1 = true
            Bukkit.broadcastMessage("§7< §a1채널 §6서바이벌 개인전 §7> §720초후 §a게임 시작")
        } else if (str == "2") {
            StreetSuv.StreetSingleWarSubStateChannel2 = true
            Bukkit.broadcastMessage("§7< §c2채널 §6서바이벌 개인전 §7> §720초후 §a게임 시작")
        }
        object : BukkitRunnable() {
            override fun run() {
                if (str == "1") {
                    if (StreetSuv.StreetSingleWarChannel1.size < 4) {
                        StreetSuv.StreetSingleWarTimerChannel1 = 20
                        StreetSuv.StreetSingleWarSubStateChannel1 = false
                        return
                    }
                    StreetSuv.StreetSingleWarSubStateChannel1 = true
                    Bukkit.broadcastMessage("§7< §a1채널 §6서바이벌 개인전 §7> §710초후 §a게임 시작")
                } else if (str == "2") {
                    StreetSuv.StreetSingleWarSubStateChannel2 = true
                    Bukkit.broadcastMessage("§7< §c2채널 §6서바이벌 개인전 §7> §710초후 §a게임 시작")
                }
            }
        }.runTaskLater(Succession.instance, (20 * 11).toLong())
        object : BukkitRunnable() {
            override fun run() {
                if (StreetSuv.StreetSingleWarChannel1.size < 4) {
                    StreetSuv.StreetSingleWarTimerChannel1 = 20
                    StreetSuv.StreetSingleWarSubStateChannel1 = false
                    return
                }
                if (str == "1") {
                    StreetSuv.StreetSingleWarSubStateChannel1 = true
                    Bukkit.broadcastMessage("§7< §a1채널 §6서바이벌 개인전 §7> §75초후 §a게임 시작")
                } else if (str == "2") {
                    StreetSuv.StreetSingleWarSubStateChannel2 = true
                    Bukkit.broadcastMessage("§7< §c2채널 §6서바이벌 개인전 §7> §75초후 §a게임 시작")
                }
            }
        }.runTaskLater(Succession.instance, (20 * 16).toLong())
        time = 20
        for (i in 20 downTo 0) {
            if (StreetSuv.StreetSingleWarChannel1.size < 4) {
                StreetSuv.StreetSingleWarTimerChannel1 = 20
                StreetSuv.StreetSingleWarSubStateChannel1 = false
                break
            }
            object : BukkitRunnable() {
                override fun run() {
                    for (p in if (str == "1") StreetSuv.StreetSingleWarChannel1 else StreetSuv.StreetSingleWarChannel2) {
                        if (StreetSuv.StreetSingleWarChannel1.size < 4) {
                            StreetSuv.StreetSingleWarTimerChannel1 = 20
                            StreetSuv.StreetSingleWarSubStateChannel1 = false
                            break
                        }
                        if (i <= 4) {
                            p.sendMessage("§7< §" + (if (str == "1") "a1" else "c2") + "채널 §6서바이벌 개인전 §7> §a게임 시작 §7카운트 다운 §l" + i + "§7초")
                        }
                        StreetSuv.StreetSingleWarTimerChannel1 = i
                    }
                }
            }.runTaskLater(Succession.instance, time)
            time += 20
        }
        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
            if (StreetSuv.StreetSingleWarChannel1.size < 4) {
                StreetSuv.StreetSingleWarTimerChannel1 = 20
                StreetSuv.StreetSingleWarSubStateChannel1 = false
                return@Runnable
            }
            if (str == "1") {
                StreetSuv.StreetSingleWarStateChannel1 = true
            } else if (str == "2") {
                StreetSuv.StreetSingleWarStateChannel2 = true
            }
            for (p in StreetSuv.StreetSingleWarChannel1) {
                p.sendMessage("§6§l무적! §7모든 생존자에게 1분간 데미지 면역을 부여합니다!")
            }
            StreetSuv.StreetSingleWarInvincibility = true
            val sub: ArrayList<Location> = StreetSuv.StreetSingleWarSpawnLocationChannel1
            for (p in if (str == "1") StreetSuv.StreetSingleWarChannel1 else StreetSuv.StreetSingleWarChannel2) {
                p.gameMode = GameMode.SURVIVAL
                Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                    p.teleport(sub[0])
                    Parachute(p)
                }, 1)
                val RandomChest = ItemStack(Material.CHEST)
                val RandomChestMeta = RandomChest.itemMeta
                RandomChestMeta.setDisplayName("§a무작위 무기 상자")
                RandomChestMeta.lore = listOf("§7무작위 무기를 하나 획득합니다")
                RandomChest.itemMeta = RandomChestMeta
                p.inventory.addItem(RandomChest)
                p.inventory.addItem(ItemStack(Material.COOKED_BEEF, 10))
                p.inventory.addItem(ItemStack(Material.STONE_AXE))
            }
            var Items: ArrayList<ItemStack> = ArrayList<ItemStack>(StreetSuv.Items)
            for (a in StreetSuv.DropItemLocation) {
                if (Items.size == 0) {
                    Items = ArrayList<ItemStack>(StreetSuv.Items)
                }
                val world = a.split(",").toTypedArray()[0].replace(" ".toRegex(), "")
                val x = a.split(",").toTypedArray()[1].replace(" ".toRegex(), "").toDouble()
                val y = a.split(",").toTypedArray()[2].replace(" ".toRegex(), "").toDouble() + 1
                val z = a.split(",").toTypedArray()[3].replace(" ".toRegex(), "").toDouble()
                val worlds = Bukkit.getWorld(world)
                val loc = Location(worlds, x, y, z)
                Items.shuffle()
                val item = Items[0]
                val entity = worlds!!.dropItem(loc, item)
                StreetSuv.DropItems.add(entity)
                entity.fallDistance = 0f
                entity.setGravity(false)
                entity.customName = "Drop"
                entity.velocity = Vector(0, 0, 0)
                entity.isGlowing = true
                Items.remove(item)
            }
            object : BukkitRunnable() {
                override fun run() {
                    if (StreetSuv.StreetSingleWarStateChannel1) {
                        if (StreetSuv.StreetSingleWarSupplyChannel1 == 0) {
                            if (StreetSuv.StreetSingleWarGlowChannel1 == 0) {
                                for (p in StreetSuv.StreetSingleWarChannel1) {
                                    p.isGlowing = true
                                    p.sendMessage("§e§l발광! §7모든 생존자에게 발광 효과를 지급합니다!")
                                }
                                cancel()
                            } else {
                                if (!StreetSuv.StreetSingleWarSupplyStateChannel1) {
                                    val RandomChest = ItemStack(Material.CHEST)
                                    val RandomChestMeta = RandomChest.itemMeta
                                    RandomChestMeta.setDisplayName("§a무작위 무기 상자")
                                    RandomChestMeta.lore = listOf("§7무작위 무기를 하나 획득합니다")
                                    RandomChest.setItemMeta(RandomChestMeta)
                                    for (p in StreetSuv.StreetSingleWarChannel1) {
                                        p.inventory.addItem(RandomChest)
                                        p.sendMessage("§a§l보급! §7모든 생존자에게 추가 무작위 무기 상자를 지급합니다!")
                                    }
                                    StreetSuv.StreetSingleWarSupplyStateChannel1 = true
                                }
                                StreetSuv.StreetSingleWarGlowChannel1 -= 1
                            }
                        } else StreetSuv.StreetSingleWarSupplyChannel1 -= 1
                    } else cancel()
                }
            }.runTaskTimer(Succession.instance, 20, 20)
            Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                StreetSuv.StreetSingleWarInvincibility = false
                for (p in StreetSuv.StreetSingleWarChannel1) {
                    p.sendMessage("§6§l무적! §7데미지 면역이 풀렸습니다!")
                }
            }, (20 * 60).toLong())
            Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                if (StreetSuv.StreetSingleWarStateChannel1) {
                    val a: String = StreetSuv.StreetSingleWarChannel1.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace("CraftPlayer{name=", "")
                        .replace("}", "")
                    Bukkit.broadcastMessage("§6§l시가전 서바이벌 대전 종료! §7살아남은 " + a + "님 축하드립니다")
                    for (p in StreetSuv.StreetSingleWarChannel1) {
                        PlayerStreetSingleWarReset(p, "TimeOut")
                    }
                    StreetSingleWarReset()
                }
            }, (20 * 60 * 5).toLong())
        }, (20 * 21).toLong())
    }

    fun PlayerStreetSingleWarReset(p: Player, reason: String) {
        if (!StreetSuv.StreetSingleWarChannel1.contains(p) and !StreetSuv.StreetSingleWarChannel2.contains(p)) {
            return
        }
        p.inventory.clear()
        p.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5, 90F, 1F))
        p.scoreboard = Bukkit.getScoreboardManager().newScoreboard
        StreetSuv.Boolean[p.uniqueId] = false
        p.isGlowing = false
        if (reason != "TimeOut") {
            Bukkit.broadcastMessage(
                if (reason == "Death") "§c§l탈락! §7" + p.name + "님이 시가전에서 탈락하셨습니다! (" + (if (StreetSuv.StreetSingleWarChannel1.contains(
                        p
                    )
                ) StreetSuv.StreetSingleWarChannel1.size - 1 else StreetSuv.StreetSingleWarChannel2.size - 1) + "/8)" else if (reason == "Victory") "§7< §" + (if (StreetSuv.StreetSingleWarChannel1.contains(
                        p
                    )
                ) "a1" else "c2") + "채널 §6서바이벌 개인전 §7> " + p.name + "님이 최후의 생존자로 승리했습니다!" else "§7< §" +
                        (if (StreetSuv.StreetSingleWarChannel1.contains(p)) "a1" else "c2") +
                        "채널 §6서바이벌 개인전 §7> §7" + p.name + "§f님이 §c퇴장§7했습니다 §7(" + (if (StreetSuv.StreetSingleWarChannel1.contains(
                        p
                    )
                ) StreetSuv.StreetSingleWarChannel1.size - 1 else StreetSuv.StreetSingleWarChannel2.size - 1) + "/8)"
            )
        }
        (if (StreetSuv.StreetSingleWarChannel1.contains(p)) StreetSuv.StreetSingleWarChannel1 else StreetSuv.StreetSingleWarChannel2).remove(
            p
        )
    }

    fun Parachute(p: Player): Chicken {
        val chicken = p.world.spawn(p.location, Chicken::class.java)

        //치킨의 파신저에 플레이어를 앉힌다
        chicken.addPassenger(p)


        //Stop Boolean에 false값을 넣어둔다
        StreetSuv.Boolean[p.uniqueId] = false
        // 1/20초 마다 실행시키는 스케듈러를 만든다
        object : BukkitRunnable() {
            override fun run() {
                // 플레이어가 바라보고 있는 방향의 0.65칸 만큼 앞의 벡터를 구한다
                val vector = p.location.direction.multiply(0.6)
                vector.y = chicken.location.direction.multiply(0.6).y - 0.2
                chicken.velocity = vector
                // 치킨의 움직임에 벡터를 대입시킨다.
                if (chicken.isOnGround) {
                    chicken.remove()
                    cancel()
                    // 치킨이 땅에 닿으면 치킨을 없애고 스케듈러를 종료시킨다
                }
                if (StreetSuv.Boolean[p.uniqueId]!!) {
                    chicken.remove()
                    cancel()
                    //임의적으로 Boolean이 true되면 아까전의 작업을 실행시킨다.
                }
            }
        }.runTaskTimer(Succession.instance, 0, 1)
        return chicken
    }

    fun faceDirection(player: Player, target: Location) {
        val dir = target.clone().subtract(player.eyeLocation).toVector()
        val loc = player.location.setDirection(dir)
        //        player.teleportAsync(loc);
        val pm: ProtocolManager = ProtocolLibrary.getProtocolManager()
        val packet: PacketContainer = pm.createPacket(PacketType.Play.Server.ENTITY_LOOK)
        packet.integers.write(0, player.entityId)
        packet.bytes.write(0, (loc.yaw * 256f / 360f).toInt().toByte())
        packet.bytes.write(1, (loc.pitch * 256f / 360f).toInt().toByte())
        try {
            pm.sendServerPacket(player, packet)
        } catch (i: InvocationTargetException) {
            i.printStackTrace()
        }
    }

    fun Street1v1Hotbar(p: Player) {
        val Exit = ItemStack(Material.STRUCTURE_VOID)
        val ExitMeta = Exit.itemMeta
        ExitMeta.setDisplayName("§c대기열 퇴장")
        ExitMeta.lore = listOf("§7대기중인 대기열에서 퇴장합니다.")
        Exit.itemMeta = ExitMeta
        p.inventory.setItem(3, Exit)
    }

    fun Street1v1HotbarRemove(p: Player) {
        val Exit = ItemStack(Material.STRUCTURE_VOID)
        val ExitMeta = Exit.itemMeta
        ExitMeta.setDisplayName("§c대기열 퇴장")
        ExitMeta.lore = listOf("§7대기중인 대기열에서 퇴장합니다.")
        Exit.itemMeta = ExitMeta
        p.inventory.remove(Exit)
    }

    fun Street1v1Clear(p: Player) {
        Street1vs1.queueList.remove(p.name + ", " + Street1vs1.queueType[p.uniqueId]!!)
        Street1vs1.normalWait.remove(p)
        Street1vs1.singleWait.remove(p)
        Street1vs1.comboWait.remove(p)
        Street1vs1.normalRoom1.remove(p)
        Street1vs1.normalRoom2.remove(p)
        Street1vs1.singleRoom1.remove(p)
        Street1vs1.singleRoom2.remove(p)
        Street1vs1.comboRoom1.remove(p)
        Street1vs1.comboRoom2.remove(p)
        Street1vs1.you.remove(p.uniqueId)
        Street1vs1.youRoomID[p.uniqueId] = 0
        Street1vs1.youDisplayName[p.uniqueId] = "NONE"
        Street1vs1.playing[p.uniqueId] = false
        Street1vs1.queueType[p.uniqueId] = "NONE"
    }

    fun Street1v1End(
        loser: Player,
        winner: Player,
        loserDisplayName: String,
        winnerDisplayName: String,
        type: String?,
        room: Int?
    ) {
        Bukkit.broadcastMessage("§6§l시가전 대전! §7$winnerDisplayName§7님이 $loserDisplayName§7님을 이기고 승리했습니다!")
        User.IsPlaying[loser.uniqueId] = false
        User.IsPlaying[winner.uniqueId] = false
        LoseWinCount(winner, loser)
        loser.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5))
        winner.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5))
        winner.health = 20.0
        loser.inventory.clear()
        winner.inventory.clear()
        when (type) {
            "단일" -> {
                when (room) {
                    1 -> {
                        Street1vs1.singleRoomState1 = false
                        Street1vs1.singleRoomState2 = false
                    }
                    2 -> Street1vs1.singleRoomState2 = false
                }
                when (room) {
                    1 -> {
                        Street1vs1.normalRoomState1 = false
                        Street1vs1.normalRoomState2 = false
                    }
                    2 -> Street1vs1.normalRoomState2 = false
                }
                when (room) {
                    1 -> {
                        Street1vs1.comboRoomState1 = false
                        Street1vs1.comboRoomState2 = false
                    }
                    2 -> Street1vs1.comboRoomState2 = false
                }
            }
            "일반" -> {
                when (room) {
                    1 -> {
                        Street1vs1.normalRoomState1 = false
                        Street1vs1.normalRoomState2 = false
                    }
                    2 -> Street1vs1.normalRoomState2 = false
                }
                when (room) {
                    1 -> {
                        Street1vs1.comboRoomState1 = false
                        Street1vs1.comboRoomState2 = false
                    }
                    2 -> Street1vs1.comboRoomState2 = false
                }
            }
            "콤보" -> when (room) {
                1 -> {
                    Street1vs1.comboRoomState1 = false
                    Street1vs1.comboRoomState2 = false
                }
                2 -> Street1vs1.comboRoomState2 = false
            }
        }
        Street1v1Clear(loser)
        Street1v1Clear(winner)
    }

    fun Street1v1Start(you: Player, me: Player, type: String, room: Int) {
        User.IsPlaying[you.uniqueId] = true
        User.IsPlaying[me.uniqueId] = true
        when (type) {
            "단일" -> when (room) {
                1 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    Bukkit.broadcastMessage(Street1vs1.youDisplayName[you.uniqueId]!!)
                    Bukkit.broadcastMessage(Street1vs1.youDisplayName[me.uniqueId]!!)
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.singleLocation1)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
                2 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.singleLocation2)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
            }
            "일반" -> when (room) {
                1 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.normalLocation1)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
                2 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.normalLocation2)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
            }
            "콤보" -> when (room) {
                1 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.comboLocation1)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
                2 -> {
                    me.sendMessage("§6§l시가전 대전! §7" + you.displayName + "님과 대전을 시작합니다")
                    you.sendMessage("§6§l시가전 대전! §7" + me.displayName + "님과 대전을 시작합니다")
                    Street1vs1.playing[me.uniqueId] = true
                    Street1vs1.playing[you.uniqueId] = true
                    Street1v1HotbarRemove(me)
                    Street1v1HotbarRemove(you)
                    Street1vs1.youDisplayName[me.uniqueId] = you.displayName
                    Street1vs1.youDisplayName[you.uniqueId] = me.displayName
                    val array: ArrayList<Location> = ArrayList<Location>(Street1vs1.comboLocation2)
                    array.shuffle()
                    you.teleport(array[0])
                    array.remove(array[0])
                    array.shuffle()
                    me.teleport(array[0])
                    array.remove(array[0])
                    faceDirection(me, you.location)
                    faceDirection(you, me.location)
                    me.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    me.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    me.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    me.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    me.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    me.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    me.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    me.inventory.heldItemSlot = 0
                    you.equipment?.setItem(EquipmentSlot.HEAD, ItemStack(Material.IRON_HELMET))
                    you.equipment?.setItem(EquipmentSlot.CHEST, ItemStack(Material.IRON_CHESTPLATE))
                    you.equipment?.setItem(EquipmentSlot.LEGS, ItemStack(Material.IRON_LEGGINGS))
                    you.equipment?.setItem(EquipmentSlot.FEET, ItemStack(Material.IRON_BOOTS))
                    you.equipment?.setItem(EquipmentSlot.OFF_HAND, ItemStack(Material.SHIELD))
                    you.inventory.setItem(0, ItemStack(Material.IRON_SWORD))
                    you.inventory.setItem(1, ItemStack(Material.STONE_AXE))
                    you.inventory.heldItemSlot = 0
                }
            }
        }
    }

    fun Street1v1WaitStart(Waitplayers: ArrayList<Player>, type: String) {
        val Deletes = ArrayList<Player?>()
        var i = 0
        Waitplayers.shuffle()
        when (type) {
            "일반" -> {
                for (p in Waitplayers) {
                    val next = i + 1
                    if (!Deletes.contains(p)) {
                        if (!Street1vs1.normalRoomState1) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 1
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 1
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 1)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    } else if (!Street1vs1.normalRoomState2) {
                        if (!Deletes.contains(p)) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 2
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 2
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 2)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    }
                    i++
                }
                Waitplayers.removeAll(Deletes)
                Deletes.clear()
            }
            "콤보" -> {
                for (p in Waitplayers) {
                    val next = i + 1
                    if (!Deletes.contains(p)) {
                        if (!Street1vs1.comboRoomState1) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 1
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 1
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 1)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    } else if (!Street1vs1.comboRoomState2) {
                        if (!Deletes.contains(p)) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 2
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 2
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 2)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    }
                    i++
                }
                Waitplayers.removeAll(Deletes)
                Deletes.clear()
                for (p in Waitplayers) {
                    val next = i + 1
                    if (!Deletes.contains(p)) {
                        if (!Street1vs1.singleRoomState1) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 1
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 1
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 1)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    } else if (!Street1vs1.singleRoomState2) {
                        if (!Deletes.contains(p)) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 2
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 2
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 2)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    }
                    i++
                }
                Waitplayers.removeAll(Deletes)
                Deletes.clear()
            }
            "단일" -> {
                for (p in Waitplayers) {
                    val next = i + 1
                    if (!Deletes.contains(p)) {
                        if (!Street1vs1.singleRoomState1) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 1
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 1
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 1)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    } else if (!Street1vs1.singleRoomState2) {
                        if (!Deletes.contains(p)) {
                            if (Waitplayers.size >= next + 1) {
                                Street1vs1.youRoomID[p.uniqueId] = 2
                                Street1vs1.youRoomID[Waitplayers[next].uniqueId] = 2
                                Street1vs1.you[p.uniqueId] = Waitplayers[next]
                                Street1vs1.you[Waitplayers[next].uniqueId] = p
                                Street1v1Start(Waitplayers[next], p, Street1vs1.queueType[p.uniqueId]!!, 2)
                                Deletes.add(p)
                                Deletes.add(Waitplayers[next])
                            }
                        }
                    }
                    i++
                }
                Waitplayers.removeAll(Deletes)
                Deletes.clear()
            }
        }
    }

    fun LoseWinCount(winner: Player, loser: Player) {
        User.WinCount[winner.uniqueId] = User.WinCount[winner.uniqueId]!! + 1
        User.LoseCount[loser.uniqueId] =
            User.LoseCount[loser.uniqueId]!! + 1
    }
}