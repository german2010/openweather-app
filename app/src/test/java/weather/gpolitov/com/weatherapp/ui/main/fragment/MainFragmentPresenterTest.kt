package weather.gpolitov.com.weatherapp.ui.main.fragment

import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import retrofit2.HttpException
import retrofit2.Response
import weather.gpolitov.com.weatherapp.data.DataRepository
import weather.gpolitov.com.weatherapp.data.network.NoNetworkException
import weather.gpolitov.com.weatherapp.model.Main
import weather.gpolitov.com.weatherapp.model.Weather
import weather.gpolitov.com.weatherapp.model.WeatherList
import weather.gpolitov.com.weatherapp.model.WeatherResponse
import weather.gpolitov.com.weatherapp.utils.schedulers.TrampolineSchedulerProvider


class MainFragmentPresenterTest {

    private lateinit var schedulerProvider: TrampolineSchedulerProvider

    private lateinit var presenter: MainFragmentPresenter

    @Mock
    lateinit var view: MainFragmentContract.View

    @Mock
    lateinit var dataRepository: DataRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        schedulerProvider = TrampolineSchedulerProvider()

        presenter = MainFragmentPresenter(dataRepository, schedulerProvider)
    }

    @Test
    fun testGetWeather_successGotWeather_showWeather() {
        val weatherResponse = getMockedWeatherResponse()

        doReturn(Single.just(weatherResponse))
                .`when`(dataRepository).getWeatherByCity(anyString(), anyString(), anyString())

        presenter.takeView(view)
        presenter.getRequestById(getMockedQueryString())

        verify(view).hideRecyclerView()
        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).showRecyclerView()
        verify(view).showWeatherData(weatherResponse)
    }

    @Test
    fun testGetWeather_networkErrorGotWeather_showNetworkError() {
        val single: Single<List<WeatherResponse>> = Single.create { emitter ->
            emitter.onError(NoNetworkException())
        }

        doReturn(single)
                .`when`(dataRepository).getWeatherByCity(anyString(), anyString(), anyString())

        presenter.takeView(view)
        presenter.getRequestById(getMockedQueryString())

        verify(view).hideRecyclerView()
        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).showRecyclerView()
        verify(view).showNetworkError()
    }

    @Test
    fun testGetWeather_httpErrorGotWeather_showHttpError() {
        val aResponse: Response<List<WeatherResponse>> = getMockedHttpErrorResponse()

        val single: Single<List<WeatherResponse>> = Single.create { emitter ->
            emitter.onError(HttpException(aResponse))
        }

        doReturn(single)
                .`when`(dataRepository).getWeatherByCity(anyString(), anyString(), anyString())

        presenter.takeView(view)
        presenter.getRequestById(getMockedQueryString())

        verify(view).hideRecyclerView()
        verify(view).showProgressIndicator()
        verify(view).hideProgressIndicator()
        verify(view).showResponseError()
    }

    private fun getMockedHttpErrorResponse(): Response<List<WeatherResponse>> {
        return Response.error(
                403,
                ResponseBody.create(
                        MediaType.parse("application/json"),
                        "{\"key\":[\"value\"]}"
                )
        )
    }

    private fun getMockedQueryString() = "Kyiv"

    private fun getMockedWeatherResponse(): WeatherResponse {
        val mainEntity = Main(9.92)
        val weatherEntity = Weather("few clouds")
        val weatherEntityList = arrayListOf(weatherEntity)
        val weatherListEntity = WeatherList(mainEntity, weatherEntityList, "2018-05-18 00:00:00")
        val weatherList = arrayListOf(weatherListEntity)
        return WeatherResponse(weatherList)
    }
}