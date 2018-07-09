package weather.gpolitov.com.weatherapp.ui.main.favorite

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import weather.gpolitov.com.weatherapp.CITY_NAME
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.ui.adapter.FavoriteAdapter
import weather.gpolitov.com.weatherapp.ui.base.BaseFragment
import weather.gpolitov.com.weatherapp.ui.main.MainActivity
import weather.gpolitov.com.weatherapp.ui.main.details.DetailsFragment
import javax.inject.Inject

class FavoriteFragment : BaseFragment(), FavoriteFragmentContract.View {

    @Inject
    internal lateinit var presenter: FavoriteFragmentContract.Presenter

    private lateinit var favoriteAdapter: FavoriteAdapter

    companion object {
        fun newInstance(): FavoriteFragment {
            val args = Bundle()
            val fragment = FavoriteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.takeView(this)
        favoriteAdapter = FavoriteAdapter { favorite: Favorite -> onFavoriteClicked(favorite) }
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = favoriteAdapter
        presenter.getFavorites()

        (activity as MainActivity).setToolbarTitle(getString(R.string.favorite_screen))
    }

    private fun onFavoriteClicked(favorite: Favorite) {
        val bundle = Bundle()
        bundle.putString(CITY_NAME, favorite.name)
        (activity as MainActivity).supportFragmentManager
                .beginTransaction()
                .replace(R.id.container_layout, DetailsFragment.newInstance(bundle), FavoriteFragment::class.java.toString())
                .addToBackStack(null)
                .commit()
    }

    override fun onDestroyView() {
        presenter.dropView()
        super.onDestroyView()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.clear()
    }

    override fun showFavorites(favorites: List<Favorite>, hasConnected: Boolean) {
        favoriteAdapter.updateList(favorites, hasConnected)
    }

    override fun showDBError() {
        Toast.makeText(requireContext(), getString(R.string.db_error), Toast.LENGTH_LONG).show()
    }

    override fun showProgressIndicator() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progress_bar.visibility = View.GONE
    }
}