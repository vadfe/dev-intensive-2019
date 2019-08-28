package ru.skillbranch.devintensive.models.data


import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User(
    val id :String,
    var firstName:String?,
    var lastName:String?,
    var avatar: String?,
    var rating: Int = 0,
    var respect: Int = 0,
    val lastVisit: Date? = null,
    var isOnline: Boolean = false
) {
    constructor(id :String, firstName:String?, lastName:String?) : this (
       id = id,
       firstName = firstName,
       lastName = lastName,
       avatar = null
    )
    constructor(id :String) : this (
        id = id,
        firstName = "John",
        lastName = "Doe",
        avatar = null
    )
    constructor(builder: Builder) : this(
        id = builder.id,
        firstName=builder.firstName,
        lastName=builder.lastName,
        avatar=builder.avatar,
        rating=builder.rating,
        respect=builder.respect,
        lastVisit=builder.lastVisit,
        isOnline=builder.isOnline)

    init {
        /*println("It's Alive!!!\n"+
        "His name is $firstName $lastName"
        )*/
    }

    companion object Factory{
        private var lastId : Int = -1
        fun makeUser(fullName:String?) : User {
            lastId++
            val (firstName, lastName) = Utils.parseFullName(fullName)
            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }

    class  Builder {
        var id : String = "0"
            private set
        var firstName: String? = null
            private set
        var lastName: String? = null
            private set
        var avatar: String? = null
            private set
        var rating: Int = 0
            private set
        var respect: Int = 0
            private set
        var lastVisit: Date? = Date()
            private set
        var isOnline: Boolean = false
            private set
        fun id(id: String) = apply { this.id = id }
        fun firstName(firstName: String) = apply { this.firstName = firstName }
        fun lastName(lastName: String) = apply { this.lastName = lastName }
        fun avatar(avatar: String) = apply { this.avatar = avatar }
        fun rating(rating: Int) = apply { this.rating = rating }
        fun respect(respect: Int) = apply { this.respect = respect }
        fun lastVisit(lastVisit: Date) = apply { this.lastVisit = lastVisit }
        fun isOnline(isOnline: Boolean) = apply { this.isOnline = isOnline }
        fun build(): User {
            return User(id = this.id, firstName = this.firstName, lastName = this.lastName,
                    avatar = this.avatar, rating = this.rating, respect = this.respect,
                    lastVisit = this.lastVisit, isOnline = this.isOnline)
        }
    }
}