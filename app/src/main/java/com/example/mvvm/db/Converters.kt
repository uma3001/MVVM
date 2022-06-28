package com.example.mvvm.db

import android.arch.persistence.room.TypeConverter
import com.example.mvvm.models.Source

class Converters {

    @TypeConverter
    fun fromsource(source: Source):String{
        return source.name
    }

    @TypeConverter
    fun tosource(name:String): Source {
        return Source(name,name)
    }
}