package com.isranascimento.hotelslist.ui.adapter.holder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.isranascimento.hotelslist.ui.models.HotelListUI

abstract class BaseHotelListHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    abstract fun bind(item: HotelListUI)
}