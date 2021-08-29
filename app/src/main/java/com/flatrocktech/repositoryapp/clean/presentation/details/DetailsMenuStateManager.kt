package com.flatrocktech.repositoryapp.clean.presentation.details

import android.content.Context
import android.view.Menu
import androidx.core.content.ContextCompat
import com.flatrocktech.repositoryapp.R

enum class StarState(val drawableId: Int) {
    STARRED(R.drawable.ic_star_filled_24),
    NOT_STARRED(R.drawable.ic_star_outline_24)
}

class DetailsMenuStateManager(private val context: Context, menu: Menu) {

    private val starItem = menu.findItem(R.id.menu_star_item)
    var starState = StarState.NOT_STARRED
        set(state) {
            field = state
            starItem.icon = ContextCompat.getDrawable(context, state.drawableId)
        }

    fun disableStar() {
        starItem.isEnabled = false
    }
}