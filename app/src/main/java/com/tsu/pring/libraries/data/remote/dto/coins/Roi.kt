package com.tsu.pring.libraries.data.remote.dto.coins

import android.os.Parcel
import android.os.Parcelable

data class Roi(
    val times: Float = 0f,
    val currency: String? = null,
    val percentage: Float = 0f,
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readFloat(),
        parcel.readString(),
        parcel.readFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(times)
        parcel.writeString(currency)
        parcel.writeFloat(percentage)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Roi> {

        override fun createFromParcel(parcel: Parcel): Roi {
            return Roi(parcel)
        }

        override fun newArray(size: Int): Array<Roi?> {
            return arrayOfNulls(size)
        }
    }
}
