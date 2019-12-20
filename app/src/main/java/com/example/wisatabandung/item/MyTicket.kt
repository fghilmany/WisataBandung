package com.example.wisatabandung.item

class MyTicket{

    var name_destination : String? = ""
    var date_exp : String? = ""
    var transaction_id : String? = ""
    var purchased_ticket : Long? = 0
    var total_pay : Long? = 0

    constructor(){

    }

    constructor(name_destination: String?, date_exp: String?,transaction_id:String?, purchased_ticket: Long?, total_pay:Long?) {
        this.name_destination = name_destination
        this.date_exp = date_exp
        this.transaction_id = transaction_id
        this.purchased_ticket = purchased_ticket
        this.total_pay = total_pay
    }
}