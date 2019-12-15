package com.example.wisatabandung.adapter

import android.content.Context
import android.view.ViewGroup
import android.view.LayoutInflater
import android.support.v4.view.PagerAdapter
import android.view.View
import android.widget.ImageView
import com.example.wisatabandung.R
import com.squareup.picasso.Picasso


class PagerAdapter(private val context: Context) : PagerAdapter() {
    /*
        This callback is responsible for creating a page. We inflate the layout and set the drawable
        to the ImageView based on the position. In the end we add the inflated layout to the parent
        container .This method returns an object key to identify the page view, but in this example page view
        itself acts as the object key
        */
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = LayoutInflater.from(context).inflate(R.layout.pager_item, null)
        val imageView : ImageView = view.findViewById(R.id.iv_pager)
        Picasso.get().load(getImageAt(position)).into(imageView)
        /*imageView.setImageDrawable(context.resources.getDrawable(getImageAt(position)))*/
        container.addView(view)
        return view
    }

    /*
        This callback is responsible for destroying a page. Since we are using view only as the
        object key we just directly remove the view from parent container
        */
    override fun destroyItem(container: ViewGroup, position: Int, view: Any) {
        container.removeView(view as View)
    }

    /*
        Returns the count of the total pages
        */
    override fun getCount(): Int {
        return 4
    }

    /*
        Used to determine whether the page view is associated with object key returned by instantiateItem.
        Since here view only is the key we return view==object
        */
    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return `object` === view
    }

    private fun getImageAt(position: Int): Int {
        when (position) {
            0 -> return R.drawable.gunung_putri1
            1 -> return R.drawable.house
            2 -> return R.drawable.table
            3 -> return R.drawable.tent
            else -> return R.drawable.museum
        }
    }
}