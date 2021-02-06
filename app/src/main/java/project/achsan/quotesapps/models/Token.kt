package project.achsan.quotesapps.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Token (
    var token: String? = null
): Parcelable