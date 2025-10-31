package com.pdxx.kotlinlearn.data

data class Lesson(
    val title: String,
    val list: List<LessonItem>
)

data class LessonItem(
    val concept: String,
    val example: String
)