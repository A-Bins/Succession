package com.bins.succession.event

import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.sk89q.worldedit.bukkit.BukkitAdapter
import com.sk89q.worldedit.math.BlockVector3
import com.sk89q.worldguard.WorldGuard
import com.sk89q.worldguard.protection.ApplicableRegionSet
import com.sk89q.worldguard.protection.managers.RegionManager
import com.sk89q.worldguard.protection.regions.RegionContainer
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent

class RegionLeave : Listener {
    @EventHandler
    fun Leave(e: PlayerMoveEvent) {
        val p = e.player
        if (!Race.RacePlaying.containsKey(p.uniqueId)) return
        val container: RegionContainer = WorldGuard.getInstance().platform.regionContainer
        val regionManager: RegionManager = container.get(BukkitAdapter.adapt(p.world))!!
        val loc: BlockVector3 = BlockVector3.at(e.to.x, e.to.y, e.to.z)
        val regionSet: ApplicableRegionSet = regionManager.getApplicableRegions(loc)

        if (Race.RacePlaying[p.uniqueId]!!) {
            for (`$` in regionSet.regions) {
                if (`$`.id.contains("line")) {
                    e.isCancelled = true
                    return
                }
            }
        }
        if (!Sabotage.SabotageReadying.containsKey(p.uniqueId)) return
        if (!Sabotage.SabotageReadying[p.uniqueId]!!) return
        for (`$` in regionSet.regions) {
            if (`$`.id.contains("sabotage")) {
                return
            }
        }
        e.isCancelled = true
    }
}