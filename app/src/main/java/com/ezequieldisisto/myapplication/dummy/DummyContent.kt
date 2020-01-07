package com.ezequieldisisto.myapplication.dummy

import com.ezequieldisisto.myapplication.model.GiphyObj
import java.util.*

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    val ITEMS: MutableList<GiphyObj> = ArrayList()

    /**
     * A map of sample (dummy) items, by ID.
     */
    val ITEM_MAP: MutableMap<String, GiphyObj> = HashMap()

    private val COUNT = 25

    private fun addItem(item: GiphyObj) {
        ITEMS.add(item)
        ITEM_MAP.put(item.title, item)
    }

    private fun makeDetails(position: Int): String {
        val builder = StringBuilder()
        builder.append("Details about Item: ").append(position)
        for (i in 0..position - 1) {
            builder.append("\nMore details information here.")
        }
        return builder.toString()
    }

}
