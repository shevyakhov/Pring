package com.tsu.pring.libraries.data.remote.dto.coins

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class CoinItem(
    val id: String?,
    val symbol: String?,
    val name: String?,
    val image: String? = null,
    @SerializedName("current_price")
    val currentPrice: Double = 0.0,
    @SerializedName("market_cap")
    val marketCap: Double = 0.0,
    @SerializedName("market_cap_rank")
    val marketCapRank: Long = 0,
    @SerializedName("fully_diluted_valuation")
    val fullyDilutedValuation: Double?,
    @SerializedName("total_volume")
    val totalVolume: Double = 0.0,
    @SerializedName("high_24h")
    val high24h: Double = 0.0,
    @SerializedName("low_24h")
    val low24h: Double = 0.0,
    @SerializedName("price_change_24h")
    val priceChange24h: Double = 0.0,
    @SerializedName("price_change_percentage_24h")
    val priceChangePercentage24h: Double = 0.0,
    @SerializedName("market_cap_change_24h")
    val marketCapChange24h: Double = 0.0,
    @SerializedName("market_cap_change_percentage_24h")
    val marketCapChangePercentage24h: Double = 0.0,
    @SerializedName("circulating_supply")
    val circulatingSupply: Double = 0.0,
    @SerializedName("total_supply")
    val totalSupply: Double? = null,
    @SerializedName("max_supply")
    val maxSupply: Double? = null,
    val ath: Double = 0.0,
    @SerializedName("ath_change_percentage")
    val athChangePercentage: Double = 0.0,
    @SerializedName("ath_date")
    val athDate: String? = null,
    val atl: Double = 0.0,
    @SerializedName("atl_change_percentage")
    val atlChangePercentage: Double = 0.0,
    @SerializedName("atl_date")
    val atlDate: String? = null,
    val roi: Roi? = null,
    @SerializedName("last_updated")
    val lastUpdated: String? = null,
    @SerializedName("price_change_percentage_1h_in_currency")
    val priceChangePercentage1hInCurrency: Double = 0.0,
): Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readDouble(),
        parcel.readString(),
        parcel.readParcelable(Roi::class.java.classLoader),
        parcel.readString(),
        parcel.readDouble()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(symbol)
        parcel.writeString(name)
        parcel.writeString(image)
        parcel.writeDouble(currentPrice)
        parcel.writeDouble(marketCap)
        parcel.writeLong(marketCapRank)
        parcel.writeValue(fullyDilutedValuation)
        parcel.writeDouble(totalVolume)
        parcel.writeDouble(high24h)
        parcel.writeDouble(low24h)
        parcel.writeDouble(priceChange24h)
        parcel.writeDouble(priceChangePercentage24h)
        parcel.writeDouble(marketCapChange24h)
        parcel.writeDouble(marketCapChangePercentage24h)
        parcel.writeDouble(circulatingSupply)
        parcel.writeValue(totalSupply)
        parcel.writeValue(maxSupply)
        parcel.writeDouble(ath)
        parcel.writeDouble(athChangePercentage)
        parcel.writeString(athDate)
        parcel.writeDouble(atl)
        parcel.writeDouble(atlChangePercentage)
        parcel.writeString(atlDate)
        parcel.writeParcelable(roi, flags)
        parcel.writeString(lastUpdated)
        parcel.writeDouble(priceChangePercentage1hInCurrency)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CoinItem> {

        override fun createFromParcel(parcel: Parcel): CoinItem {
            return CoinItem(parcel)
        }

        override fun newArray(size: Int): Array<CoinItem?> {
            return arrayOfNulls(size)
        }
    }
}