package com.bins.succession.game.vars.race

import java.util.HashMap
import java.util.UUID
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.ArrayList

class Race {
    fun getRaceFinalLocation(roomID: Int): ArrayList<Location>? {
        if (roomID == 1) {
            return ArrayList(Room1RaceFinal)
        }
        if (roomID == 2) {
            return ArrayList(Room2RaceFinal)
        }
        if (roomID == 3) {
            return ArrayList(Room3RaceFinal)
        }
        if (roomID == 4) {
            return ArrayList(Room4RaceFinal)
        }
        if (roomID == 5) {
            return ArrayList(Room5RaceFinal)
        }
        if (roomID == 6) {
            return ArrayList(Room6RaceFinal)
        }
        if (roomID == 7) {
            return ArrayList(Room7RaceFinal)
        }
        return if (roomID == 8) {
            ArrayList(Room8RaceFinal)
        } else null
    }

    fun getRandomRaceLocation(i: Int): ArrayList<Location>? {
        if (i == 1) {
            val array = ArrayList(Room1RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 2) {
            val array = ArrayList(Room2RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 3) {
            val array = ArrayList(Room3RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 4) {
            val array = ArrayList(Room4RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 5) {
            val array = ArrayList(Room5RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 6) {
            val array = ArrayList(Room6RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 7) {
            val array = ArrayList(Room7RaceSpawned)
            array.shuffle()
            return array
        }
        if (i == 8) {
            val array = ArrayList(Room8RaceSpawned)
            array.shuffle()
            return array
        }
        return null
    }

    fun ChannelInventory(str: String): Inventory? {
        when (str) {
            "수평" -> {
                val Channel1 = Channel1
                val Channel1Meta = Channel1.itemMeta
                Channel1Meta.setDisplayName("§a1채널")
                
                
                
                Channel1Meta.lore = if (Room1State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room1[0].name + "§7 vs " + Room1[1].name
                ) 
                else if (Room1.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room1[0].name
                )
                else listOf(
                    "§7수평 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수평모드 1채널에 참가합니다!"
                )
                
                
                
                Channel1.itemMeta = Channel1Meta
                val Channel2 = Channel2
                val Channel2Meta = Channel2.itemMeta
                Channel2Meta.setDisplayName("§a2채널")
                
                
                
                Channel2Meta.lore = if (Room2State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room2[0].name + "§7 vs " + Room2[1].name
                ) else if (Room2.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room2[0].name
                ) else listOf(
                    "§7수평 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수평모드 2채널에 참가합니다!"
                )
                
                
                Channel2.itemMeta = Channel2Meta
                val Channel3 = Channel3
                val Channel3Meta = Channel3.itemMeta
                Channel3Meta.setDisplayName("§a3채널")
                Channel3Meta.lore = if (Room3State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room3[0].name + "§7 vs " + Room3[1].name
                ) else if (Room3.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room3[0].name
                ) else listOf(
                    "§7수평 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수평모드 3채널에 참가합니다!"
                )
                Channel3.itemMeta = Channel3Meta
                val Channel4 = Channel4
                val Channel4Meta = Channel4.itemMeta
                Channel4Meta.setDisplayName("§a4채널")
                Channel4Meta.lore = if (Room4State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room4[0].name + "§7 vs " + Room4[1].name
                ) else if (Room4.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room4[0].name
                ) else listOf(
                    "§7수평 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수평모드 4채널에 참가합니다!"
                )
                Channel4.itemMeta = Channel4Meta
                HorizontalInventory.setItem(10, Channel1)
                HorizontalInventory.setItem(12, Channel2)
                HorizontalInventory.setItem(14, Channel3)
                HorizontalInventory.setItem(16, Channel4)
                return HorizontalInventory
            }
            "수직" -> {
                val Channel5 = Channel5
                val Channel5Meta = Channel5.itemMeta
                Channel5Meta.setDisplayName("§a1채널")
                Channel5Meta.lore = if (Room5State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room5[0].name + "§7 vs " + Room5[1].name
                ) else if (Room5.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room5[0].name
                ) else listOf(
                    "§7수직 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수직모드 1채널에 참가합니다!"
                )
                Channel5.itemMeta = Channel5Meta
                val Channel6 = Channel6
                val Channel6Meta = Channel6.itemMeta
                Channel6Meta.setDisplayName("§a2채널")
                Channel6Meta.lore = if (Room2State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room6[0].name + "§7 vs " + Room6[1].name
                ) else if (Room6.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room6[0].name
                ) else listOf(
                    "§7수직 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수직모드 2채널에 참가합니다!"
                )
                Channel6.itemMeta = Channel6Meta
                val Channel7 = Channel7
                val Channel7Meta = Channel7.itemMeta
                Channel7Meta.setDisplayName("§a3채널")
                Channel7Meta.lore = if (Room7State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room7[0].name + "§7 vs " + Room7[1].name
                ) else if (Room7.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room7[0].name
                ) else listOf(
                    "§7수직 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수직모드 3채널에 참가합니다!"
                )
                Channel7.itemMeta = Channel7Meta
                val Channel8 = Channel8
                val Channel8Meta = Channel8.itemMeta
                Channel8Meta.setDisplayName("§a4채널")
                Channel8Meta.lore = if (Room8State) listOf(
                    "§c게임중...",
                    "§7게임이 진행중입니다.",
                    "§7",
                    "§7" + Room8[0].name + "§7 vs " + Room8[1].name
                ) else if (Room8.isNotEmpty()) listOf(
                    "§6대기 중",
                    "§7게임이 대기중입니다",
                    "§7",
                    "§7대기 인원: " + Room8[0].name
                ) else listOf(
                    "§7수직 모드에 참가합니다.",
                    "§7",
                    "§e클릭시 수직모드 4채널에 참가합니다!"
                )
                Channel8.itemMeta = Channel8Meta
                VerticalInventory.setItem(10, Channel5)
                VerticalInventory.setItem(12, Channel6)
                VerticalInventory.setItem(14, Channel7)
                VerticalInventory.setItem(16, Channel8)
                return VerticalInventory
            }
        }
        return null
    }

    private val Room1RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 3005.5, 67.0, 3020.5),
            Location(Bukkit.getWorld("world"), 2995.5, 67.0, 3020.5)
        )
    )
    private val Room2RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2979.5, 67.0, 3020.5),
            Location(Bukkit.getWorld("world"), 2969.5, 67.0, 3020.5)
        )
    )
    private val Room3RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2954.5, 67.0, 3020.5),
            Location(Bukkit.getWorld("world"), 2944.5, 67.0, 3020.5)
        )
    )
    private val Room4RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2928.5, 67.0, 3020.5),
            Location(Bukkit.getWorld("world"), 2918.5, 67.0, 3020.5)
        )
    )
    private val Room5RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 3007.5, 58.0, 2981.5),
            Location(Bukkit.getWorld("world"), 2997.5, 58.0, 2981.5)
        )
    )
    private val Room6RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2983.5, 58.0, 2981.5),
            Location(Bukkit.getWorld("world"), 2973.5, 58.0, 2981.5)
        )
    )
    private val Room7RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2959.5, 58.0, 2981.5),
            Location(Bukkit.getWorld("world"), 2949.5, 58.0, 2981.5)
        )
    )
    private val Room8RaceSpawned = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2935.5, 58.0, 2981.5),
            Location(Bukkit.getWorld("world"), 2925.5, 58.0, 2981.5)
        )
    )
    private val Room1RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 3005.5, 67.5, 3096.5),
            Location(Bukkit.getWorld("world"), 2995.5, 67.5, 3096.5)
        )
    )
    private val Room2RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2979.5, 67.5, 3096.5),
            Location(Bukkit.getWorld("world"), 2969.5, 67.5, 3096.5)
        )
    )
    private val Room3RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2954.5, 67.0, 3096.5),
            Location(Bukkit.getWorld("world"), 2944.5, 67.0, 3096.5)
        )
    )
    private val Room4RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2928.5, 67.0, 3096.5),
            Location(Bukkit.getWorld("world"), 2918.5, 67.0, 3096.5)
        )
    )
    private val Room5RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 3004.5, 90.0, 2987.5),
            Location(Bukkit.getWorld("world"), 3000.5, 90.0, 2987.5)
        )
    )
    private val Room6RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2980.5, 90.0, 2987.5),
            Location(Bukkit.getWorld("world"), 2976.5, 90.0, 2987.5)
        )
    )
    private val Room7RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2956.5, 90.0, 2987.5),
            Location(Bukkit.getWorld("world"), 2952.5, 90.0, 2987.5)
        )
    )
    private val Room8RaceFinal = ArrayList(
        listOf(
            Location(Bukkit.getWorld("world"), 2932.5, 90.0, 2987.5),
            Location(Bukkit.getWorld("world"), 2932.5, 90.0, 2987.5)
        )
    )
    private val Channel1 = ItemStack(
        if (Room1State) Material.RED_BANNER else if (Room1.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room1.isNotEmpty()) 1 else Room1.size
    )
    private val Channel2 = ItemStack(
        if (Room2State) Material.RED_BANNER else if (Room2.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room2.isNotEmpty()) 1 else Room2.size
    )
    private val Channel3 = ItemStack(
        if (Room3State) Material.RED_BANNER else if (Room3.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room3.isNotEmpty()) 1 else Room3.size
    )
    private val Channel4 = ItemStack(
        if (Room4State) Material.RED_BANNER else if (Room4.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room4.isNotEmpty()) 1 else Room4.size
    )
    private val Channel5 = ItemStack(
        if (Room5State) Material.RED_BANNER else if (Room5.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room5.isNotEmpty()) 1 else Room5.size
    )
    private val Channel6 = ItemStack(
        if (Room6State) Material.RED_BANNER else if (Room6.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room6.isNotEmpty()) 1 else Room6.size
    )
    private val Channel7 = ItemStack(
        if (Room7State) Material.RED_BANNER else if (Room7.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room7.isNotEmpty()) 1 else Room7.size
    )
    private val Channel8 = ItemStack(
        if (Room8State) Material.RED_BANNER else if (Room8.isNotEmpty()) Material.WHITE_BANNER else Material.GREEN_BANNER,
        if (Room8.isNotEmpty()) 1 else Room8.size
    )

    companion object {
        fun RandomBlocks(): ItemStack? {
            val ItemStacks = ArrayList<ItemStack?>()
            val end_stone = ItemStack(Material.END_STONE)
            val end_stone_meta = end_stone.itemMeta
            end_stone_meta.setDisplayName("§a무중력 블록")
            end_stone_meta.lore = listOf("§7설치시 상대방을 띄우고 느린 낙하를 겁니다", "", "§7§oI believe I can fly~")
            end_stone.itemMeta = end_stone_meta
            val white_wool = ItemStack(Material.WHITE_WOOL)
            val white_wool_meta = white_wool.itemMeta
            white_wool_meta.setDisplayName("§a정지 블록")
            white_wool_meta.lore = listOf("§7설치시 상대방을 해당 블록으로 가둡니다")
            white_wool.itemMeta = white_wool_meta
            val oak_wood = ItemStack(Material.OAK_WOOD)
            val oak_wood_meta = oak_wood.itemMeta
            oak_wood_meta.setDisplayName("§a정지 블록")
            oak_wood_meta.lore = listOf("§7설치시 상대방을 해당 블록으로 가둡니다")
            oak_wood.itemMeta = oak_wood_meta
            val glass = ItemStack(Material.GLASS)
            val glass_meta = glass.itemMeta
            glass_meta.setDisplayName("§a헤롱헤롱 블록")
            glass_meta.lore = listOf("§7설치시 상대방에게 멀미를 겁니다", "", "§7§o여..여기가 어디오..", "", "§7§o아, 안심하세요 천국입니다.")
            glass.itemMeta = glass_meta
            val grass_block = ItemStack(Material.GRASS_BLOCK)
            val grass_block_meta = grass_block.itemMeta
            grass_block_meta.setDisplayName("§a초심 블록")
            grass_block_meta.lore = listOf("§7설치시 상대방을 시작 지점으로 돌려보냅니다", "", "§7§o지우야 태초마을이야!")
            grass_block.itemMeta = grass_block_meta
            val slime_block = ItemStack(Material.SLIME_BLOCK)
            val slime_block_meta = slime_block.itemMeta
            slime_block_meta.setDisplayName("§a개구리 블록")
            slime_block_meta.lore = listOf("§7설치시 상대방이 개구리마냥 위 아래로 날뜁니다!")
            slime_block.itemMeta = slime_block_meta
            val heart = ItemStack(Material.NETHER_WART_BLOCK)
            val heart_meta = heart.itemMeta
            heart_meta.setDisplayName("§c하트 블록")
            heart_meta.lore = listOf("§7설치시 하트가 하나 늘어납니다", "", "§7§o이거 햝아 보신분?", "§7§o딸기맛 납니다")
            heart.itemMeta = heart_meta
            ItemStacks.add(end_stone)
            ItemStacks.add(white_wool)
            ItemStacks.add(heart)
            ItemStacks.add(slime_block)
            ItemStacks.add(glass)
            ItemStacks.add(grass_block)
            ItemStacks.add(oak_wood)
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.shuffle()
            return ItemStacks[0]
        }

        fun VerticalRandomBlocks(): ItemStack? {
            val ItemStacks = ArrayList<ItemStack?>()
            val white_wool = ItemStack(Material.WHITE_WOOL)
            val white_wool_meta = white_wool.itemMeta
            white_wool_meta.setDisplayName("§a정지 블록")
            white_wool_meta.lore = listOf("§7설치시 상대방을 해당 블록으로 가둡니다")
            white_wool.itemMeta = white_wool_meta
            val oak_wood = ItemStack(Material.OAK_WOOD)
            val oak_wood_meta = oak_wood.itemMeta
            oak_wood_meta.setDisplayName("§a정지 블록")
            oak_wood_meta.lore = listOf("§7설치시 상대방을 해당 블록으로 가둡니다")
            oak_wood.itemMeta = oak_wood_meta
            val glass = ItemStack(Material.GLASS)
            val glass_meta = glass.itemMeta
            glass_meta.setDisplayName("§a헤롱헤롱 블록")
            glass_meta.lore = listOf("§7설치시 상대방에게 멀미를 겁니다", "", "§7여..여기가 어디오..", "", "§7아, 안심하세요 천국입니다.")
            glass.itemMeta = glass_meta
            val grass_block = ItemStack(Material.GRASS_BLOCK)
            val grass_block_meta = grass_block.itemMeta
            grass_block_meta.setDisplayName("§a초심 블록")
            grass_block_meta.lore = listOf("§7설치시 상대방을 시작 지점으로 돌려보냅니다")
            grass_block.itemMeta = grass_block_meta
            val heart = ItemStack(Material.NETHER_WART_BLOCK)
            val heart_meta = heart.itemMeta
            heart_meta.setDisplayName("§c하트 블록")
            heart_meta.lore = listOf("§7설치시 하트가 하나 늘어납니다", "", "§7§o이거 햝아 보신분?", "§7§o딸기맛 납니다")
            heart.itemMeta = heart_meta
            ItemStacks.add(white_wool)
            ItemStacks.add(heart)
            ItemStacks.add(glass)
            ItemStacks.add(grass_block)
            ItemStacks.add(oak_wood)
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_PLANKS))
            ItemStacks.add(ItemStack(Material.OAK_SLAB))
            ItemStacks.add(ItemStack(Material.OAK_STAIRS))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.add(ItemStack(Material.OAK_FENCE))
            ItemStacks.add(ItemStack(Material.GRANITE_WALL))
            ItemStacks.add(ItemStack(Material.LADDER))
            ItemStacks.shuffle()
            return ItemStacks[0]
        }

        val RaceInventory = Bukkit.createInventory(null, 27, "경주 대전")
        private val HorizontalInventory = Bukkit.createInventory(null, 27, "수평")
        private val VerticalInventory = Bukkit.createInventory(null, 27, "수직")
        var Raceyou = HashMap<UUID, Player>()
        var RaceyouDisplayName = HashMap<UUID, String>()
        var RaceRoomID = HashMap<UUID, Int>()
        var RacePlaying = HashMap<UUID, Boolean>()
        var RaceImInChannel = HashMap<UUID, Boolean>()
        var Hearts = HashMap<UUID, Int>()
        var MyAreaLocation = HashMap<UUID, Location>()
        var PlacedBlockLocation = HashMap<UUID, ArrayList<Location>>()
        var Room1 = ArrayList<Player>()
        var Room2 = ArrayList<Player>()
        var Room3 = ArrayList<Player>()
        var Room4 = ArrayList<Player>()
        var Room5 = ArrayList<Player>()
        var Room6 = ArrayList<Player>()
        var Room7 = ArrayList<Player>()
        var Room8 = ArrayList<Player>()
        var Room1State = false
        var Room2State = false
        var Room3State = false
        var Room4State = false
        var Room5State = false
        var Room6State = false
        var Room7State = false
        var Room8State = false
        var Room1SubState = false
        var Room2SubState = false
        var Room3SubState = false
        var Room4SubState = false
        var Room5SubState = false
        var Room6SubState = false
        var Room7SubState = false
        var Room8SubState = false
    }
}