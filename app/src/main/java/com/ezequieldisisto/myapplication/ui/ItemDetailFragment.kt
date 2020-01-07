package com.ezequieldisisto.myapplication.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.ezequieldisisto.myapplication.R
import kotlinx.android.synthetic.main.activity_item_detail.*
import kotlinx.android.synthetic.main.item_detail.view.*
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [ItemListActivity]
 * in two-pane mode (on tablets) or a [ItemDetailActivity]
 * on handsets.
 */
class ItemDetailFragment : Fragment() {

    /**
     * The dummy content this fragment is presenting.
     */
    private var title: String? = null
    private var image: String? = null
    private var time: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            title = it.getString(ITEM_TITLE)
            image = it.getString(ITEM_IMAGE)
            time = it.getString(ITEM_TIME)

            activity?.toolbar_layout?.title = title
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.item_detail, container, false)

        // Show the dummy content as text in a TextView.

        Glide.with(requireContext()).load(image).into(rootView.detail_image)

        val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val outputFormat = SimpleDateFormat("MMM dd, yyyy")

        var date: Date? = null
        try {
            date = inputFormat.parse(time)
            rootView.detail_time.text =
                "${getString(R.string.imported)} ${outputFormat.format(date)}"
        } catch (e: ParseException) {

        }

        return rootView
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ITEM_TITLE = "title"
        const val ITEM_IMAGE = "image"
        const val ITEM_TIME = "time"
    }
}
