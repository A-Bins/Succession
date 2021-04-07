package com.bins.succession.utilities

import com.bins.succession.Succession
import com.bins.succession.info.rank.Type
import com.bins.succession.utilities.util.bb
import com.google.gson.JsonArray
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.inventory.ItemStack
import org.bukkit.util.io.BukkitObjectInputStream
import org.bukkit.util.io.BukkitObjectOutputStream
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder
import java.io.*
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*
import kotlin.collections.HashMap

class utilJson {
    private fun serializeItemStack(item: ItemStack): String? {
        val outputStream = ByteArrayOutputStream()
        try {
            val dataOutput = BukkitObjectOutputStream(outputStream)
            dataOutput.writeObject(item)
            dataOutput.close()
            return Base64Coder.encodeLines(outputStream.toByteArray())
        } catch (exception: IOException) {
            exception.printStackTrace()
        }
        return null
    }

    private fun deserializeItemStack(item: String): ItemStack? {
        val inputStream = ByteArrayInputStream(Base64Coder.decodeLines(item))
        try {
            val dataInput = BukkitObjectInputStream(inputStream)
            dataInput.close()
            return dataInput.readObject() as ItemStack
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
        return null
    }

    companion object {
        fun loadInt(agora: Succession, hash: HashMap<UUID, Int>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toInt()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadloc(agora: Succession, hash: HashMap<UUID, Location>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        hash[UUID.fromString(key.toString() + "")] = Location(
                            Bukkit.getWorld(
                                value.toString().split("name=").toTypedArray()[1].split("}").toTypedArray()[0]
                            ),
                            value.toString().split("x=").toTypedArray()[1].split(",")
                                .toTypedArray()[0].toDouble(),
                            value.toString().split("y=").toTypedArray()[1].split(",")
                                .toTypedArray()[0].toDouble(),
                            value.toString().split("z=").toTypedArray()[1].split(",")
                                .toTypedArray()[0].toDouble()
                        )
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadDouble(agora: Succession, hash: HashMap<UUID, Double>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach{ key: Any, value: Any ->
                        hash[UUID.fromString(key.toString() + "")] = (value.toString() + "").toDouble()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadString(agora: Succession, hash: HashMap<UUID, String>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        try {
                            hash[UUID.fromString(key.toString() + "")] = "$value"
                        } catch (e: UnsupportedEncodingException) {
                            e.printStackTrace()
                        }
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun save(agora: Succession, hash: HashMap<*, *>, jsonname: String) {
            val data = JSONObject()
            hash.forEach { (key: Any, value: Any) -> data[key] = "" + value }
            try {
                val UniOutput = BufferedWriter(
                    OutputStreamWriter(
                        FileOutputStream(File(agora.dataFolder, "$jsonname.json").path),
                        "EUC-KR"
                    )
                )
                UniOutput.write("$data")
                UniOutput.flush()
                UniOutput.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun saveArray(agora: Succession, hash: ArrayList<*>, jsonname: String) {
            val array = JSONArray()
            hash.forEach { value: Any -> array.add("" + value) }
            writeJson(agora, jsonname, array.toJSONString())
        }

        fun loadFriend(agora: Succession, hash: HashMap<UUID, HashMap<String, LinkedList<String>>>){
            try{
                val jsonname = "FriendStates"
                agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
                File(agora.dataFolder, "$jsonname.json").mkdir()
                val parser = JSONParser()
                val resultHash: HashMap<String, LinkedList<String>> = HashMap()
                val fileReader = FileReader(File(agora.dataFolder, "$jsonname.json"))

                if (fileReader.ready()) {
                    val obj: JSONObject = parser.parse(fileReader) as JSONObject
                    obj.forEach { x: Any, y: Any ->
                        (y as JSONObject).forEach { key: Any, value: Any ->
                            (value as JSONArray).forEach { v: Any ->
                                val result: LinkedList<String> = object : LinkedList<String>() {
                                    init {
                                        addAll(v as LinkedList<String>)
                                    }
                                }
                                resultHash["" + key] = result
                            }
                        }
                        hash[UUID.fromString(x.toString() + "")] = resultHash
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun saveFriend(agora: Succession, hash: HashMap<UUID, HashMap<String, LinkedList<String>>>) {
            val arrayname = "FriendStates"
            val uuid = JSONObject()
            val str = JSONObject()
            for ((key, value) in hash) {
                for ((key1, value1) in value) {
                    val a: JSONArray = object : JSONArray() {
                        init {
                            addAll(value1)
                        }
                    }
                    str[key1] = a
                }
                uuid[key] = str.clone()
                str.clear()
            }
            writeJson(agora, arrayname, uuid.toJSONString())
        }

        private fun writeJson(agora: Succession, jsonname: String, write: String) {
            try {
                val UniOutput = BufferedWriter(
                    OutputStreamWriter(
                        FileOutputStream(File(agora.dataFolder, "$jsonname.json").path),
                        "EUC-KR"
                    )
                )
                UniOutput.write(write)
                UniOutput.flush()
                UniOutput.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun loadArrayLocationString(agora: Succession, hash: ArrayList<String>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val array: JSONArray = obj as JSONArray
                    array.forEach { value: Any -> hash.add(value.toString()) }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadUUID(agora: Succession, hash: ArrayList<UUID>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val array: JSONArray = obj as JSONArray
                    array.forEach { value: Any -> hash.add(UUID.fromString("" + value)) }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadRank(agora: Succession, hash: HashMap<UUID, Type>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        hash[UUID.fromString(key.toString() + "")] = Type.valueOf(value.toString() + "")
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }

        fun loadStrInt(agora: Succession, hash: HashMap<String, Int>, jsonname: String) {
            agora.makeFile(File(agora.dataFolder, "$jsonname.json"))
            File(agora.dataFolder, "$jsonname.json").mkdir()
            val parser = JSONParser()
            try {
                if (FileReader(File(agora.dataFolder, "$jsonname.json")).ready()) {
                    val obj: Any = parser.parse(FileReader(File(agora.dataFolder, "$jsonname.json")))
                    val jsonObject: JSONObject = obj as JSONObject
                    jsonObject.forEach { key: Any, value: Any ->
                        hash[key.toString() + ""] = (value.toString() + "").toInt()
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } catch (e: ParseException) {
                e.printStackTrace()
            }
        }
    }
}