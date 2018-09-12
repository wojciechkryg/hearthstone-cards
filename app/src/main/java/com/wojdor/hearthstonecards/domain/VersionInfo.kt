package com.wojdor.hearthstonecards.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionInfo(
        val version: String,
        val classNames: List<String>,
        val standardSets: List<String>,
        val locales: List<String>
) : Parcelable
