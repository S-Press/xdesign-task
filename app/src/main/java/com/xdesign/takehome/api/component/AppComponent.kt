package com.xdesign.takehome.api.component

import android.content.Context
import com.xdesign.takehome.api.modules.DataProviderModule
import com.xdesign.takehome.api.modules.NetworkModule
import com.xdesign.takehome.api.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 * Component used to bring provided dependencies to a given instance
 */
@Singleton
@Component(modules = [(NetworkModule::class), (ViewModelModule::class), (DataProviderModule::class)])
interface AppComponent {
    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }
}