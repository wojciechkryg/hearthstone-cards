package com.wojdor.hearthstonecards.app.util

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

import timber.log.Timber

class JsonParser {

    private val gson = Gson()

    fun <T> toJson(objectToParse: T): String {
        return gson.toJson(objectToParse)
    }

    fun <T> fromJson(type: Class<T>?, json: String): T? {
        var parsedObject: T? = null
        try {
            parsedObject = gson.fromJson(json, type)
        } catch (error: JsonSyntaxException) {
            Timber.e(error)
        }
        return parsedObject
    }
}
