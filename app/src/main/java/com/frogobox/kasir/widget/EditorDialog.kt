package com.frogobox.kasir.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.frogobox.kasir.MainActivity
import com.frogobox.kasir.R
import com.frogobox.kasir.adapter.BelanjaanDataAdapter
import com.frogobox.kasir.model.Belanjaan

class EditorDialog(ctx: Context?, bel: Belanjaan, totalbelanja: TextView) {
    init {
        val nama = bel.produk.nama
        val harga = bel.produk.harga
        val `val` = bel.quantity
        val v = LayoutInflater.from(ctx).inflate(R.layout.editproduk, null)
        val num = v.findViewById<View>(R.id.numr) as NumPik
        val hargaview = v.findViewById<View>(R.id.hargaview) as TextView
        val totalview = v.findViewById<View>(R.id.totalview) as TextView
        val dlg = AlertDialog.Builder(
            ctx!!
        )
        dlg.setView(v)
        dlg.setTitle(nama)
        dlg.setPositiveButton("Set") { p1, p2 ->
            MainActivity.dataBalanjaan?.tambah(bel.produk, num.value)
            totalbelanja.text =
                "Rp. " + BelanjaanDataAdapter.PRICE_FORMATTER.format(BelanjaanDataAdapter.total)
        }
        dlg.setNeutralButton("Hapus Belanjaan", null)
        dlg.show()
        totalview.text = "Rp. " + BelanjaanDataAdapter.PRICE_FORMATTER.format(harga * `val`)
        hargaview.text = "Rp. " + BelanjaanDataAdapter.PRICE_FORMATTER.format(harga)
        num.minValue = 1
        num.maxValue = 200
        num.value = `val`
        num.setOnValueChangedListener { p1, p2, p3 ->
            totalview.text = "Rp. " + BelanjaanDataAdapter.PRICE_FORMATTER.format(harga * num.value)
        }
    }
}