package com.atejeda.starwars.interfaces

import com.atejeda.starwars.data.model.Element

interface ElementEvents {
    fun onclickElement(element: Element, postion:Int)
}