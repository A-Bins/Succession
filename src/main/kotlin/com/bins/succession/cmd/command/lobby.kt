package com.bins.succession.cmd.command

import com.bins.succession.game.manager.RaceManager
import com.bins.succession.game.manager.StreetManager
import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class lobby : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val p = sender
            if (StreetSuv.StreetSingleWarChannel1.contains(p) and StreetSuv.StreetSingleWarStateChannel1) {
                p.health = 0.0
            } else if (Street1vs1.playing[p.uniqueId]!!) {
                p.health = 0.0
            } else if (Race.RacePlaying[p.uniqueId]!!) {
                RaceManager.RaceChannelClear(
                    p,
                    Race.Raceyou[p.uniqueId]!!,
                    Race.RaceyouDisplayName[Race.Raceyou[p.uniqueId]!!.uniqueId]!!,
                    Race.RaceyouDisplayName[p.uniqueId]!!,
                    Race.RaceRoomID[Race.Raceyou[p.uniqueId]!!.uniqueId]!!
                )
            } else if (Street1vs1.queueList.contains(p.name + ", " + Street1vs1.queueType[p.uniqueId])) {
                StreetManager.Street1v1Hotbar(p)
            } else if (Sabotage.SabotageReadying[p.uniqueId]!!) {
                Sabotage.SabotagePlayers.remove(p)
                Sabotage.SabotageReadying[p.uniqueId] = false
                p.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5, 90F, 1F))
            } else {
                p.teleport(Location(Bukkit.getWorld("world"), 10004.5, 40.0, 10000.5, 90F, 1F))
                StreetManager.PlayerStreetSingleWarReset(p, "Exit")
            }
            p.isGlowing = false
            p.inventory.clear()
        }
        return false
    }
}