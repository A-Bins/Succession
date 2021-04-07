package com.bins.succession.cmd.command

import com.bins.succession.game.manager.SabotageManager.SabotageStart
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.utilities.util.bb
import com.bins.succession.info.data.Server
import com.bins.succession.info.data.User.ChestAmount
import com.bins.succession.Succession
import com.bins.succession.friends.Friend
import com.bins.succession.utilities.utilJson
import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.scoreboard.*


class Test : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<String>): Boolean {
        if (sender is Player) {
            val p = sender
            if (!p.isOp) return false
            if (args.isNotEmpty()) {
                if (args[0] == "리로드") {
                    Bukkit.broadcastMessage("여태까지 한 리로드 횟수는 " + Server.ReloadAmount["Server"])
                    return false
                } else if (args[0] == "5") {
                    val manager = Bukkit.getScoreboardManager()
                    val board: Scoreboard = manager.newScoreboard

                    val objective: Objective = board.registerNewObjective("showhealth", "health", "/ 20")
                    objective.displaySlot = DisplaySlot.BELOW_NAME

                    for (online in Bukkit.getOnlinePlayers()) {
                        online.scoreboard = board
                        online.health = online.health //Update their health
                    }
                }
            }
            //            Sabotage.SabotagePlayers.add(you.getLocation());
//            SabotageStart();
        }
        return false
    }
}