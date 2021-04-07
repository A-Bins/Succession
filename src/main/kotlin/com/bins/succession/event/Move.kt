package com.bins.succession.event

import com.bins.succession.game.manager.RaceManager
import com.bins.succession.game.vars.race.Race
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class Move : Listener {
    @EventHandler
    fun onMove(e: PlayerMoveEvent) {
        val p = e.player
        if (Race.Room1.contains(p)) {
            if (Race.Room1State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room1SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room2.contains(p)) {
            if (Race.Room2State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room2SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room3.contains(p)) {
            if (Race.Room3State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room3SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room4.contains(p)) {
            if (Race.Room4State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room4SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room5.contains(p)) {
            if (Race.Room5State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room5SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room6.contains(p)) {
            if (Race.Room6State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room6SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room7.contains(p)) {
            if (Race.Room7State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room7SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        } else if (Race.Room8.contains(p)) {
            if (Race.Room8State) {
                minusHeart(Location(e.player.world, e.to.x, e.to.y, e.to.z), p)
            }
            if (!Race.Room8SubState) {
                return
            }
            e.isCancelled = isCancelled(
                Location(
                    e.player.world,
                    e.from.x,
                    e.from.y,
                    e.from.z
                ), Location(e.player.world, e.to.x, e.to.y, e.to.z)
            )
        }
    }

    companion object {
        fun isCancelled(from: Location, to: Location): Boolean {
            return from != to
        }

        fun minusHeart(to: Location, p: Player) {
            if (to.add(0.0, -1.0, 0.0).toCenterLocation().block.type == Material.RED_TERRACOTTA) {
                p.teleport(Race.MyAreaLocation[p.uniqueId]!!)
                Race.Hearts[p.uniqueId] = Race.Hearts[p.uniqueId]!! - 1
                p.health = 20.0
                if (Race.Hearts[p.uniqueId]!! <= 0) {
                    RaceManager.RaceChannelClear(
                        p,
                        Race.Raceyou[p.uniqueId]!!,
                        Race.RaceyouDisplayName[Race.Raceyou[p.uniqueId]!!.uniqueId]!!,
                        Race.RaceyouDisplayName[p.uniqueId]!!,
                        Race.RaceRoomID[Race.Raceyou[p.uniqueId]!!.uniqueId]!!
                    )
                }
            }
        }
    }
}