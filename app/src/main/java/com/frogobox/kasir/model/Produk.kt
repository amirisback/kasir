package com.frogobox.kasir.model

import android.content.Context
import com.frogobox.kasir.DBHelper
import java.util.*

class Produk(var nama: String, var sn: String, var harga: Long, var stok: Int) {

    companion object {
        @JvmStatic
        fun getBySN(ctx: Context?, SN: String?): Produk? {
            val cur = DBHelper(ctx).baca(SN)
            if (!cur.moveToFirst()) return null
            if (cur.count < 1) {
                return null
            }
            val nama = cur.getString(cur.getColumnIndex("nama"))
            val sn = cur.getString(cur.getColumnIndex("sn"))
            val harga = cur.getLong(cur.getColumnIndex("harga"))
            val stok = cur.getInt(cur.getColumnIndex("stok"))
            return Produk(nama, sn, harga, stok)
        }

        @JvmStatic
        fun getInit(ctx: Context?): ArrayList<Produk> {
            val prod = ArrayList<Produk>()
            val cur = DBHelper(ctx).semuaData()
            cur.moveToFirst()
            for (i in 0 until cur.count) {
                cur.moveToPosition(i)
                val nama = cur.getString(cur.getColumnIndex("nama"))
                val sn = cur.getString(cur.getColumnIndex("sn"))
                val harga = cur.getLong(cur.getColumnIndex("harga"))
                val stok = cur.getInt(cur.getColumnIndex("stok"))
                prod.add(Produk(nama, sn, harga, stok))
            }
            return prod
        }
    }
}