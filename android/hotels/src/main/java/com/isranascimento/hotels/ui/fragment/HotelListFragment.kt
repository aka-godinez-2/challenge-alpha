package com.isranascimento.hotels.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.isranascimento.hoteldetail.fragment.HotelDetailFragment
import com.isranascimento.hotels.databinding.HotelListFragmentBinding
import com.isranascimento.hotels.ui.adapter.HotelsListAdapter
import com.isranascimento.hotels.ui.models.HotelListUI
import com.isranascimento.hotels.ui.models.HotelListUIState
import com.isranascimento.hotels.ui.viewmodels.HotelsListViewModel
import com.isranascimento.theme.sharedcomponents.ConnectionErrorView
import com.isranascimento.utils.extensions.navigateToScreen
import com.isranascimento.utils.extensions.setVisible
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class HotelListFragment: Fragment(), HotelsListAdapter.HotelsListAdapterContract {
    private lateinit var binding: HotelListFragmentBinding
    private val viewModel: HotelsListViewModel by viewModel()
    private val adapter by lazy {
        HotelsListAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return HotelListFragmentBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupViewModelObserver()
    }

    private fun setupViews() {
        binding.hotelList.adapter = adapter
    }

    private fun setupViewModelObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect {
                    renderUi(it)
                }
            }
        }
    }

    private fun renderUi(state: HotelListUIState) {
        binding.loadingIndicator.setVisible(state is HotelListUIState.Loading)
        binding.hotelList.setVisible(state is HotelListUIState.Success)
        binding.connectionErrorView.setVisible(state is HotelListUIState.Error)

        if(state is HotelListUIState.Success) {
            renderSuccess(state.hotelsValue)
        }
        if(state is HotelListUIState.Error) {
            renderError()
        }
    }

    private fun renderError() {
        binding.connectionErrorView.setOnTryAgainClickListener {
            viewModel.getHotelsList()
        }
    }

    private fun renderSuccess(value: List<HotelListUI>) {
        binding.hotelList.setVisible(true)
        adapter.submitList(value)
    }

    override fun onHotelClick(hotelId: String) {
        navigateToScreen(
            HotelDetailFragment.newInstance(
                viewModel.getHotel(hotelId)
            )
        )
    }
}