package com.ezequieldisisto.myapplication.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.ezequieldisisto.myapplication.viewmodel.GiphyViewModel
import com.ezequieldisisto.myapplication.R

import com.ezequieldisisto.myapplication.dummy.DummyContent
import com.ezequieldisisto.myapplication.model.GiphyObj
import kotlinx.android.synthetic.main.activity_item_list.*
import kotlinx.android.synthetic.main.item_list_content.view.*
import kotlinx.android.synthetic.main.item_list.*

/**
 * An activity representing a list of Pings. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ItemDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class ItemListActivity : AppCompatActivity() {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private var twoPane: Boolean = false
    private lateinit var giphyViewModel: GiphyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item_list)

        setSupportActionBar(toolbar)
        toolbar.title = title

        if (item_detail_container != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            twoPane = true
        }

        giphyViewModel = ViewModelProviders.of(this).get(GiphyViewModel::class.java)
        giphyViewModel.getList()

        giphyViewModel.giphyList.observe(this, Observer {
            setupRecyclerView(item_list, it)
        })

        giphyViewModel.status.observe(this, Observer {
            when (it) {
                GiphyViewModel.Status.ERROR -> {
                    Toast.makeText(this, "Error getting the list" , Toast.LENGTH_SHORT).show()
                }
            }
        })

    }

    private fun setupRecyclerView(recyclerView: RecyclerView, giphyList: ArrayList<GiphyObj>) {
        recyclerView.adapter =
            SimpleItemRecyclerViewAdapter(
                this,
                giphyList,
                twoPane
            )
    }

    class SimpleItemRecyclerViewAdapter(private val parentActivity: ItemListActivity, private val values: ArrayList<GiphyObj>, private val twoPane: Boolean) :
        RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>() {

        private val onClickListener: View.OnClickListener

        init {
            onClickListener = View.OnClickListener { v ->
                val item = v.tag as GiphyObj
                if (twoPane) {
                    val fragment = ItemDetailFragment()
                        .apply {
                        arguments = Bundle().apply {
                            putString(ItemDetailFragment.ITEM_IMAGE, item.getImage())
                            putString(ItemDetailFragment.ITEM_TITLE, item.title)
                            putString(ItemDetailFragment.ITEM_TIME, item.import_datetime)
                        }
                    }
                    parentActivity.supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.item_detail_container, fragment)
                        .commit()
                } else {
                    val intent = Intent(v.context, ItemDetailActivity::class.java).apply {
                        putExtra(ItemDetailFragment.ITEM_IMAGE, item.getImage())
                        putExtra(ItemDetailFragment.ITEM_TITLE, item.title)
                        putExtra(ItemDetailFragment.ITEM_TIME, item.import_datetime)
                    }
                    v.context.startActivity(intent)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_list_content, parent, false)
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = values[position]

            Glide.with(holder.image).load(item.getImage()).into(holder.image)
            holder.contentView.text = item.title

            with(holder.itemView) {
                tag = item
                setOnClickListener(onClickListener)
            }
        }

        override fun getItemCount() = values.size

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val image: ImageView = view.image
            val contentView: TextView = view.content
        }
    }
}
