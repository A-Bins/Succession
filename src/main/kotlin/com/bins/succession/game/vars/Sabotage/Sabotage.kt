package com.bins.succession.game.vars.Sabotage

import java.util.HashMap
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

object Sabotage {
    val SabotageInventory: Inventory
        get() {
            val i : Inventory = Bukkit.createInventory(null, 27, "사보추어")
            val Sabotage = ItemStack(Material.SNOWBALL)
            val SabotageMeta = Sabotage.itemMeta
            SabotageMeta.addItemFlags(*ItemFlag.values())
            SabotageMeta.setDisplayName("§a사보추어")
            SabotageMeta.lore = listOf(
                "§8협동, 생존",
                "",
                "§78명의 생존자 속에 교묘하게 숨어든",
                "§72명의 배신자를 찾아내고, 모든 임무를",
                "§7성공적으로 완수해 설산에서 탈출하세요!",
                "",
                "§e클릭시 대기실에 참가합니다!"
            )
            Sabotage.setItemMeta(SabotageMeta)
            val Intro = ItemStack(Material.KNOWLEDGE_BOOK)
            val IntroMeta = Intro.itemMeta
            IntroMeta.addItemFlags(*ItemFlag.values())
            IntroMeta.setDisplayName("§6게임 설명")
            IntroMeta.lore = listOf(
                "§7사보추어는 여러명의 생존자 무리에 있는",
                "§7배신자인 사보추어를 찾아내고 탈출 조건을",
                "§7만족시키고 탈출하면 승리하는 게임입니다"
            )
            Intro.setItemMeta(IntroMeta)
            i.setItem(13, Sabotage)
            i.setItem(22, Intro)
            return i
        }
    val SabotagePlayers = ArrayList<Player>()
    val SabotageReady = Location(Bukkit.getWorld("world"), -867.5, 38.toDouble(), -1132.5)
    val SabotagePlaying = HashMap<UUID, Boolean>()
    val WoodIsBreaking = HashMap<UUID, Boolean>()
    val SabotageReadying = HashMap<UUID, Boolean>()
    fun getBag(u: UUID): Inventory {
        val inv = Bukkit.createInventory(null, 9, "가방")
        val HardWoodBool = Items[u]!!["HardWood"] == 0
        val HardWood = ItemStack(
            if (HardWoodBool) Material.GRAY_DYE else Material.STICK,
            (if (HardWoodBool) 1 else Items[u]!!["HardWood"])!!
        )
        val HardWoodMeta = HardWood.itemMeta
        val HardWoodName = if (HardWoodBool) "§c" else "§f목재 §7(재료)"
        val HardWoodLore = ArrayList(
            if (HardWoodBool) listOf(
                "§c소지중이지 않은 재료이다"
            ) else listOf(
                "§8§o쓸만해 보이는 단단한 목재다.. ",
                "", "",
                "§a좌클릭§7시 §a1개 §7떨어트립니다.", "§a우클릭§7시 §a모두 §7떨어트립니다.", "", "§a웅크리고 좌클릭§7시 §a절반 §7떨어트립니다."
            )
        )
        HardWoodMeta.setDisplayName(HardWoodName)
        HardWoodMeta.setCustomModelData(if (HardWoodBool) 0 else 1)
        HardWoodMeta.addItemFlags(*ItemFlag.values())
        HardWoodMeta.lore = HardWoodLore
        HardWood.itemMeta = HardWoodMeta
        val MetalBool = Items[u]!!["Metal"] == 0
        val Metal = ItemStack(
            if (MetalBool) Material.GRAY_DYE else Material.NETHERITE_SCRAP,
            (if (MetalBool) 1 else Items[u]!!["Metal"])!!
        )
        val MetalMeta = Metal.itemMeta
        val MetalName = if (MetalBool) "§c" else "§f금속판 §7(재료)"
        val MetalLore = ArrayList(
            if (MetalBool) listOf(
                "§c소지중이지 않은 재료이다"
            ) else listOf(
                "§8§o겉은 녹슬어 보이지만 제 값을 하는 것 같다.. ",
                "", "",
                "§a좌클릭§7시 §a1개 §7떨어트립니다.", "§a우클릭§7시 §a모두 §7떨어트립니다.", "", "§a웅크리고 좌클릭§7시 §a절반 §7떨어트립니다."
            )
        )
        MetalMeta.setDisplayName(MetalName)
        MetalMeta.setCustomModelData(if (MetalBool) 0 else 2)
        MetalMeta.addItemFlags(*ItemFlag.values())
        MetalMeta.lore = MetalLore
        Metal.itemMeta = MetalMeta
        val FuelBool = Items[u]!!["Fuel"] == 0
        val Fuel = ItemStack(
            if (FuelBool) Material.GRAY_DYE else Material.CHARCOAL,
            (if (FuelBool) 1 else Items[u]!!["Fuel"])!!
        )
        val FuelMeta = Fuel.itemMeta
        val FuelName = if (FuelBool) "§c" else "§f연료 §7(재료)"
        val FuelLore = ArrayList(
            if (FuelBool) listOf(
                "§c소지중이지 않은 재료이다"
            ) else listOf(
                "§8§o효율이 좋지 않은 고체 연료이다..",
                "", "",
                "§a좌클릭§7시 §a1개 §7떨어트립니다.", "§a우클릭§7시 §a모두 §7떨어트립니다.", "", "§a웅크리고 좌클릭§7시 §a절반 §7떨어트립니다."
            )
        )
        FuelMeta.setDisplayName(FuelName)
        FuelMeta.setCustomModelData(if (FuelBool) 0 else 3)
        FuelMeta.addItemFlags(*ItemFlag.values())
        FuelMeta.lore = FuelLore
        Fuel.itemMeta = FuelMeta
        val MWBool = Items[u]!!["MW"] == 0
        val MW = ItemStack(
            if (MWBool) Material.GRAY_DYE else Material.PRISMARINE_CRYSTALS,
            (if (MWBool) 1 else Items[u]!!["MW"])!!
        )
        val MWMeta = MW.itemMeta
        val MWName = if (MWBool) "§c" else "§f기계 폐품 §7(재료)"
        val MWLore = ArrayList(
            if (MWBool) listOf(
                "§c소지중이지 않은 재료이다"
            ) else listOf(
                "§8§o삐걱삐걱 대는 기계폐품이다..",
                "", "",
                "§a좌클릭§7시 §a1개 §7떨어트립니다.", "§a우클릭§7시 §a모두 §7떨어트립니다.", "", "§a웅크리고 좌클릭§7시 §a절반 §7떨어트립니다."
            )
        )
        MWMeta.setDisplayName(MWName)
        MWMeta.setCustomModelData(if (MWBool) 0 else 4)
        MWMeta.addItemFlags(*ItemFlag.values())
        MWMeta.lore = MWLore
        MW.itemMeta = MWMeta
        val EWBool = Items[u]!!["EW"] == 0
        val EW = ItemStack(
            if (EWBool) Material.GRAY_DYE else Material.MUSIC_DISC_11,
            (if (EWBool) 1 else Items[u]!!["EW"])!!
        )
        val EWMeta = EW.itemMeta
        val EWName = if (EWBool) "§c" else "§f전자 폐품 §7(재료)"
        val EWLore = ArrayList(
            if (EWBool) listOf(
                "§c소지중이지 않은 재료이다"
            ) else listOf(
                "§8§o한때 최신식이었으나, 지금은 잘 쓰이지 않는 모양이다.",
                "", "",
                "§a좌클릭§7시 §a1개 §7떨어트립니다.", "§a우클릭§7시 §a모두 §7떨어트립니다.", "", "§a웅크리고 좌클릭§7시 §a절반 §7떨어트립니다."
            )
        )
        EWMeta.setDisplayName(EWName)
        EWMeta.setCustomModelData(if (EWBool) 0 else 5)
        EWMeta.addItemFlags(*ItemFlag.values())
        EWMeta.lore = EWLore
        EW.itemMeta = EWMeta
        inv.setItem(2, HardWood)
        inv.setItem(3, Metal)
        inv.setItem(4, Fuel)
        inv.setItem(5, MW)
        inv.setItem(6, EW)
        return inv
    }

    val Items = HashMap<UUID, HashMap<String, Int>>()
    val WoodProcesses = HashMap<UUID, String>()
    val WoodComplete = HashMap<UUID, Boolean>()
    fun SABOTAGE_ITEM_STACK_LIST(): List<ItemStack> {
        val itemStacks = ArrayList<ItemStack>()
        val HardWood = ItemStack(
            Material.STICK, 1
        )
        val HardWoodMeta = HardWood.itemMeta
        val HardWoodName = "§f목재 §7(재료)"
        val HardWoodLore = ArrayList(
            listOf(
                "§8§o쓸만해 보이는 단단한 목재다.. "
            )
        )
        HardWoodMeta.setDisplayName(HardWoodName)
        HardWoodMeta.setCustomModelData(1)
        HardWoodMeta.addItemFlags(*ItemFlag.values())
        HardWoodMeta.lore = HardWoodLore
        HardWood.itemMeta = HardWoodMeta
        val Metal = ItemStack(
            Material.NETHERITE_SCRAP, 1
        )
        val MetalMeta = Metal.itemMeta
        val MetalName = "§f금속판 §7(재료)"
        val MetalLore = ArrayList(
            listOf(
                "§8§o겉은 녹슬어 보이지만 제 값을 하는 것 같다.. "
            )
        )
        MetalMeta.setDisplayName(MetalName)
        MetalMeta.setCustomModelData(2)
        MetalMeta.addItemFlags(*ItemFlag.values())
        MetalMeta.lore = MetalLore
        Metal.itemMeta = MetalMeta
        val Fuel = ItemStack(
            Material.CHARCOAL, 1
        )
        val FuelMeta = Fuel.itemMeta
        val FuelName = "§f연료 §7(재료)"
        val FuelLore = ArrayList(
            listOf(
                "§8§o효율이 좋지 않은 고체 연료이다.."
            )
        )
        FuelMeta.setDisplayName(FuelName)
        FuelMeta.setCustomModelData(3)
        FuelMeta.addItemFlags(*ItemFlag.values())
        FuelMeta.lore = FuelLore
        Fuel.itemMeta = FuelMeta
        val MW = ItemStack(
            Material.PRISMARINE_CRYSTALS, 1
        )
        val MWMeta = MW.itemMeta
        val MWName = "§f기계 폐품 §7(재료)"
        val MWLore = ArrayList(
            listOf(
                "§8§o삐걱삐걱 대는 기계폐품이다.."
            )
        )
        MWMeta.setDisplayName(MWName)
        MWMeta.setCustomModelData(4)
        MWMeta.addItemFlags(*ItemFlag.values())
        MWMeta.lore = MWLore
        MW.itemMeta = MWMeta
        val EW = ItemStack(
            Material.MUSIC_DISC_11, 1
        )
        val EWMeta = EW.itemMeta
        val EWName = "§f전자 폐품 §7(재료)"
        val EWLore = ArrayList(
            listOf(
                "§8§o한때 최신식이었으나, 지금은 잘 쓰이지 않는 모양이다."
            )
        )
        EWMeta.setDisplayName(EWName)
        EWMeta.setCustomModelData(5)
        EWMeta.addItemFlags(*ItemFlag.values())
        EWMeta.lore = EWLore
        EW.itemMeta = EWMeta
        itemStacks.add(HardWood)
        itemStacks.add(Metal)
        itemStacks.add(Fuel)
        itemStacks.add(EW)
        itemStacks.add(MW)
        return itemStacks
    }

    val EmbersProcessesValue = HashMap<UUID, Int>()
    val EmbersFailed = HashMap<UUID, Boolean>()
    val EmbersSuccesses = HashMap<UUID, Boolean>()
    var EmbersIsUse = false
    var EmbersWhois: Player? = null
    var SabotageIsStart = false
    var SabotageStartReady = false
}