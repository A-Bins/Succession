package com.bins.succession.event

import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.info.data.Server
import com.bins.succession.info.data.User
import com.bins.succession.Succession
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.ArmorStand
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.block.BlockPlaceEvent
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.util.EulerAngle
import org.bukkit.util.Vector
import java.util.*
import kotlin.math.cos
import kotlin.math.sin

class Block : Listener {
    @EventHandler
    fun onBreak(e: BlockBreakEvent) {
        val p = e.player
        if (e.block.type == Material.PLAYER_HEAD) {
            val b = e.block
            for (i in b.drops) {
                if ((i.itemMeta as SkullMeta).owningPlayer == null) continue
                if ((i.itemMeta as SkullMeta).owningPlayer!!.name == "wkwmd") {
                    e.isCancelled = true
                    if (Sabotage.WoodComplete[p.uniqueId]!!) return
                    if (Sabotage.WoodIsBreaking[p.uniqueId]!!) {
                        var str: String = Sabotage.WoodProcesses[p.uniqueId]!!
                        val index = str.indexOf("§r")
                        val c = str.toCharArray()
                        if (c[index + 6] == 'c') {
                            c[index] = ' '
                            c[index + 1] = ' '
                            c[index + 3] = '§'
                            c[index + 4] = 'r'
                            Sabotage.WoodComplete[p.uniqueId] = true
                            Sabotage.WoodIsBreaking[p.uniqueId] = false
                            p.sendActionBar("§aComplete! Your Successes!")
                            p.sendTitle("", "§aComplete! Your Successes!", 5, 40, 5)
                            p.playSound(p.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                            Sabotage.WoodProcesses[p.uniqueId] = str
                            return
                        }
                        c[index] = ' '
                        c[index + 1] = ' '
                        c[index + 5] = '§'
                        c[index + 6] = 'r'
                        str = String(c)
                        Sabotage.WoodProcesses[p.uniqueId] = str
                        return
                    }
                    Bukkit.broadcastMessage("장작이다!")
                    Sabotage.WoodIsBreaking[p.uniqueId] = true
                    //6번 해야함
                    //18초.
                    Bukkit.getScheduler().runTaskLater(
                        Succession.instance,
                        Runnable { Sabotage.WoodIsBreaking[p.uniqueId] = false },
                        (20 * 14).toLong()
                    )
                    Sabotage.WoodProcesses[p.uniqueId] = " §a§l|§a§l§m§r                                 §c§l| "
                    object : BukkitRunnable() {
                        override fun run() {
                            if (Sabotage.WoodComplete[p.uniqueId]!!) {
                                cancel()
                                return
                            }
                            if (!Sabotage.WoodIsBreaking[p.uniqueId]!!) {
                                p.sendMessage("§c실패했습니다! 조금더 빠르게 장작을 패세요!")
                                cancel()
                                return
                            }
                            p.sendActionBar(Sabotage.WoodProcesses[p.uniqueId]!!)
                        }
                    }.runTaskTimer(Succession.instance, 0, 1)
                }
            }
        }
        if (Race.RacePlaying.containsKey(e.player.uniqueId)) {
            if (Race.RacePlaying[e.player.uniqueId]!!) {
                e.block.drops.clear()
                if (!Race.PlacedBlockLocation[e.player.uniqueId]!!.contains(e.block.location.toCenterLocation())) {
                    e.isCancelled = true
                    return
                }
                return
            }
        }
        if (e.player.isOp) return
        e.isCancelled = true
    }

    @EventHandler
    fun onPlace(e: BlockPlaceEvent) {
        val b = e.block
        val p = e.player
        when (b.type) {
            Material.PLAYER_HEAD -> {
                for (i in b.drops) {
                    if ((i.itemMeta as SkullMeta).owningPlayer?.name == "wkwmd") {
                        val ar = b.world.spawn(b.location.toCenterLocation().add(+0.38, -0.5, -0.2), ArmorStand::class.java)
                        ar.equipment!!.setItem(EquipmentSlot.HAND, ItemStack(Material.NETHERITE_AXE))
                        ar.setArms(true)
                        ar.isInvulnerable = true
                        ar.isInvisible = true
                        ar.setGravity(false)
                        ar.setBasePlate(false)
                        ar.isMarker = true
                        ar.rightArmPose = EulerAngle(0.436332, 0.0, 0.0)
                    }
                }
            }
            Material.CAMPFIRE -> {
                val loc = b.location.toCenterLocation()
                var x: Double
                var z: Double
                var i = 0
                while (i < 135) {
                    val angle = i * 3 * Math.PI / 180
                    x = loc.x + 0.6 * cos(angle)
                    z = loc.z + 0.6 * sin(angle)
                    val ar = b.world.spawn(Location(loc.world, x + 0.375, loc.y - 1, z), ArmorStand::class.java)
                    ar.equipment!!.setItem(EquipmentSlot.HAND, ItemStack(Material.COBBLESTONE))
                    ar.setArms(true)
                    ar.isInvulnerable = true
                    ar.isInvisible = true
                    ar.setGravity(false)
                    ar.setBasePlate(false)
                    ar.isMarker = true
                    ar.rightArmPose = EulerAngle(0.436332, 0.0, 0.0)
                    i += 10
                }
                //            Double x = -0.3D;
    //            Double z = -0.6D;
    //            for (int i = 0; i < 100; i += 10) {
    //                    Boolean w = i >= 20;
    //                    x = w ? x + 0.1 : x - 0.1;
    //                    z += 0.3;
    //            }
            }
            Material.ENDER_CHEST -> {
                val loc = b.location.toCenterLocation()
                val s: WorldServer = (loc.world as CraftWorld).handle
                val armorStand = EntityArmorStand(s, loc.x, loc.y + 1.5, loc.z)
                armorStand.customNameVisible = true
                armorStand.customName = ChatComponentText("§6§l전리품 상자")
                armorStand.isNoGravity = true
                armorStand.isInvisible = true
                armorStand.isMarker = true
                val armorStand2 = EntityArmorStand(s, loc.x, loc.y + 1.25, loc.z)
                armorStand2.customNameVisible = true
                armorStand2.customName = ChatComponentText("§7무작위 치장품을 하나 발견합니다")
                armorStand2.isNoGravity = true
                armorStand2.isInvisible = true
                armorStand2.isMarker = true
                val armorStand3 = EntityArmorStand(s, loc.x, loc.y + 1, loc.z)
                armorStand3.customNameVisible = true
                armorStand3.customName = ChatComponentText("§7소지중인 전리품 상자: §a0개")
                armorStand3.isNoGravity = true
                armorStand3.isInvisible = true
                armorStand3.isMarker = true
                val armorStand4 = EntityArmorStand(s, loc.x, loc.y + 0.75, loc.z)
                armorStand4.customNameVisible = true
                armorStand4.customName = ChatComponentText("§e엔더상자를 클릭해 확인합니다!")
                armorStand4.isNoGravity = true
                armorStand4.isInvisible = true
                armorStand4.isMarker = true
                var packet: PacketPlayOutSpawnEntityLiving? = PacketPlayOutSpawnEntityLiving(armorStand)
                var meta: PacketPlayOutEntityMetadata? =
                    PacketPlayOutEntityMetadata(armorStand.id, armorStand.dataWatcher, true)
                for (a in Bukkit.getOnlinePlayers()) {
                    (a as CraftPlayer).handle.playerConnection.sendPacket(packet)
                    a.handle.playerConnection.sendPacket(meta)
                }
                packet = PacketPlayOutSpawnEntityLiving(armorStand2)
                meta = PacketPlayOutEntityMetadata(armorStand2.id, armorStand2.dataWatcher, true)
                for (a in Bukkit.getOnlinePlayers()) {
                    (a as CraftPlayer).handle.playerConnection.sendPacket(packet)
                    a.handle.playerConnection.sendPacket(meta)
                }
                packet = PacketPlayOutSpawnEntityLiving(armorStand3)
                meta = PacketPlayOutEntityMetadata(armorStand3.id, armorStand3.dataWatcher, true)
                for (a in Bukkit.getOnlinePlayers()) {
                    (a as CraftPlayer).handle.playerConnection.sendPacket(packet)
                    a.handle.playerConnection.sendPacket(meta)
                }
                packet = PacketPlayOutSpawnEntityLiving(armorStand4)
                meta = PacketPlayOutEntityMetadata(armorStand4.id, armorStand4.dataWatcher, true)
                for (a in Bukkit.getOnlinePlayers()) {
                    (a as CraftPlayer).handle.playerConnection.sendPacket(packet)
                    a.handle.playerConnection.sendPacket(meta)
                }
                Server.HoloArmorStand.add(armorStand)
                Server.HoloArmorStandId.add(armorStand.bukkitEntity.uniqueId)
                Server.HoloArmorStandLoc[armorStand.bukkitEntity.uniqueId] =
                    armorStand.bukkitEntity.location
                Server.HoloArmorStandStr[armorStand.bukkitEntity.uniqueId] = armorStand.customName?.text.toString()
                Server.HoloArmorStand.add(armorStand2)
                Server.HoloArmorStandId.add(armorStand2.bukkitEntity.uniqueId)

                Server.HoloArmorStandLoc[armorStand2.bukkitEntity.uniqueId] =
                    armorStand2.bukkitEntity.location

                Server.HoloArmorStandStr[armorStand2.bukkitEntity.uniqueId] =
                    armorStand2.customName?.text.toString()

                Server.HoloArmorStand.add(armorStand3)

                Server.HoloArmorStandId.add(armorStand3.bukkitEntity.uniqueId)

                Server.HoloArmorStandLoc[armorStand3.bukkitEntity.uniqueId] =
                    armorStand3.bukkitEntity.location

                Server.HoloArmorStandStr[armorStand3.bukkitEntity.uniqueId] =
                    armorStand3.customName?.text.toString()

                Server.HoloArmorStand.add(armorStand4)

                Server.HoloArmorStandId.add(armorStand4.bukkitEntity.uniqueId)

                Server.HoloArmorStandLoc[armorStand4.bukkitEntity.uniqueId] =
                    armorStand4.bukkitEntity.location

                Server.HoloArmorStandStr[armorStand4.bukkitEntity.uniqueId] =
                    armorStand4.customName?.text.toString()
            }
        }
        if (Race.RacePlaying[e.player.uniqueId]!!) {
            val you: Player = Race.Raceyou[e.player.uniqueId]!!
            if ((e.blockAgainst.type == Material.WHITE_STAINED_GLASS
                        ) or (e.blockAgainst.type == Material.LIGHT_GRAY_STAINED_GLASS
                        ) or (e.blockAgainst.type == Material.QUARTZ_SLAB
                        ) or (e.blockAgainst.type == Material.RED_TERRACOTTA
                        ) or (e.blockAgainst.type == Material.SEA_LANTERN
                        ) or (e.blockAgainst.type == Material.NETHERITE_BLOCK
                        ) or (e.blockAgainst.type == Material.WHITE_STAINED_GLASS_PANE)
            ) {
                e.isCancelled = true
                return
            } else if (e.itemInHand.itemMeta != null) {
                val name = e.itemInHand.itemMeta.displayName
                val uuid: UUID = you.uniqueId
                if (name.contains("헤롱헤롱")) {
                    User.RaceInterference[p.uniqueId] = User.RaceInterference[p.uniqueId]!! + 1
                    you.inventory.setItem(0, ItemStack(Material.SNOWBALL, 5))
                    you.addPotionEffect(PotionEffect(PotionEffectType.CONFUSION, 200, 1, false, false, false))
                    you.sendTitle("", "§e어...갑자기 어지러우시지 않나요?", 5, 30, 5)
                } else if (name.contains("하트")) {
                    Race.Hearts[e.player.uniqueId] = Race.Hearts[e.player.uniqueId]!! + 1
                    e.player.sendTitle("", "§c어 갑자기 수혈을 받았어요!", 5, 30, 5)
                } else if (name.contains("초심")) {
                    User.RaceInterference[p.uniqueId] = User.RaceInterference[p.uniqueId]!! + 1
                    you.inventory.setItem(0, ItemStack(Material.SNOWBALL, 5))
                    you.teleport(Race.MyAreaLocation[uuid]!!)
                    you.sendTitle("§a태초마을!", "§e상대방에 의하여 초심으로 돌아왔습니다!", 5, 30, 5)
                } else if (name.contains("개구리")) {
                    User.RaceInterference[p.uniqueId] = User.RaceInterference[p.uniqueId]!! + 1
                    you.inventory.setItem(0, ItemStack(Material.SNOWBALL, 5))
                    you.sendTitle("", "§a어?! 당신..개구리가 됐어요!", 5, 30, 5)
                    var i = 0
                    while (i < 15 * 4) {
                        Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                            you.velocity = you.velocity.add(Vector(0.05, 3.0, 0.0))
                            Bukkit.getScheduler().runTaskLater(Succession.instance, Runnable {
                                you.velocity = you.velocity.add(
                                    Vector(0.0, -4.0, 0.05)
                                )
                            }, 5)
                        }, i.toLong())
                        i += 15
                    }
                } else if (name.contains("정지")) {
                    User.RaceInterference[p.uniqueId] = User.RaceInterference[p.uniqueId]!! + 1
                    you.inventory.setItem(0, ItemStack(Material.SNOWBALL, 5))
                    val loc = Location(you.world, you.location.x, you.location.y, you.location.z).toCenterLocation()
                    you.sendTitle("", "§c상대방에 의하여 가둬졌습니다!", 5, 30, 5)
                    if (loc.clone().add(0.0, 2.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, 2.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, 2.0, 0.0))
                    }
                    if (loc.clone().add(0.0, -1.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, -1.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, -1.0, 0.0))
                    }
                    if (loc.clone().add(0.0, 0.0, 1.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, 0.0, 1.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, 0.0, 1.0))
                    }
                    if (loc.clone().add(0.0, 1.0, 1.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, 1.0, 1.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, 1.0, 1.0))
                    }
                    if (loc.clone().add(1.0, 0.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(1.0, 0.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(1.0, 0.0, 0.0))
                    }
                    if (loc.clone().add(1.0, 1.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(1.0, 1.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(1.0, 1.0, 0.0))
                    }
                    if (loc.clone().add(0.0, 0.0, -1.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, 0.0, -1.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, 0.0, -1.0))
                    }
                    if (loc.clone().add(0.0, 1.0, -1.0).block.type == Material.AIR) {
                        loc.clone().add(0.0, 1.0, -1.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(0.0, 1.0, -1.0))
                    }
                    if (loc.clone().add(-1.0, 0.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(-1.0, 0.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(-1.0, 0.0, 0.0))
                    }
                    if (loc.clone().add(-1.0, 1.0, 0.0).block.type == Material.AIR) {
                        loc.clone().add(-1.0, 1.0, 0.0).block.type = e.blockPlaced.type
                        Race.PlacedBlockLocation[you.uniqueId]!!.add(loc.clone().add(-1.0, 1.0, 0.0))
                    }
                } else if (name.contains("무중력")) {
                    User.RaceInterference[p.uniqueId] = User.RaceInterference[p.uniqueId]!! + 1
                    you.velocity = you.velocity.add(Vector(0, 2, 0))
                    you.addPotionEffect(PotionEffect(PotionEffectType.SLOW_FALLING, 150, 1, false, false, false))
                    you.sendTitle("", "§7상대방에 의하여 띄워졌습니다!", 5, 30, 5)
                    you.inventory.setItem(0, ItemStack(Material.SNOWBALL, 5))
                }
            }
            val slot = (Math.random() * (9 - 2 + 1) + 2).toInt() - 1
            e.player.inventory.setItem(
                slot,
                if (Race.RaceRoomID[e.player.uniqueId]!! > 4) Race.VerticalRandomBlocks() else Race.RandomBlocks()
            )
            Race.PlacedBlockLocation[e.player.uniqueId]!!.add(e.blockPlaced.location.toCenterLocation())
            User.RaceBlockPlace[p.uniqueId] = User.RaceBlockPlace[p.uniqueId]!! + 1
            return
        }
        if (Street1vs1.queueList.contains(e.player.name + ", " + Street1vs1.queueType[e.player.uniqueId]) or Race.RaceImInChannel[e.player.uniqueId]!!) {
            if (e.block.type == Material.STRUCTURE_VOID) {
                e.isCancelled = true
                return
            }
        }
        if (e.player.isOp) return
        e.isCancelled = true
    }
}