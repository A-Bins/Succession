package com.bins.succession.event


import com.bins.succession.game.manager.RaceManager
import com.bins.succession.game.manager.SabotageManager
import com.bins.succession.game.manager.StreetManager
import com.bins.succession.game.vars.race.Race
import com.bins.succession.game.vars.Sabotage.Sabotage
import com.bins.succession.game.vars.Street.Street1vs1
import com.bins.succession.game.vars.Street.StreetSuv
import com.bins.succession.info.data.User
import com.bins.succession.menu.Menu
import net.md_5.bungee.api.ChatColor
import net.minecraft.server.v1_16_R3.EntityHuman
import net.minecraft.server.v1_16_R3.EntityItem
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.ClickType
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemFlag
import org.bukkit.inventory.ItemStack

class InventoryClick : Listener {
    @EventHandler
    fun click(e: InventoryClickEvent) {
        if (Street1vs1.queueList.contains(e.whoClicked.name + ", " + Street1vs1.queueType[e.whoClicked.uniqueId]) or (Race.RaceImInChannel[
                e.whoClicked.uniqueId
            ]!!)
        ) {
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.type == Material.STRUCTURE_VOID) {
                e.isCancelled = true
            }
        }
        val p = e.whoClicked as Player
        val title = e.view.title
        if (title == "시가전") {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            if (e.currentItem!!.itemMeta.displayName.contains("1 vs 1")) {

                //1vs1 전용
                val normal = ItemStack(Material.DIAMOND_SWORD)
                val normalMeta = normal.itemMeta
                normalMeta.setDisplayName("§6일반 무기 대전")
                normalMeta.lore = listOf(
                    "§74가지 무작위 무기를 가지고 시작합니다.",
                    "",
                    "§e클릭하여 대기열에 참가합니다."
                )
                normalMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                normal.itemMeta = normalMeta
                Street1vs1.street1v1inv.setItem(13, normal)
                val single = ItemStack(Material.GOLDEN_SWORD)
                val singleMeta = normal.itemMeta
                singleMeta.setDisplayName("§6단일 무기 대전")
                singleMeta.lore = listOf(
                    "§71가지 무작위 무기를 가지고 시작합니다.",
                    "",
                    "§e클릭하여 대기열에 참가합니다."
                )
                singleMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                single.itemMeta = singleMeta
                Street1vs1.street1v1inv.setItem(11, single)
                val combo = ItemStack(Material.IRON_SWORD)
                val comboMeta = normal.itemMeta
                comboMeta.setDisplayName("§6콤보 무기 대전")
                comboMeta.lore = listOf(
                    "§7시너지 효과가 있는 무기 세트를 가지고 시작합니다.",
                    "",
                    "§e클릭하여 대기열에 참가합니다."
                )
                comboMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                combo.itemMeta = comboMeta
                Street1vs1.street1v1inv.setItem(15, combo)
                p.openInventory(Street1vs1.street1v1inv)
                return
            }
            if (!e.currentItem!!.itemMeta.displayName.contains("개인전")) {
                return
            }
            //서바이벌 개인전 전용
            val channel1 = ItemStack(
                if (StreetSuv.StreetSingleWarStateChannel1) Material.RED_BANNER else Material.LIME_BANNER,
                if (StreetSuv.StreetSingleWarChannel1.size == 0) 1 else StreetSuv.StreetSingleWarChannel1.size
            )
            val channel1Meta = channel1.itemMeta
            channel1Meta.setDisplayName(if (StreetSuv.StreetSingleWarStateChannel1) "§a1채널: §c게임 진행중" else "§a1채널: §6게임 대기중")
            channel1Meta.lore = if (StreetSuv.StreetSingleWarStateChannel1) listOf(
                "§71번 채널에서 게임을 진행하는 중입니다",
                "",
                "§7현재 " + StreetSuv.StreetSingleWarChannel1.size + "/8 명이 게임 중입니다"
            ) else listOf(
                "§71번 채널에서 게임을 진행합니다.",
                "",
                "§e클릭시 참여합니다!",
                "",
                "§7현재 " + StreetSuv.StreetSingleWarChannel1.size + "/8 명이 접속 중입니다"
            )
            channel1.itemMeta = channel1Meta
            StreetSuv.StreetSingleWarChannelInv.setItem(12, channel1)
            //Variables.StreetSingleWarStateChannel2 ? Material.RED_BANNER : Material.LIME_BANNER
            val channel2 = ItemStack(
                Material.BARRIER,
                if (StreetSuv.StreetSingleWarChannel2.size == 0) 1 else StreetSuv.StreetSingleWarChannel2.size
            )
            val channel2Meta = channel2.itemMeta
            //            Variables.StreetSingleWarStateChannel2 ? "§a2채널: §c게임 진행중" : "§a2채널: §6게임 대기중"
            channel2Meta.setDisplayName("§c준비중!")
            //            channel2Meta.setLore(
//                    Variables.StreetSingleWarStateChannel2 ?
//                            Arrays.asList(
//                                    "§72번 채널에서 게임을 진행하는 중입니다"
//                            )
//                            :
//                            Arrays.asList(
//                                    "§72번 채널에서 게임을 진행합니다.",
//                                    "",
//                                    "§e클릭시 참여합니다!"
//                    )
//            );
            channel2.itemMeta = channel2Meta
            StreetSuv.StreetSingleWarChannelInv.setItem(14, channel2)
            p.openInventory(StreetSuv.StreetSingleWarChannelInv)
        }
        else if (title == "채널") {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            if (!e.currentItem!!.itemMeta.displayName.contains("채널")) {
                return
            }
            if (StreetSuv.StreetSingleWarChannel1.contains(p) or StreetSuv.StreetSingleWarChannel2.contains(p)) {
                p.sendMessage("§c이미 참가하셨습니다!")
                p.closeInventory()
                return
            }
            if (e.currentItem!!.itemMeta.displayName.contains("1채널")) {
                if (StreetSuv.StreetSingleWarStateChannel1) {
                    p.sendMessage("§c이미 게임이 시작했습니다!")
                    return
                }
                p.closeInventory()
                StreetSuv.StreetSingleWarChannel1.add(p)
                p.teleport(Location(Bukkit.getWorld("world"), 1129.5, 173.0, 712.5))
                Bukkit.broadcastMessage("§7< §a1채널 §6서바이벌 개인전 §7> §7" + p.name + "§f님이 §a참여§7했습니다 §7(" + StreetSuv.StreetSingleWarChannel1.size + "/8)")
            } else if (e.currentItem!!.itemMeta.displayName.contains("2채널")) {
                if (StreetSuv.StreetSingleWarStateChannel2) {
                    p.sendMessage("§c이미 게임이 시작했습니다!")
                    return
                }
                p.closeInventory()
                StreetSuv.StreetSingleWarChannel2.add(p)
                Bukkit.broadcastMessage("§7< §c2채널 §6서바이벌 개인전 §7> §7" + p.name + "§f님이 §a참여§7했습니다 §7(" + StreetSuv.StreetSingleWarChannel2.size + "/8)")
            }
            if (StreetSuv.StreetSingleWarChannel1.size >= 4) {
                StreetManager.StreetSingleWarStart("1")
            }
            if (StreetSuv.StreetSingleWarChannel2.size >= 4) {
                StreetManager.StreetSingleWarStart("2")
            }
        }
        else if (title.contains("vs")) {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            if (e.currentItem!!.itemMeta.displayName.contains("일반")
                || e.currentItem!!.itemMeta.displayName.contains("콤보")
                || e.currentItem!!.itemMeta.displayName.contains("단일")
            ) {
                val name = e.currentItem!!.itemMeta.displayName
                val value =
                    if (name.contains("일반")) "일반" else if (name.contains("콤보")) "콤보" else if (name.contains("단일")) "단일" else "ERROR"
                if (value == "ERROR") return
                if (Street1vs1.normalRoom1.contains(p)) return
                if (Street1vs1.normalRoom2.contains(p)) return
                if (Street1vs1.singleRoom1.contains(p)) return
                if (Street1vs1.singleRoom2.contains(p)) return
                if (Street1vs1.comboRoom1.contains(p)) return
                if (Street1vs1.comboRoom2.contains(p)) return
                when (val result = value + "") {
                    "콤보" -> if (!Street1vs1.comboRoomState1) {
                        if (Street1vs1.comboRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.comboRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.comboRoom2[0]
                            Street1vs1.you[Street1vs1.comboRoom2[0].uniqueId] = p
                            Street1vs1.comboRoomState2 = true
                            Street1vs1.comboRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.comboRoom2[0], p, result, 2)
                            return
                        } else if (Street1vs1.comboRoom1.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 1
                            Street1vs1.youRoomID[Street1vs1.comboRoom1[0].uniqueId] = 1
                            Street1vs1.you[p.uniqueId] = Street1vs1.comboRoom1[0]
                            Street1vs1.you[Street1vs1.comboRoom1[0].uniqueId] = p
                            Street1vs1.comboRoomState1 = true
                            Street1vs1.comboRoom1.add(p)
                            StreetManager.Street1v1Start(Street1vs1.comboRoom1[0], p, result, 1)
                            return
                        }
                        Street1vs1.comboRoom1.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else if (!Street1vs1.comboRoomState2) {
                        if (Street1vs1.comboRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.comboRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.comboRoom2[0]
                            Street1vs1.you[Street1vs1.comboRoom2[0].uniqueId] = p
                            Street1vs1.comboRoomState2 = true
                            Street1vs1.comboRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.comboRoom2[0], p, result, 2)
                            return
                        }
                        Street1vs1.comboRoom2.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else {
                        Street1vs1.comboWait.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    }
                    "일반" -> if (!Street1vs1.normalRoomState1) {
                        if (Street1vs1.normalRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.normalRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.normalRoom2[0]
                            Street1vs1.you[Street1vs1.normalRoom2[0].uniqueId] = p
                            Street1vs1.normalRoomState2 = true
                            Street1vs1.normalRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.normalRoom2[0], p, result, 2)
                            return
                        } else if (Street1vs1.normalRoom1.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 1
                            Street1vs1.youRoomID[Street1vs1.normalRoom1[0].uniqueId] = 1
                            Street1vs1.you[p.uniqueId] = Street1vs1.normalRoom1[0]
                            Street1vs1.you[Street1vs1.normalRoom1[0].uniqueId] = p
                            Street1vs1.normalRoomState1 = true
                            Street1vs1.normalRoom1.add(p)
                            StreetManager.Street1v1Start(Street1vs1.normalRoom1[0], p, result, 1)
                            return
                        }
                        Street1vs1.normalRoom1.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else if (!Street1vs1.normalRoomState2) {
                        if (Street1vs1.normalRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.normalRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.normalRoom2[0]
                            Street1vs1.you[Street1vs1.normalRoom2[0].uniqueId] = p
                            Street1vs1.normalRoomState2 = true
                            Street1vs1.normalRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.normalRoom2[0], p, result, 2)
                            return
                        }
                        Street1vs1.normalRoom2.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else {
                        Street1vs1.normalWait.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    }
                    "단일" -> if (!Street1vs1.singleRoomState1) {
                        if (Street1vs1.singleRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.singleRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.singleRoom2[0]
                            Street1vs1.you[Street1vs1.singleRoom2[0].uniqueId] = p
                            Street1vs1.singleRoomState2 = true
                            Street1vs1.singleRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.singleRoom2[0], p, result, 2)
                            return
                        } else if (Street1vs1.singleRoom1.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 1
                            Street1vs1.youRoomID[Street1vs1.singleRoom1[0].uniqueId] = 1
                            Street1vs1.you[p.uniqueId] = Street1vs1.singleRoom1[0]
                            Street1vs1.you[Street1vs1.singleRoom1[0].uniqueId] = p
                            Street1vs1.singleRoomState1 = true
                            Street1vs1.singleRoom1.add(p)
                            StreetManager.Street1v1Start(Street1vs1.singleRoom1[0], p, result, 1)
                            return
                        }
                        Street1vs1.singleRoom1.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else if (!Street1vs1.singleRoomState2) {
                        if (Street1vs1.singleRoom2.isNotEmpty()) {
                            Street1vs1.youRoomID[p.uniqueId] = 2
                            Street1vs1.youRoomID[Street1vs1.singleRoom2[0].uniqueId] = 2
                            Street1vs1.you[p.uniqueId] = Street1vs1.singleRoom2[0]
                            Street1vs1.you[Street1vs1.singleRoom2[0].uniqueId] = p
                            Street1vs1.singleRoomState2 = true
                            Street1vs1.singleRoom2.add(p)
                            StreetManager.Street1v1Start(Street1vs1.singleRoom2[0], p, result, 2)
                            return
                        }
                        Street1vs1.singleRoom2.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    } else {
                        Street1vs1.singleWait.add(p)
                        StreetManager.Street1v1Hotbar(p)
                    }
                }
                p.closeInventory()
                Street1vs1.queueList.add(p.name + ", " + value)
                Street1vs1.queueType[p.uniqueId] = value
            }
        }
        else if (title.contains("경주")) {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            if (e.currentItem!!.itemMeta.displayName.contains("수평")
                || e.currentItem!!.itemMeta.displayName.contains("수직")
            ) {
                val name = e.currentItem!!.itemMeta.displayName
                val value = if (name.contains("수평")) "수평" else "수직"
                p.openInventory(Race().ChannelInventory(value)!!)
            }
        }
        else if (title.contains("수평") or title.contains("수직")) {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            if (Race.RaceImInChannel[p.uniqueId]!!) {
                return
            }
            if (e.currentItem!!.itemMeta.displayName.contains("채널")) {
                val name = e.currentItem!!.itemMeta.displayName
                var roomID =
                    ChatColor.stripColor(name.replace("채널", ""))
                        .toInt()
                roomID =
                    if (title.contains("수직")) if (roomID == 1) 5 else if (roomID == 2) 6 else if (roomID == 3) 7 else if (roomID == 4) 8 else 0 else roomID
                when (roomID) {
                    1 -> {
                        if (!Race.Room1State) {
                            if (Race.Room1.isNotEmpty()) {
                                val you: Player = Race.Room1[0]
                                Race.Room1.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room1.add(p)
                        }
                        return
                    }
                    2 -> {
                        if (!Race.Room2State) {
                            if (Race.Room2.isNotEmpty()) {
                                val you: Player = Race.Room2[0]
                                Race.Room2.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room2.add(p)
                        }
                        return
                    }
                    3 -> {
                        if (!Race.Room3State) {
                            if (Race.Room3.isNotEmpty()) {
                                val you: Player = Race.Room3[0]
                                Race.Room3.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room3.add(p)
                        }
                        return
                    }
                    4 -> {
                        if (!Race.Room4State) {
                            if (Race.Room4.isNotEmpty()) {
                                val you: Player = Race.Room4[0]
                                Race.Room4.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room4.add(p)
                        }
                        return
                    }
                    5 -> {
                        if (!Race.Room5State) {
                            if (Race.Room5.isNotEmpty()) {
                                val you: Player = Race.Room5[0]
                                Race.Room5.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room5.add(p)
                        }
                        return
                    }
                    6 -> {
                        if (!Race.Room6State) {
                            if (Race.Room6.isNotEmpty()) {
                                val you: Player = Race.Room6[0]
                                Race.Room6.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room6.add(p)
                        }
                        return
                    }
                    7 -> {
                        if (!Race.Room7State) {
                            if (Race.Room7.isNotEmpty()) {
                                val you: Player = Race.Room7[0]
                                Race.Room7.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room7.add(p)
                        }
                        return
                    }
                    8 -> {
                        if (!Race.Room8State) {
                            if (Race.Room8.isNotEmpty()) {
                                val you: Player = Race.Room8[0]
                                Race.Room8.add(p)
                                Race.RaceRoomID[p.uniqueId] = roomID
                                Race.RaceImInChannel[p.uniqueId] = true
                                RaceManager.RaceStart(title, p, you, roomID)
                                return
                            }
                            Race.Room8.add(p)
                        }
                        return
                    }
                }
                Race.RaceRoomID[p.uniqueId] = roomID
                Race.RaceImInChannel[p.uniqueId] = true
                RaceManager.RaceHotbar(p)
                p.closeInventory()
            }
        }
        else if (title.contains("게임 메뉴")) {
            e.isCancelled = true
            if (e.currentItem == null) {
                return
            }
            if (e.currentItem!!.itemMeta == null) {
                return
            }
            val name = e.currentItem!!.itemMeta.displayName
            when {
                name.contains("경주") -> {
                    val vert = ItemStack(Material.CHAINMAIL_BOOTS)
                    val vertMeta = vert.itemMeta
                    vertMeta.setDisplayName("§a수직 모드")
                    vertMeta.lore = listOf(
                        "§8캐주얼 게임",
                        "",
                        "§7수직으로 놓여져 있는 완주 지점까지",
                        "§7먼저 도달하면 승리 합니다",
                        "",
                        "§e클릭하여 대기열에 참여합니다"
                    )
                    vertMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    vert.itemMeta = vertMeta
                    Race.RaceInventory.setItem(14, vert)
                    val horiz = ItemStack(Material.LEATHER_BOOTS)
                    val horizMeta = horiz.itemMeta
                    horizMeta.setDisplayName("§a수평 모드")
                    horizMeta.lore = listOf(
                        "§8캐주얼 게임",
                        "",
                        "§7수평으로 놓여져 있는 완주 지점까지",
                        "§7먼저 도달하면 승리 합니다",
                        "",
                        "§e클릭하여 대기열에 참여합니다"
                    )
                    horizMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    horiz.itemMeta = horizMeta
                    Race.RaceInventory.setItem(12, horiz)
                    val intro = ItemStack(Material.KNOWLEDGE_BOOK)
                    val introMeta = intro.itemMeta
                    introMeta.setDisplayName("§6게임 설명")
                    introMeta.lore = listOf(
                        "§7경주는 상대방보다 더 빠르게 완주 지점까지",
                        "§7블록을 가지고 길을 이으며 도달하면",
                        "§7승리 할 수 있는 게임입니다."
                    )
                    intro.itemMeta = introMeta
                    Race.RaceInventory.setItem(22, intro)
                    p.openInventory(Race.RaceInventory)
                }
                name.contains("시가전") -> {
                    val singleGameStart = ItemStack(Material.IRON_SWORD)
                    val singleGameStartMeta = singleGameStart.itemMeta
                    singleGameStartMeta.setDisplayName("§a서바이벌 개인전")
                    singleGameStartMeta.lore = listOf(
                        "§8캐주얼 게임",
                        "",
                        "§7무작위 무기를 받고 도심지에서",
                        "§7다른 상대를 처치하거나 다른 상대의 공격으로부터",
                        "§7버텨 제한 시간 내까지 생존하면 승리합니다",
                        "",
                        "§e클릭하여 대기실에 입장합니다"
                    )
                    singleGameStartMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    singleGameStart.itemMeta = singleGameStartMeta
                    StreetSuv.StreetWarJoin.setItem(14, singleGameStart)
                    val pvpGameStart = ItemStack(Material.IRON_SWORD)
                    val pvpGameStartMeta = pvpGameStart.itemMeta
                    pvpGameStartMeta.setDisplayName("§a1 vs 1 대전")
                    pvpGameStartMeta.lore = listOf(
                        "§8캐주얼 게임",
                        "",
                        "§7조건에 맞게 무작위 무기를 받고 제한 시간내에",
                        "§7상대방을 쓰러뜨리면 승리합니다",
                        "",
                        "§e클릭하여 게임모드를 살펴봅니다"
                    )
                    pvpGameStartMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES)
                    pvpGameStart.itemMeta = pvpGameStartMeta
                    StreetSuv.StreetWarJoin.setItem(12, pvpGameStart)
                    val intro = ItemStack(Material.KNOWLEDGE_BOOK)
                    val introMeta = intro.itemMeta
                    introMeta.setDisplayName("§6게임 설명")
                    introMeta.lore = listOf(
                        "§7시가전은 무작위로 주어지는 무기를 가지고",
                        "§7각 모드의 규칙에 따라 생존하거나, 적을 처치하면",
                        "§7승리할 수 있는 게임입니다."
                    )
                    intro.itemMeta = introMeta
                    StreetSuv.StreetWarJoin.setItem(22, intro)
                    p.openInventory(StreetSuv.StreetWarJoin)
                }
                name.contains("사보추어") -> {
                    p.openInventory(Sabotage.SabotageInventory)
                }
            }
        }
        else if (title.contains("사보추어")) {
            e.isCancelled = true
            if (e.currentItem == null) return
            if (e.currentItem!!.itemMeta == null) return
            if (!e.currentItem!!.itemMeta.displayName.contains("사보추어")) return
            if (Sabotage.SabotagePlayers.contains(p)) return
            if (Sabotage.SabotageStartReady) return
            if (Sabotage.SabotagePlayers.size == 8) return
            Sabotage.SabotagePlayers.add(p)
            Sabotage.SabotageReadying[p.uniqueId] = true
            User.IsPlaying[p.uniqueId] = true
            p.teleport(Sabotage.SabotageReady)
            if (Sabotage.SabotagePlayers.size == 8) {
                SabotageManager.SabotageStart()
            }
        }
        else if (title.contains("가방")) {
            e.isCancelled = true
            if (e.currentItem == null) return
            if (e.currentItem!!.itemMeta == null) return
            if (e.currentItem!!.itemMeta.customModelData == 0) return
            if (e.click == ClickType.SHIFT_LEFT || e.click == ClickType.RIGHT || e.click == ClickType.LEFT) {
                val custom = e.currentItem!!.itemMeta.customModelData
                val amount = if (e.click == ClickType.SHIFT_LEFT) if (e.currentItem!!
                        .amount % 2 == 0
                ) e.currentItem!!.amount / 2 else (e.currentItem!!
                    .amount - 1) / 2 + 1 else if (e.click == ClickType.RIGHT) e.currentItem!!
                    .amount else if (e.click == ClickType.LEFT) 1 else 0
                val name =
                    if (custom == 1) "HardWood" else if (custom == 2) "Metal" else if (custom == 3) "Fuel" else if (custom == 4) "MW" else if (custom == 5) "EW" else "NaS"
                val item = e.currentItem
                item!!.amount = amount
                Sabotage.Items[p.uniqueId]!![name] = Sabotage.Items[p.uniqueId]!![name]!! - amount
                item.lore = listOf(item.lore!![0])
                val entityHuman: EntityHuman = (p as CraftPlayer).handle
                val a: EntityItem = entityHuman.drop(CraftItemStack.asNMSCopy(item), false)!!
                a.glowing = true
                p.swingMainHand()
                p.closeInventory()
            }
        }
        else if (title == "메뉴") {
            e.isCancelled = true
            if (e.currentItem == null) return
            if (e.currentItem!!.itemMeta == null) return
            val main = Menu(p)
            if (e.currentItem!!.itemMeta.displayName.contains("게임 메뉴")) {
                p.openInventory(main.toGameMenu())
            } else if (e.currentItem!!.itemMeta.displayName.contains("통계")) {
                p.openInventory(main.toStatics())
            }
        }
        else if (title.contains("통계")) {

            e.isCancelled = true
        }
    }
}