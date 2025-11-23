package com.pdxx.kotlinlearn.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pdxx.kotlinlearn.data.Lesson
import java.io.IOException

/**
 * JSON数据加载工具类
 * 用于从assets目录加载JSON文件并解析为Lesson对象列表
 */
object JsonDataLoader {
    
    /**
     * 从assets目录加载JSON文件并解析为Lesson对象列表
     * @param context 上下文对象
     * @param fileName JSON文件名
     * @return Lesson对象列表，如果加载失败则返回空列表
     */
    fun loadLessonsFromAssets(context: Context, fileName: String): List<Lesson> {
        return try {
            // 从assets读取JSON字符串
            val jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
            
            // 使用Gson解析JSON
            val gson = Gson()
            val listType = object : TypeToken<List<Lesson>>() {}.type
            gson.fromJson(jsonString, listType) ?: emptyList()
        } catch (e: IOException) {
            e.printStackTrace()
            emptyList()
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}