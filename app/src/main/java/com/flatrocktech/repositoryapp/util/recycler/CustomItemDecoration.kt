package com.flatrocktech.repositoryapp.util.recycler

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.flatrocktech.repositoryapp.R

/*
    Taken from: https://stackoverflow.com/questions/44708457/is-it-possible-to-hide-the-itemdecoration-for-the-last-item-in-a-recyclerview
 */
class CustomItemDecoration(
    context: Context
) : RecyclerView.ItemDecoration() {

    private val drawable: Drawable = ContextCompat.getDrawable(context, R.drawable.divider_1dp)!!

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left = parent.paddingLeft
        val right = parent.width - parent.paddingRight

        val childCount = parent.adapter!!.itemCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)
            if (child != null) {
                val params = child.layoutParams as RecyclerView.LayoutParams
                val top = child.bottom + params.bottomMargin
                val bottom = top + drawable.intrinsicHeight
                drawable.setBounds(left, top, right, bottom)
                drawable.draw(c)
            }
        }
    }
}