package weather.gpolitov.com.weatherapp.ui.main.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_details.*
import weather.gpolitov.com.weatherapp.CITY_NAME
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.Favorite
import weather.gpolitov.com.weatherapp.ui.base.BaseFragment
import weather.gpolitov.com.weatherapp.ui.main.MainActivity
import javax.inject.Inject

class DetailsFragment : BaseFragment(), DetailsContract.View {

    @Inject
    internal lateinit var presenter: DetailsContract.Presenter

    companion object {
        fun newInstance(args: Bundle): DetailsFragment {
            val fragment = DetailsFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val selectedFavorite = arguments?.getString(CITY_NAME)

        presenter.takeView(this)
        if (selectedFavorite != null) {
            presenter.getDetailedWeatherByCity(selectedFavorite)
        }
    }

    override fun onDestroyView() {
        presenter.dropView()
        super.onDestroyView()
    }

    override fun onPrepareOptionsMenu(menu: Menu?) {
        menu?.clear()
    }

    override fun setData(favorite: Favorite) {
        (activity as MainActivity).setToolbarTitle(favorite.name)
        temp_text_view.text = favorite.temp
        description_text_view.text = favorite.description
    }

    override fun showDBError() {
        Toast.makeText(requireContext(), getString(R.string.db_error), Toast.LENGTH_LONG).show()
    }
}