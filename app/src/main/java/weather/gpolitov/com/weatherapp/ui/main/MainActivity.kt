package weather.gpolitov.com.weatherapp.ui.main

import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.ui.base.BaseActivity
import weather.gpolitov.com.weatherapp.ui.main.favorite.FavoriteFragment
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragment
import weather.gpolitov.com.weatherapp.utils.SyncUtil
import javax.inject.Inject

class MainActivity : BaseActivity(), FragmentManager.OnBackStackChangedListener {

    @Inject
    internal lateinit var syncUtil: SyncUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setActionBar(toolbar, false, false, "")

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container_layout, MainFragment.newInstance(), MainFragment::class.java.toString())
                    .commitAllowingStateLoss()
        }

        supportFragmentManager.addOnBackStackChangedListener(this)

        syncUtil.startSync()
    }

    override fun onDestroy() {
        super.onDestroy()
        syncUtil.stopSync()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.add_favorite -> {
                onAddFavoriteClick()
                true
            }
            R.id.show_favorites -> {
                onShowFavoritesClick()
                true
            }
            android.R.id.home -> {
                supportFragmentManager.popBackStack()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun onAddFavoriteClick() {
        val fragment = supportFragmentManager.findFragmentByTag(MainFragment::class.java.toString()) as MainFragment
        fragment.saveToLocalStorage()
    }

    private fun onShowFavoritesClick() {
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_layout, FavoriteFragment.newInstance(), FavoriteFragment::class.java.toString())
                .addToBackStack(null)
                .commit()
    }

    override fun onBackStackChanged() {
        shouldDisplayHomeUp()
    }

    private fun shouldDisplayHomeUp() {
        val canShowBack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(canShowBack)
    }

    fun setToolbarTitle(title: String) {
        supportActionBar?.title = title
    }
}
