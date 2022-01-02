package com.mostafamohammed.callreceptionoptions.customView

import com.mostafamohammed.callreceptionoptions.R

enum class Status(val label: Int) {
    RINGING(R.string.ringing),
    ANSWER(R.string.answer),
    DECLINE(R.string.decline),
    MESSAGE(R.string.message),
    SILENCER(R.string.silencer)
}