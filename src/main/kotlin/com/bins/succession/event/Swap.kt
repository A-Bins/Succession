package com.bins.succession.event

import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import com.bins.succession.menu.Menu
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerSwapHandItemsEvent
import java.util.*

class Swap : Listener {
    @EventHandler
    fun onSwap(e: PlayerSwapHandItemsEvent) {
        val p = e.player
        val uuid: UUID = p.uniqueId
        if (Sabotage.SabotagePlaying.containsKey(uuid)) {
            if (Sabotage.SabotagePlaying[uuid]!!) {
                p.openInventory(Sabotage.getBag(uuid))
                e.isCancelled = true
                return
            }
        }
        if (User.IsPlaying[uuid]!!) return
        if (Street1vs1.queueList.contains(p.name + ", " + Street1vs1.queueType[uuid])
            or Race.RaceImInChannel[uuid]!!
            or StreetSuv.StreetSingleWarChannel1.contains(p)
        ) {
            if (e.offHandItem == null) {
                return
            }
            if (e.offHandItem!!.type == Material.STRUCTURE_VOID) {
                e.isCancelled = true
                return
            }
            return
        }
        e.isCancelled = true
        val main = Menu(p)
        p.openInventory(main.toMainMenu())
    }
}