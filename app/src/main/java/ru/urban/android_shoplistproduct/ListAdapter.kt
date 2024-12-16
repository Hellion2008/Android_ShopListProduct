package ru.urban.android_shoplistproduct

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class ListAdapter (context: Context, productList: MutableList<Product>) :
    ArrayAdapter<Product> (context, R.layout.list_item, productList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        var view = convertView
        val product = getItem(position)

        if (view == null){
            view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)
        }

        val imageView = view?.findViewById<ImageView>(R.id.productImageIV)
        val nameProduct = view?.findViewById<TextView>(R.id.nameProductTV)
        val priceProduct = view?.findViewById<TextView>(R.id.priceProductTV)

        imageView?.setImageURI(Uri.parse(product?.image))
        nameProduct?.text = product?.name
        priceProduct?.text = product?.price

        return view!!
    }
}