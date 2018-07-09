package weather.gpolitov.com.weatherapp.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class Utils @Inject constructor(val context: Context) {

    private val DATE_FORMATTER = "yyyy-MM-dd'T'HH:mm:ss"

    fun isNetworkConnected(): Boolean {
        val cm: ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun getCurrentTime(): String {
        val formatUTC = SimpleDateFormat(DATE_FORMATTER)
        formatUTC.timeZone = TimeZone.getTimeZone("UTC")
        return formatUTC.format(Date())
    }
}
