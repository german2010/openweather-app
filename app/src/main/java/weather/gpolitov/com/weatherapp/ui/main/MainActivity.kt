package weather.gpolitov.com.weatherapp.ui.main

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import weather.gpolitov.com.weatherapp.R
import weather.gpolitov.com.weatherapp.ui.base.BaseActivity
import weather.gpolitov.com.weatherapp.ui.main.fragment.MainFragment

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                    .beginTransaction()
                    .add(R.id.container_layout, MainFragment.newInstance())
                    .commitAllowingStateLoss()
        }
    }
}
