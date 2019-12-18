package com.example.wisatabandung.item

class Destination{

    var photo_1 : String? = ""
    var name : String? = ""
    var address : String? = ""
    var id : String? = ""
    var price :Int? = 0

    constructor(){

    }

    constructor(photo_1: String?, name: String?, address: String?,price: Int?,id:String?) {
        this.photo_1 = photo_1
        this.name = name
        this.address = address
        this.price = price
        this.id = id
    }

}
