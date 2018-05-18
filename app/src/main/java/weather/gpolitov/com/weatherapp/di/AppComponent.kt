package weather.gpolitov.com.weatherapp.di

import dagger.BindsInstance
import dagger.Component
import weather.gpolitov.com.weatherapp.App
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(AppModule::class,
        NetworkModule::class))
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)
}