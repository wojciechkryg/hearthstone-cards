package com.wojdor.hearthstonecards.data.service.model

data class VersionInfoModel(
        val patch: String,
        val classes: List<String>,
        val standard: List<String>,
        val locales: List<String>
)
