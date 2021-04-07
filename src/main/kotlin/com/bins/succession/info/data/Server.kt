package com.bins.succession.info.data

import net.minecraft.server.v1_16_R3.EntityArmorStand
import org.bukkit.Location
import java.util.*
import kotlin.collections.HashMap

object Server {
    val ReloadAmount = HashMap<String, Int>()
    val HoloArmorStand: ArrayList<EntityArmorStand> = ArrayList<EntityArmorStand>()
    val HoloArmorStandId: ArrayList<UUID> = ArrayList<UUID>()
    val HoloArmorStandLoc: HashMap<UUID, Location> = HashMap<UUID, Location>()
    val HoloArmorStandStr: HashMap<UUID, String> = HashMap<UUID, String>()
}