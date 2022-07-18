package com.rrat.mvvmtodoappcompose.playground



interface Animal{
    var name:String
    //get()= names[this] ?: "Default name"
    //set(value){names[this] = value}

    fun makeVoice()
    {
        println("${this::class.simpleName} name $name voice")
    }

  /*  companion object{
        private val names = mutableMapOf<Any, String>()
    }*/
}

//FOX
class Fox: Animal {
    override var name: String = "Sox"
}

//DOG
class Dog: Animal
{
    override var name: String = "Terry"
    override fun makeVoice() {
        println("${this::class.simpleName} name $name voice override")
    }
}

//CAT
class Cat(override var name: String) : Animal

fun main()
{
    val fox = Fox()
    val dog = Dog()
    val cat = Cat("Mou")

    fox.makeVoice()
    dog.makeVoice()
    cat.makeVoice()
}