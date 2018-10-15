package com.wojdor.hearthstonecards.domain

import android.os.Parcelable
import com.wojdor.hearthstonecards.application.extension.empty
import kotlinx.android.parcel.Parcelize

@Parcelize
data class VersionInfo(
        val version: String = String.empty,
        val classNames: List<String> = emptyList(),
        val standardSets: List<String> = emptyList(),
        val locales: List<String> = emptyList()
) : Parcelable
