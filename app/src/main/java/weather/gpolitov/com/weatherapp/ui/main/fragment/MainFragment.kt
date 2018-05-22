package weather.gpolitov.com.weatherapp.ui.main.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_main.*
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import weather.gpolitov.com.weatherapp.ui.adapter.WeatherAdapter
import weather.gpolitov.com.weatherapp.ui.base.BaseFragment
import javax.inject.Inject

class MainFragment : BaseFragment(), MainFragmentContract.View {

    @Inject
    internal lateinit var presenter: MainFragmentContract.Presenter

    private lateinit var adapter: WeatherAdapter

    companion object {
        fun newInstance(): MainFragment {
            val args = Bundle()
            val fragment = MainFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setOnEditor()
        presenter.takeView(this)
        adapter = WeatherAdapter(requireContext())
        recycler_view.layoutManager = LinearLayoutManager(requireContext())
        recycler_view.adapter = adapter

        search_button.setOnClickListener({
            getWeather()
        })
    }

    private fun setOnEditor() {
        query_edit_text.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                getWeather()
                true
            } else {
                false
            }
        }
    }

    private fun getWeather() {
        val queryString = query_edit_text.text.toString().trim()
        if (!TextUtils.isEmpty(queryString)) {
            presenter.getRequestById(queryString)
        }
    }

    override fun showProgressIndicator() {
        progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgressIndicator() {
        progress_bar.visibility = View.GONE
    }

    override fun showWeatherData(response: WeatherResponse) {
        adapter.updateList(response.weatherList)
    }

    override fun showResponseError() {
        Toast.makeText(requireContext(), "Some API error", Toast.LENGTH_LONG).show()
    }

    override fun showNetworkError() {
        Toast.makeText(requireContext(), "Some network error", Toast.LENGTH_LONG).show()
    }

    override fun showRecyclerView() {
        recycler_view.visibility = View.VISIBLE
    }

    override fun hideRecyclerView() {
        recycler_view.visibility = View.INVISIBLE
    }
}