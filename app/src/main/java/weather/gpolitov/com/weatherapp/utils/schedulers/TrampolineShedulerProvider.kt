package weather.gpolitov.com.weatherapp.utils.schedulers

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class TrampolineShedulerProvider : BaseShedulerProvider {

    override fun computation(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun io(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun ui(): Scheduler {
        return Schedulers.trampoline()
    }

    override fun multi(): Scheduler {
        return Schedulers.trampoline()
    }
}