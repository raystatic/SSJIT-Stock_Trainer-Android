package com.ssjit.papertrading.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.ssjit.papertrading.R
import com.ssjit.papertrading.data.models.Banner
import com.ssjit.papertrading.other.Utility
import org.w3c.dom.Text

class BannerPagerAdapter (private val mContext: Context, private  val itemList: MutableList<Banner>) : PagerAdapter() {
    private var layoutInflater: LayoutInflater? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        layoutInflater = LayoutInflater.from(mContext)
        val view = layoutInflater!!.inflate(R.layout.banner_layout, container, false)
        val tvName = view.findViewById<TextView>(R.id.tvStock)
        val tagLine = view.findViewById<TextView>(R.id.tvTagline)
        val img = view.findViewById<ImageView>(R.id.imgStock)

        val bannner = itemList[position]

        Glide.with(mContext)
            .load(bannner.image)
            .into(img)
        tvName.text = bannner.name
        tagLine.text = bannner.tagline

        view.setOnClickListener {
            Utility.openUrl(mContext,bannner.link)
        }

        container.addView(view, position)
        return view
    }

    override fun getCount(): Int {
        return itemList.size
    }

    override fun isViewFromObject(view: View, obj: Any): Boolean {
        return view === obj
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        val view = `object` as View
        container.removeView(view)
    }
}