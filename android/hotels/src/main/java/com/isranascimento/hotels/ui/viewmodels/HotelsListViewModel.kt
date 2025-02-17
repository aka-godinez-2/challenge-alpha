package com.isranascimento.hotels.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.isranascimento.coremodels.hotel.Hotel
import com.isranascimento.hotels.models.HotelsListDomainState
import com.isranascimento.hotels.repository.IHotelsListRepository
import com.isranascimento.hotels.ui.models.HotelListUI
import com.isranascimento.hotels.ui.models.HotelListUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HotelsListViewModel(
    private val repository: IHotelsListRepository
): ViewModel() {
    private val _uiState = MutableStateFlow<HotelListUIState>(HotelListUIState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        getHotelsList()
    }

    fun getHotelsList() {
        _uiState.value = HotelListUIState.Loading
        viewModelScope.launch {
            when(val value = repository.getHotelList()) {
                is HotelsListDomainState.Success -> {
                    _uiState.value = HotelListUIState.Success(HotelListUI.fromHotelListDomainClass(value.hotelList))
                }
                is HotelsListDomainState.Error -> {
                    _uiState.value = HotelListUIState.Error
                }
            }
        }
    }

    fun getHotel(id: String): Hotel {
        val hotel = repository.getHotelWithId(id)
        return hotel!!
    }
}