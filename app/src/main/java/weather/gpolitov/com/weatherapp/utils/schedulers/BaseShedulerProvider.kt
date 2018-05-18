package weather.gpolitov.com.weatherapp.utils.schedulers

import io.reactivex.Scheduler

interface BaseShedulerProvider {

    abstract fun computation(): Scheduler

    abstract fun multi(): Scheduler

    abstract fun io(): Scheduler

    abstract fun ui(): Scheduler
}