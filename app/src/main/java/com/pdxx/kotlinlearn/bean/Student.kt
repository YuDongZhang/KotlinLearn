package com.pdxx.kotlinlearn.bean

class Student(val name:String) {

    constructor(personEntity: PersonEntity):this(personEntity.toString())

}