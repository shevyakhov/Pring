package com.tsu.pring.libraries.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.tsu.pring.libraries.data.remote.dto.coins.Roi

class RoiTypeConverter {

	private val gson = Gson()

	@TypeConverter
	fun fromRoi(value: Roi?): String? {
		return value?.let { Gson().toJson(it) }
	}

	@TypeConverter
	fun toRoi(value: String?): Roi? {
		return value?.let { Gson().fromJson(it, Roi::class.java) }
	}
}