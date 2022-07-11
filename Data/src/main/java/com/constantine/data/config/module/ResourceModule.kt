package com.constantine.data.config.module

import com.constantine.data.config.scope.Rest
import com.constantine.data.content.Resource
import com.constantine.data.resource.MainResource
import com.constantine.data.resource.ServiceResource
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ResourceModule {
    @Binds
    @IntoMap
    @Singleton
    @Rest("/")
    abstract fun provideMainResource(resource: MainResource): Resource

    @Binds
    @IntoMap
    @Singleton
    @Rest("/services")
    abstract fun provideCallResource(resource: ServiceResource): Resource
}
