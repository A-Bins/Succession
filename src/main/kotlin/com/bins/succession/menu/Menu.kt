package com.bins.succession.menu

import java.util.UUID
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import org.bukkit.Bukkit
import org.bukkit.DyeColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import com.bins.succession.game.vars.race.Race.Companion.Room1
import com.bins.succession.game.vars.race.Race.Companion.Room2
import com.bins.succession.game.vars.race.Race.Companion.Room3
import com.bins.succession.game.vars.race.Race.Companion.Room4
import com.bins.succession.game.vars.race.Race.Companion.Room5
import com.bins.succession.game.vars.race.Race.Companion.Room6
import com.bins.succession.game.vars.race.Race.Companion.Room7
import com.bins.succession.game.vars.race.Race.Companion.Room8


class Menu(player: Player) {
    var uuid: UUID = player.uniqueId
    private val Race: ItemStack = object : ItemStack(Material.LEATHER_BOOTS) {
        init {
            val Meta = itemMeta as LeatherArmorMeta
            Meta.setColor(DyeColor.RED.color)
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a경주")
            Meta.lore = listOf(
                "§8경쟁",
                "",
                "§7시작 지점에서 출발하여 경주 지점까지 먼저",
                "§7도착하면 승리하는 게임입니다! 상대방을 방해하며",
                "§7보다 먼저 완주지점에 도착하세요!",
                "",
                "§a접속하려면 클릭하세요!",
                "",
                "§7현재 " + (Room1.size + Room2.size + Room3.size + Room4.size + Room5.size + Room6.size + Room7.size + Room8.size) + "명이 접속중입니다!"
            )
            itemMeta = Meta
        }
    }
    private val StreetWar: ItemStack = object : ItemStack(Material.IRON_SWORD) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            Meta.setDisplayName("§a시가전")
            Meta.lore = listOf(
                "§8경쟁",
                "",
                "§7무작위로 주어지는 무기를 가지고",
                "§7규칙에 따라 최후의 1인이 되거나, ",
                "§7상대를 쓰러뜨려 승리하세요!",
                "",
                "§a접속하려면 클릭하세요!",
                "",
                "§7현재 " + StreetSuv.StreetSingleWarChannel1.size + "명이 접속중입니다!"
            )
            itemMeta = Meta
        }
    }
    private val WonderLand: ItemStack = object : ItemStack(Material.NETHER_STAR) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            Meta.setDisplayName("§a원더랜드")
            Meta.lore = listOf(
                "§8모험, 지속적인 게임",
                "",
                "§7혼자서, 혹은 친구와 함께 플레이하며, 가보지 않은 곳을",
                "§7모험하고, 성장하며 모든 도전과제를 달성 해보세요!",
                "",
                "§a접속하려면 클릭하세요!",
                "",
                "§7현재 ( 미확인 )명이 접속중입니다!"
            )
            itemMeta = Meta
        }
    }
    private val Sabotage: ItemStack = object : ItemStack(Material.SNOWBALL) {
        init {
            val SabotageMeta = itemMeta
            SabotageMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            SabotageMeta.setDisplayName("§a사보추어")
            SabotageMeta.lore = listOf(
                "§8협동 생존, 추리",
                "",
                "§78명의 생존자 속에 교묘하게 숨어든 2명의 배신자를 찾아내고,",
                "§7모든 임무를 성공적으로 완수해 설산에서 탈출하세요!",
                "",
                "§a접속하려면 클릭하세요!",
                "",
                "§7현재 " + com.bins.succession.game.vars.Sabotage.Sabotage.SabotagePlayers.size + "명이 접속중입니다!"
            )
            itemMeta = SabotageMeta
        }
    }
    private val Statistics: ItemStack = object : ItemStack(Material.ENDER_EYE) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a통계")
            Meta.lore = listOf(
                "§7모든 게임의 통계 내역을 확인합니다",
                "",
                "§a클릭해 통계 내역을 확인합니다"
            )
            itemMeta = Meta
        }
    }
    private val Challenges: ItemStack = object : ItemStack(Material.TOTEM_OF_UNDYING) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a도전과제")
            Meta.lore = listOf(
                "§7게임을 진행하면서 이룰 수 있는 목표인 도전과제입니다",
                "",
                "§a클릭해 도전과제을 확인합니다"
            )
            itemMeta = Meta
        }
    }
    private val Decorative: ItemStack = object : ItemStack(Material.DRAGON_BREATH) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a치장품")
            Meta.lore = listOf(
                "§7전리품 상자에서 획득한 치장품입니다",
                "",
                "§a클릭해 치장품을 확인합니다"
            )
            itemMeta = Meta
        }
    }
    private val Support: ItemStack = object : ItemStack(Material.GOLD_INGOT) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a팀 후원")
            Meta.lore = listOf(
                "§7Project Agora를 제작한 팀 owo를 후원해주세요!",
                "",
                "§a클릭해 확인합니다"
            )
            itemMeta = Meta
        }
    }
    private val GameMenu: ItemStack = object : ItemStack(Material.COMPASS) {
        init {
            val SabotageMeta = itemMeta
            SabotageMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            SabotageMeta.setDisplayName("§a게임 메뉴")
            SabotageMeta.lore = listOf(
                "§75가지 이상의 게임이 기다리고 있습니다!",
                "",
                "§a클릭 해 게임을 살펴봅니다"
            )
            itemMeta = SabotageMeta
        }
    }
    private val RaceStatics: ItemStack = object : ItemStack(Material.LEATHER_BOOTS) {
        init {
            val Meta = itemMeta as LeatherArmorMeta
            Meta.setColor(DyeColor.RED.color)
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a경주 통계")
            Meta.lore = listOf(
                "§7블록을 설치한 횟수: §a" + User.RaceBlockPlace[uuid],
                "§7우승한 횟수: §a" + User.RaceWin[uuid],
                "",
                "§7눈덩이를 맞춘 횟수: §a" + User.RaceSnowBall[uuid],
                "§7방해 블록을 설치한 횟수: §a" + User.RaceInterference[uuid],
                "",
                "§7수평모드를 진행한 횟수: §a" + User.RaceVertical[uuid],
                "§7수직모드를 진행한 횟수: §a" + User.RaceHorizontal[uuid]
            )
            itemMeta = Meta
        }
    }
    private val StreetWarStatics: ItemStack = object : ItemStack(Material.IRON_SWORD) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(*ItemFlag.values())
            Meta.setDisplayName("§a시가전 통계")
            Meta.lore = listOf(
                "§7대전 모드에서 적 처치: §a" + User.Street1V1Kill[uuid],
                "§7대전 모드에서 사망: §a" + User.Street1V1Death[uuid],
                "",
                "§7생존 모드에서 우승: §a" + User.StreetWarWin[uuid],
                "§7생존 모드에서 사살: §a" + User.StreetWarKill[uuid],
                "§7생존 모드에서 사망: §a" + User.StreetWarDeath[uuid],
                "",
                "§7총 입힌 피해량: §a" + User.StreetDamage[uuid],
                "§7총 받은 피해량: §a" + User.StreetTakeDamage[uuid],
                "§7무작위 무기 상자를 연 횟수: §a" + User.StreetOpenCount[uuid]
            )
            itemMeta = Meta
        }
    }
    private val WonderLandStatics: ItemStack = object : ItemStack(Material.NETHER_STAR) {
        init {
            val Meta = itemMeta
            Meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            Meta.setDisplayName("§a원더랜드 통계")
            Meta.lore = listOf()
            itemMeta = Meta
        }
    }
    private val SabotageStatics: ItemStack = object : ItemStack(Material.SNOWBALL) {
        init {
            val SabotageMeta = itemMeta
            SabotageMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            SabotageMeta.setDisplayName("§a사보추어 통계")
            SabotageMeta.lore = listOf()
            itemMeta = SabotageMeta
        }
    }

    fun toMainMenu(): Inventory {
        val inv = Bukkit.createInventory(null, 27, "메뉴")
        inv.setItem(10, GameMenu)
        inv.setItem(12, Statistics)
        inv.setItem(13, Challenges)
        inv.setItem(14, Decorative)
        inv.setItem(16, Support)
        return inv
    }

    fun toStatics(): Inventory {
        val inv = Bukkit.createInventory(null, 27, "통계")
        inv.setItem(10, RaceStatics)
        inv.setItem(12, StreetWarStatics)
        inv.setItem(14, WonderLandStatics)
        inv.setItem(16, SabotageStatics)
        return inv
    }

    fun toGameMenu(): Inventory {
        val inv = Bukkit.createInventory(null, 27, "게임 메뉴")
        val BLACK = ItemStack(Material.BLACK_STAINED_GLASS_PANE)
        val BLACKMeta = BLACK.itemMeta
        BLACKMeta.setDisplayName("§f")
        BLACK.itemMeta = BLACKMeta
        inv.setItem(0, BLACK)
        inv.setItem(8, BLACK)
        inv.setItem(9, BLACK)
        inv.setItem(17, BLACK)
        inv.setItem(18, BLACK)
        inv.setItem(26, BLACK)
        inv.setItem(10, Race)
        inv.setItem(12, StreetWar)
        inv.setItem(14, WonderLand)
        inv.setItem(16, Sabotage)
        return inv
    }

}