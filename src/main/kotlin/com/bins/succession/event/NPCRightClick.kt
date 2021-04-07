package com.bins.succession.event

import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import net.citizensnpcs.api.event.NPCRightClickEvent
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.*

class NPCRightClick : Listener {
    @EventHandler
    fun Right(e: NPCRightClickEvent) {
        if (Street1vs1.queueList.contains(e.clicker.name + ", " + Street1vs1.queueType[e.clicker.uniqueId])
            or (Race.RaceImInChannel[e.clicker.uniqueId]!!)
        ) {
            e.isCancelled = true
            return
        }
        if (e.npc.name.contains("시가전")) {
            val p: Player = e.clicker
            val uuid: UUID = p.uniqueId
            val SingleGameStart = ItemStack(Material.IRON_SWORD)
            val SingleGameStartMeta = SingleGameStart.itemMeta
            SingleGameStartMeta.setDisplayName("§a서바이벌 개인전")
            SingleGameStartMeta.lore = listOf(
                "§8캐주얼 게임",
                "",
                "§7무작위 무기를 받고 도심지에서",
                "§7다른 상대를 처치하거나 다른 상대의 공격으로부터",
                "§7버텨 제한 시간 내까지 생존하면 승리합니다",
                "",
                "§e클릭하여 대기실에 입장합니다"
            )
            SingleGameStartMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            SingleGameStart.setItemMeta(SingleGameStartMeta)
            StreetSuv.StreetWarJoin.setItem(14, SingleGameStart)
            val PvPGameStart = ItemStack(Material.IRON_SWORD)
            val PvPGameStartMeta = PvPGameStart.itemMeta
            PvPGameStartMeta.setDisplayName("§a1 vs 1 대전")
            PvPGameStartMeta.lore = listOf(
                "§8캐주얼 게임",
                "",
                "§7조건에 맞게 무작위 무기를 받고 제한 시간내에",
                "§7상대방을 쓰러뜨리면 승리합니다",
                "",
                "§e클릭하여 게임모드를 살펴봅니다"
            )
            PvPGameStartMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            PvPGameStart.setItemMeta(PvPGameStartMeta)
            StreetSuv.StreetWarJoin.setItem(12, PvPGameStart)
            val Introduction = ItemStack(Material.KNOWLEDGE_BOOK)
            val IntroductionMeta = Introduction.itemMeta
            IntroductionMeta.setDisplayName("§6게임 설명")
            IntroductionMeta.lore = listOf(
                "§7시가전은 무작위로 주어지는 무기를 가지고",
                "§7각 모드의 규칙에 따라 생존하거나, 적을 처치하면",
                "§7승리할 수 있는 게임입니다."
            )
            Introduction.setItemMeta(IntroductionMeta)
            StreetSuv.StreetWarJoin.setItem(22, Introduction)
            p.openInventory(StreetSuv.StreetWarJoin)
        } else if (e.npc.name.contains("경주")) {
            val p: Player = e.clicker
            val Verticality = ItemStack(Material.CHAINMAIL_BOOTS)
            val VerticalityMeta = Verticality.itemMeta
            VerticalityMeta.setDisplayName("§a수직 모드")
            VerticalityMeta.lore = listOf(
                "§8캐주얼 게임",
                "",
                "§7수직으로 놓여져 있는 완주 지점까지",
                "§7먼저 도달하면 승리 합니다",
                "",
                "§e클릭하여 대기열에 참여합니다"
            )
            VerticalityMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            Verticality.setItemMeta(VerticalityMeta)
            Race.RaceInventory.setItem(14, Verticality)
            val Horizontal = ItemStack(Material.LEATHER_BOOTS)
            val HorizontalMeta = Horizontal.itemMeta
            HorizontalMeta.setDisplayName("§a수평 모드")
            HorizontalMeta.lore = listOf(
                "§8캐주얼 게임",
                "",
                "§7수평으로 놓여져 있는 완주 지점까지",
                "§7먼저 도달하면 승리 합니다",
                "",
                "§e클릭하여 대기열에 참여합니다"
            )
            HorizontalMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
            Horizontal.setItemMeta(HorizontalMeta)
            Race.RaceInventory.setItem(12, Horizontal)
            val Introduction = ItemStack(Material.KNOWLEDGE_BOOK)
            val IntroductionMeta = Introduction.itemMeta
            IntroductionMeta.setDisplayName("§6게임 설명")
            IntroductionMeta.lore = listOf<String>(
                "§7경주는 상대방보다 더 빠르게 완주 지점까지",
                "§7블록을 가지고 길을 이으며 도달하면",
                "§7승리 할 수 있는 게임입니다."
            )
            Introduction.setItemMeta(IntroductionMeta)
            Race.RaceInventory.setItem(22, Introduction)
            p.openInventory(Race.RaceInventory)
        } else if (e.npc.name.contains("사보추어")) {
            e.clicker.openInventory(Sabotage.SabotageInventory)
        }
    }
}