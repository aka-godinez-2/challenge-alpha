package com.isranascimento.hotels.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.isranascimento.core.fragment.BaseToolbarFragment
import com.isranascimento.hotels.R
import com.isranascimento.hotels.databinding.HotelDetailFragmentBinding
import com.isranascimento.hotels.ui.adapter.detail.HotelDetailGalleryAdapter
import com.isranascimento.hotels.ui.models.HotelDetailUI
import com.isranascimento.utils.extensions.attachSnapHelperWithListener

class HotelDetailFragment: BaseToolbarFragment() {
    private lateinit var binding: HotelDetailFragmentBinding

    private val uiModel by lazy {
        requireArguments().getParcelable<HotelDetailUI>(MODEL)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return HotelDetailFragmentBinding.inflate(inflater, container, false)
            .also {
                binding = it
            }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            hotelName.text = uiModel.name
            hotelDescription.text = uiModel.description
            hotelRating.rating = uiModel.starCount
            hotelLocation.text = getString(R.string.hotel_list_location_text, uiModel.city, uiModel.state)
            binding.gallery.adapter = HotelDetailGalleryAdapter(uiModel.gallery)
        }
        setupGalleryItemsText(0)
        setupGallerySnap()
    }

    private fun setupGallerySnap() {
        binding.gallery.attachSnapHelperWithListener(
            PagerSnapHelper(),
        ) {
            setupGalleryItemsText(it)
        }
    }

    private fun setupGalleryItemsText(currentItem: Int) {
        binding.galleryItemsText.text =
            getString(R.string.gallery_items_text, currentItem + 1, uiModel.gallery.count())
    }

    override fun getToolbarTitle(): String = uiModel.name

    override fun getMenuResource(): Int = R.menu.hotels_detail_menu

    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        if(menuItem.itemId == R.id.action_share) {
            shareHotel()
            return true
        }
        return super.onMenuItemClick(menuItem)
    }

    private fun shareHotel() {
        val i = Intent(Intent.ACTION_SEND)
        i.type = "text/plain"
        i.putExtra(Intent.EXTRA_TEXT, uiModel.shareLink)
        startActivity(Intent.createChooser(i, getString(R.string.share_action)))
    }

    companion object {
        private const val MODEL = "model"

        fun newInstance(model: HotelDetailUI): HotelDetailFragment =
            HotelDetailFragment().apply {
                this.arguments = bundleOf(MODEL to model)
            }
    }
}