package com.bins.succession.info.data

import java.util.HashMap
import java.util.UUID

object User {
    
    val ChestAmount = HashMap<UUID, Int>()
    val IsClick = HashMap<UUID, Boolean>()
    val IsPlaying = HashMap<UUID, Boolean>()
    val WinCount = HashMap<UUID, Int>()
    val LoseCount = HashMap<UUID, Int>()
    
    val RaceBlockPlace = HashMap<UUID, Int>()
    
    val RaceWin = HashMap<UUID, Int>()
    
    val RaceSnowBall = HashMap<UUID, Int>()
    
    val RaceInterference = HashMap<UUID, Int>()
    
    val RaceVertical = HashMap<UUID, Int>()
    
    val RaceHorizontal = HashMap<UUID, Int>()
    
    val Street1V1Kill = HashMap<UUID, Int>()
    
    val Street1V1Death = HashMap<UUID, Int>()
    
    val StreetWarWin = HashMap<UUID, Int>()
    
    val StreetWarKill = HashMap<UUID, Int>()
    
    val StreetWarDeath = HashMap<UUID, Int>()
    
    val StreetDamage = HashMap<UUID, Double>()
    
    val StreetTakeDamage = HashMap<UUID, Double>()
    
    val StreetOpenCount = HashMap<UUID, Int>()
}