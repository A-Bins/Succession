package com.bins.succession.event

import com.bins.succession.game.manager.StreetManager
import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Sound
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.entity.Snowball
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.*
import org.bukkit.event.player.PlayerDropItemEvent
import org.bukkit.event.player.PlayerRespawnEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.util.Vector
import org.spigotmc.event.entity.EntityDismountEvent
import java.util.*
import java.util.function.Consumer
import kotlin.math.roundToInt

class Death : Listener {
    @EventHandler
    fun onDismount(e: EntityDismountEvent) {
        if (e.entity !is Player) return
        StreetSuv.Boolean[e.entity.uniqueId] = true
    }

    @EventHandler
    fun onRespawn(e: PlayerRespawnEvent) {
        e.respawnLocation = Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5)
    }

    @EventHandler
    fun onDrop(e: PlayerDropItemEvent) {
        if (Street1vs1.queueList.contains(e.player.name + ", " + Street1vs1.queueType[e.player.uniqueId]) or Race.RaceImInChannel[e.player.uniqueId]!! or Race.RacePlaying[e.player.uniqueId]!!
        ) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onDespawn(e: ItemDespawnEvent) {
        if (e.entity.customName != null && e.entity.customName == "Drop") {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun projectilehit(e: ProjectileHitEvent) {
        val hit = e.hitEntity
        val entity: Entity = e.entity
        val shooter = e.entity.shooter
        if (hit is Player) {
            if (entity is Snowball) {
                if (shooter is Player) {
                    User.RaceSnowBall[shooter.uniqueId] =
                        User.RaceSnowBall[shooter.uniqueId]!! + 1
                    val ve = (e.entity.shooter as Player?)!!.location.direction
                    val loc = hit.getLocation()
                    loc.direction = ve
                    loc.pitch = 0f
                    hit.damage(0.0001)
                    hit.health = 20.0
                    hit.setVelocity(Vector(0.0, 0.25, 0.0).add(loc.direction.multiply(0.4)))
                    hit.playSound(hit.getLocation(), Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                    shooter.playSound(shooter.location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1f, 1f)
                }
            }
        }
    }

    @EventHandler
    fun onDamage(e: EntityDamageEvent) {
        if (e.entity is Player) {
            val p = e.entity as Player
            if (Race.RacePlaying.containsKey(p.uniqueId)) {
                if (Race.RacePlaying[p.uniqueId]!!) {
                    e.isCancelled = true
                }
            }
            if (StreetSuv.StreetSingleWarChannel1.contains(p)) {
                if (StreetSuv.StreetSingleWarInvincibility) {
                    e.isCancelled = true
                }
            }
        }
    }

    @EventHandler
    fun onDamagebyEntity(e: EntityDamageByEntityEvent) {
        val vi = e.entity
        val at = e.damager
        if (vi is Player && at is Player) {
            if (StreetSuv.StreetSingleWarChannel1.contains(vi) && StreetSuv.StreetSingleWarChannel1.contains(at)) {
                User.StreetTakeDamage[vi.getUniqueId()] =
                    User.StreetTakeDamage[vi.getUniqueId()]!! + (e.damage * 100).roundToInt() / 100.0
                User.StreetDamage[at.getUniqueId()] =
                    User.StreetDamage[at.getUniqueId()]!! + (e.damage * 100).roundToInt() / 100.0
            }
        }
    }

    @EventHandler
    fun onDead(e: PlayerDeathEvent) {
        val p = e.entity
        val uuid: UUID = p.uniqueId
        if (Street1vs1.playing[p.uniqueId]!!) {
            val winner: Player = Street1vs1.you[p.uniqueId]!!
            val winneruuid: UUID = winner.uniqueId
            StreetManager.Street1v1End(
                p, winner, Street1vs1.youDisplayName[winner.uniqueId]!!, Street1vs1.youDisplayName[p.uniqueId]!!,
                Street1vs1.queueType[p.uniqueId]!!, Street1vs1.youRoomID[p.uniqueId]!!
            )
            StreetManager.Street1v1WaitStart(
                when {
                    Street1vs1.queueType[p.uniqueId]!! == "일반" -> Street1vs1.normalWait
                    Street1vs1.queueType[p.uniqueId]!! == "콤보" -> Street1vs1.comboWait
                    Street1vs1.queueType[p.uniqueId]!! == "단일" -> Street1vs1.singleWait
                    else -> Street1vs1.normalWait
                }, Street1vs1.queueType[p.uniqueId]!!)
            Street1vs1.queueList.remove(p.name + ", " + Street1vs1.queueType[p.uniqueId])
            Street1vs1.queueType.remove(p.uniqueId)
            Street1vs1.queueList.remove(winner.name + ", " + Street1vs1.queueType[winner.uniqueId])
            Street1vs1.queueType.remove(winner.uniqueId)
            User.Street1V1Kill[winneruuid] = User.Street1V1Kill[winneruuid]!! + 1
            User.Street1V1Death[uuid] = User.Street1V1Death[uuid]!! + 1
        }


        //서바이벌 대전 연산
        run {
            p.isGlowing = false
            if (StreetSuv.StreetSingleWarChannel1.contains(p)) {
                if (e.entity.killer != null) {
                    User.StreetWarKill[e.entity.killer!!.uniqueId] =
                        User.StreetWarKill[e.entity.killer!!.uniqueId]!! + 1
                    User.StreetWarDeath[uuid] = User.StreetWarDeath[uuid]!! + 1
                    StreetSuv.StreetSingleWarKillChannel1[e.entity.killer!!
                        .uniqueId] = StreetSuv.StreetSingleWarKillChannel1[e.entity.killer!!.uniqueId]!! + 1
                }
                p.inventory.forEach(
                    Consumer { `$`: ItemStack? ->
                        if (`$` != null) {
                            p.world.dropItemNaturally(p.location, `$`)
                        }
                    }
                )
                e.entity.inventory.clear()
                if (!StreetSuv.StreetSingleWarStateChannel1) return
                if (StreetSuv.StreetSingleWarChannel1.size <= 2) {
                    StreetManager.PlayerStreetSingleWarReset(p, "Death")
                    if (StreetSuv.StreetSingleWarChannel1.isNotEmpty()) {
                        StreetSuv.StreetSingleWarChannel1[0].isGlowing = false
                        User.StreetWarWin[StreetSuv.StreetSingleWarChannel1[0].uniqueId] =
                            User.StreetWarWin[StreetSuv.StreetSingleWarChannel1[0].uniqueId]!! + 1
                        StreetManager.PlayerStreetSingleWarReset(StreetSuv.StreetSingleWarChannel1[0], "Victory")
                    } else {
                        Bukkit.broadcastMessage("§7< §a1채널 §6서바이벌 개인전 §7> 남은 생존자가 없는 관계로 조기 종료합니다.")
                    }
                    StreetManager.StreetSingleWarReset()
                } else {
                    StreetManager.PlayerStreetSingleWarReset(p, "Death")
                }
            }
        }
    }
}