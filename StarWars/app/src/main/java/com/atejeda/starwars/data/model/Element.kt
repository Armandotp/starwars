package com.atejeda.starwars.data.model

data class Element(
    var id:Int,
    var name:String,
    var height:Double,
    var mass:Double,
    var gender:String,
    var species:String,
    var wiki:String,
    var image:String,
    var born:String,
    var affiliations:List<String>
):java.io.Serializable