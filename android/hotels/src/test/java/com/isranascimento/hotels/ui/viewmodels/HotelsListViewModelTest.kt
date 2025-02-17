package com.isranascimento.hotels.ui.viewmodels

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.isranascimento.coremodels.hotel.Hotel
import com.isranascimento.hotels.models.HotelsListDomainState
import com.isranascimento.hotels.repository.IHotelsListRepository
import com.isranascimento.hotels.ui.viewmodels.HotelsListViewModelTest.HotelsListRepositoryDouble.ExpectedResponseStatus.ERROR
import com.isranascimento.hotels.ui.viewmodels.HotelsListViewModelTest.HotelsListRepositoryDouble.ExpectedResponseStatus.SUCCESS
import com.isranascimento.hotels.ui.models.HotelListUIState
import com.isranascimento.hotels.ui.models.HotelListUITitle
import com.isranascimento.hotels.util.ReturnedValues.HOTEL_DOMAIN_LIST
import com.isranascimento.hotels.util.createHotelUIItem
import com.isranascimento.testutils.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test

class HotelsListViewModelTest {

    @ExperimentalCoroutinesApi
    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var sut: HotelsListViewModel

    @Test
    fun `WHEN repository returns with success THEN the viewmodel update the uiStateFlow to loading and after UiState_Success expected value`() = runBlocking {
        sut = HotelsListViewModel(HotelsListRepositoryDouble(SUCCESS))
        sut.uiState.test {
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Success::class.java)
            sut.getHotelsList()
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Loading::class.java)

            val successItem = awaitItem()
            assertThat(successItem).isInstanceOf(HotelListUIState.Success::class.java)
            successItem as HotelListUIState.Success
            assertThat(successItem.hotelsValue.size).isEqualTo(7)
            assertThat(successItem.hotelsValue[0]).isEqualTo(HotelListUITitle(4f))
            assertThat(successItem.hotelsValue[1]).isEqualTo(createHotelUIItem(4))
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `WHEN repository returns with error THEN the viewmodel update the uiStateFlow to loading and after UiState_Error`() = runBlocking {
        sut = HotelsListViewModel(HotelsListRepositoryDouble(ERROR))

        sut.uiState.test {
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Error::class.java)
            sut.getHotelsList()
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `WHEN the viewModel is created it calls the getHotelsList function and set expected sequence of state`() = runBlocking {
        sut = HotelsListViewModel(HotelsListRepositoryDouble(ERROR))

        sut.uiState.test {
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Error::class.java)
            sut.getHotelsList()
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Loading::class.java)
            assertThat(awaitItem()).isInstanceOf(HotelListUIState.Error::class.java)
            cancelAndIgnoreRemainingEvents()
        }

    }

    @Test
    fun `WHEN getHotelWithSku is called THEN the viewmodel returns the expected Hotel`() {
        sut = HotelsListViewModel(HotelsListRepositoryDouble(SUCCESS))

        val hotel = sut.getHotel("1")

        assertThat(hotel.id).isEqualTo("1")
        assertThat(hotel.name).isEqualTo("Hotel 1")
        assertThat(hotel.gallery.size).isEqualTo(1)
        assertThat(hotel.url).isEqualTo("Share 1")
        assertThat(hotel.description).isEqualTo("Description 1")
        assertThat(hotel.address.city).isEqualTo("City 1")
        assertThat(hotel.amenities.size).isEqualTo(6)
        assertThat(hotel.starCount).isEqualTo(3)
        assertThat(hotel.address.state).isEqualTo("State 1")
    }

    private class HotelsListRepositoryDouble(
        private val expectedResponseStatus: ExpectedResponseStatus
    ): IHotelsListRepository {
        enum class ExpectedResponseStatus {
            SUCCESS, ERROR
        }

        override suspend fun getHotelList(): HotelsListDomainState = when (expectedResponseStatus) {
            SUCCESS -> HotelsListDomainState.Success(HOTEL_DOMAIN_LIST)
            ERROR -> HotelsListDomainState.Error
        }

        override fun getHotelWithId(hotelId: String): Hotel? {
            return HOTEL_DOMAIN_LIST[0]
        }
    }
}