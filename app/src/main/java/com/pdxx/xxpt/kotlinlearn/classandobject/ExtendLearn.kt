package com.pdxx.xxpt.kotlinlearn.classandobject

class ExtendLearn{
    /*
    Kotlin 扩展
    Kotlin 可以对一个类的属性和方法进行扩展，且不需要继承或使用 Decorator 模式。

    扩展是一种静态行为，对被扩展的类代码本身不会造成任何影响。

    扩展函数
    扩展函数可以在已有类中添加新的方法，不会对原类做修改，扩展函数定义形式：
    fun receiverType.functionName(params){
     body
    }
    receiverType：表示函数的接收者，也就是函数扩展的对象
     functionName：扩展函数的名称
     params：扩展函数的参数，可以为NULL
         以下实例扩展 User 类 ：
     */

    class   User(var name:String)

    fun User.Print(){
        println("用户名 $name")
    }

    /*fun main(arg:Array<String>){
        var user = User("Runoob")
        user.Print()
    }

    实例执行输出结果为：

用户名 Runoob
*/

    //下面代码为 MutableList 添加一个swap 函数：



}