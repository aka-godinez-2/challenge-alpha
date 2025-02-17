package com.isranascimento.hoteldetail.models

import com.isranascimento.coremodels.hotel.Hotel

data class HotelDetailUI(
    val gallery: List<String>,
    val id: String,
    val name: String,
    val city: String,
    val state: String,
    val amenities: List<String>,
    val description: String,
    val starCount: Float,
    val shareLink: String
) {
    companion object {
        fun fromDomainModel(domain: Hotel): HotelDetailUI {
            return HotelDetailUI(
                gallery = domain.gallery,
                id = domain.id,
                name = domain.name,
                city = domain.address.city,
                state = domain.address.state,
                amenities = domain.amenities,
                description = domain.description,
                starCount = domain.starCount.toFloat(),
                shareLink = domain.url
            )
        }
    }
}