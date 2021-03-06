package com.frogobox.kasir

import android.Manifest
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.frogobox.kasir.adapter.BelanjaanDataAdapter
import com.frogobox.kasir.adapter.ProdukDataAdapter
import com.frogobox.kasir.fragment.BelanjaFragment
import com.frogobox.kasir.fragment.ProductFragment
import com.frogobox.kasir.model.Produk.Companion.getInit
import com.google.android.material.navigation.NavigationView
import kr.co.namee.permissiongen.PermissionFail
import kr.co.namee.permissiongen.PermissionGen
import kr.co.namee.permissiongen.PermissionSuccess

class MainActivity : AppCompatActivity() {
    var mDrawer: DrawerLayout? = null
    var nvDrawer: NavigationView? = null
    var drawerToggle: ActionBarDrawerToggle? = null
    var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById<View>(R.id.mytoolbar) as Toolbar
        setSupportActionBar(toolbar)
        mDrawer = findViewById<View>(R.id.drawer_layout) as DrawerLayout
        nvDrawer = findViewById<View>(R.id.naView) as NavigationView
        setupDrawer(nvDrawer)
        drawerToggle = setupDrawerToggle()
        mDrawer!!.addDrawerListener(drawerToggle!!)
        supportFragmentManager.beginTransaction().replace(R.id.konten, BelanjaFragment()).commit()
        nvDrawer!!.menu.getItem(0).isChecked = true
        title = "Belanja"
        reqPerms()
        dataproduk = ProdukDataAdapter(this, getInit(this))
        dataBalanjaan = BelanjaanDataAdapter(this)
    }

    private fun reqPerms() {
        PermissionGen.with(this@MainActivity)
            .addRequestCode(100)
            .permissions(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )
            .request()
    }

    private fun setupDrawer(nview: NavigationView?) {
        nview!!.setNavigationItemSelectedListener { menu ->
            Terpilih(menu)
            true
        }
    }

    private fun setupDrawerToggle(): ActionBarDrawerToggle {
        return ActionBarDrawerToggle(
            this,
            mDrawer,
            toolbar,
            R.string.drawer_buka,
            R.string.drawer_tutup
        )
    }

    private fun Terpilih(menu: MenuItem) {
        val fragclass: Class<*>
        var frag: Fragment? = null
        fragclass = when (menu.itemId) {
            R.id.frag1 -> BelanjaFragment::class.java
            R.id.frag2 -> ProductFragment::class.java
            else -> BelanjaFragment::class.java
        }
        try {
            frag = fragclass.newInstance() as Fragment
        } catch (e: Exception) {
        }
        val fm = supportFragmentManager
        fm.beginTransaction().replace(R.id.konten, frag!!).commit()
        menu.isChecked = true
        title = menu.title
        mDrawer!!.closeDrawers()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // TODO: Implement this method
        when (item.itemId) {
            android.R.id.home -> {
                mDrawer!!.openDrawer(GravityCompat.START)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        // Sync the toggle state after onRestoreInstanceState has occurred.
        drawerToggle!!.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        drawerToggle!!.onConfigurationChanged(newConfig)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.mainmenu, menu)
        return true
    }

    @PermissionSuccess(requestCode = 100)
    fun doSomething() {
        // Lakukan sesuatu disini
    }

    @PermissionFail(requestCode = 100)
    fun doFailSomething() {
        val dlg = AlertDialog.Builder(this)
        dlg.setTitle("Perijinan ditolak")
        dlg.setCancelable(false)
        dlg.setMessage("Untuk menggunakan Aplikasi ini kamu perlu membolehkan beberapa perijinan yang diajukan. Atau Aplikasi ini tidak bisa digunakan")
        dlg.setNegativeButton("Keluar") { p1, p2 -> finish() }
        dlg.show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
    }

    override fun onDestroy() {
        BelanjaanDataAdapter.total = 0
        super.onDestroy()
    }

    companion object {
        @JvmField
        var dataproduk: ProdukDataAdapter? = null

        @JvmField
        var dataBalanjaan: BelanjaanDataAdapter? = null
    }
}