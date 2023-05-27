package com.atejeda.starwars.utils

import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide

fun ImageView.load(url:String){
    Glide.with(this.context).load(url).into(this)
}