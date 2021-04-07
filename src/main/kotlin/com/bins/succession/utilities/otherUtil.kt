package com.bins.succession.utilities

import com.bins.succession.Succession
import net.minecraft.server.v1_16_R3.*
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.entity.Player

object otherUtil {
    fun sendGlowingBlock(p: Player, loc: Location, lifetime: Long): EntityShulker {
        val connection: PlayerConnection = (p as CraftPlayer).handle.playerConnection
        //  Scoreboard nmsScoreBoard = ((CraftScoreboard) Bukkit.getScoreboardManager().getMainScoreboard()).getHandle();
        val shulker = EntityShulker(EntityTypes.SHULKER, (loc.world as CraftWorld).handle)
        shulker.setLocation(loc.x, loc.y, loc.z, 0F, 0F)
        shulker.setFlag(6, true) //Glow
        shulker.setFlag(5, true) //Invisibility
        val spawnPacket = PacketPlayOutSpawnEntityLiving(shulker)
        connection.sendPacket(spawnPacket)
        Bukkit.getScheduler().scheduleSyncDelayedTask(Succession.instance, {
            val destroyPacket = PacketPlayOutEntityDestroy(shulker.id)
            connection.sendPacket(destroyPacket)
        }, lifetime + ((Math.random() + 1) * 100).toLong())
        return shulker
    }

    fun removeGlowingBlock(p: Player, shulker: EntityShulker) {
        val connection: PlayerConnection = (p as CraftPlayer).handle.playerConnection
        val destroyPacket = PacketPlayOutEntityDestroy(shulker.id)
        connection.sendPacket(destroyPacket)
    }
}