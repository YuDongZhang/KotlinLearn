package com.pdxx.kotlinlearn

import org.junit.Test

class `16_集合` {
    /**
     * 数组
     */
    @Test
    fun test1() {
        //arrayOf
        val array: Array<Int> = arrayOf()

        //arrayOfNulls
        val arrayOne: Array<Int?> = arrayOfNulls<Int>(3)
        arrayOne[0] = 4
        arrayOne[1] = 5
        arrayOne[2] = 6

        //array 构造函数  //如果函数作为参数 , 并且是最后一个 , 写出lamb形式
        val arrayTwo: Array<String> = Array(5) { i -> (i * i).toString() }

        //指定类型的数据
        //intArrayof() , doubleArrayOf()
        val x: IntArray = intArrayOf(1, 2, 3)

        // 例如：用常量初始化数组中的值 // 大小为 5、值为 [42, 42, 42, 42, 42] 的整型数组
        val arr4 = IntArray(5) { 42 }

        // 例如：使用 lambda 表达式初始化数组中的值
        // 大小为 5、值为 [0, 1, 2, 3, 4] 的整型数组（值初始化为其索引值）
        var arr5 = IntArray(5) { it * 1 }

        // 数组遍历
        for (item in arr5) {
            println(item)
        }

        //带索引遍历数组
        for (i in arr5.indices) {
            println(i.toString() + "->" + arr5[i])
        }

        //遍历元素(带索引)
        for ((index, item) in arr5.withIndex()) {
            println("$index->$item")
        }

        //forEach遍历数组
        arr5.forEach { println(it) }

        //forEach增强版
        arr5.forEachIndexed { index, item -> println("$index：$item") }

    }

    /**
     * 集合
     */
    @Test
    fun test2() {
        //不可变集合
        val stringList = listOf("one", "two", "one")
        println(stringList)

        val stringSet = setOf("one", "two", "three")
        println(stringSet)


        //可变集合
        val numbers = mutableListOf(1, 2, 3, 4)
        numbers.add(5)
        numbers.removeAt(1)
        numbers[0] = 0
        println(numbers)

    }

    /**
     * 集合排序
     */
    @Test
    fun test3() {
        val numbers = mutableListOf(1, 2, 3, 4)
        //随机排列元素
        numbers.shuffle()
        println(numbers)
        numbers.sort() //排序，从小打到
        numbers.sortDescending() // 从大到小
        println(numbers)

        // 定义一个Person类，有name 和 age 两属性
        data class Language(var name: String, var score: Int)

        val languageList: MutableList<Language> = mutableListOf()

        languageList.add(Language("Java", 80))
        languageList.add(Language("Kotlin", 90))
        languageList.add(Language("Dart", 99))
        languageList.add(Language("C", 80))
        // 使用sortBy进行排序，适合单条件排序
        languageList.sortBy { it.score }
        println(languageList)
        // 使用sortWith进行排序，适合多条件排序
        languageList.sortWith(compareBy(
                // it变量是lambda中的隐式参数
                { it.score }, { it.name }))
        println(languageList)
    }

    /**
     * set 和 map
     */
    @Test
    fun test4() {
        /**set**/
        val hello = mutableSetOf("H", "e", "l", "l", "o")//自动过滤重复元素
        hello.remove("o")

        // 集合的加减操作
        hello += setOf("w", "o", "r", "l", "d")
        println(hello)

        /**Map 不是 Collection 接口的继承者；但是它也是 Kotlin 的一种集合类型**/
        val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)
        println("All keys: ${numbersMap.keys}")
        println("All values: ${numbersMap.values}")

        if ("key2" in numbersMap)
            println("Value by key \"key2\": ${numbersMap["key2"]}")

        if (1 in numbersMap.values)
            println("1 is in the map")

        if (numbersMap.containsValue(1))
            println(" 1 is in the map")

        /** 内容相同 , 顺序不同的map是否相等 */
        val otherMap = mapOf("key3" to 3, "key1" to 1, "key2" to 2, "key4" to 1)
        println("numbersMap==otherMap:${otherMap == numbersMap}")
        otherMap.equals(numbersMap)

    }




}