package com.constantine.data.server

import com.constantine.data.server.content.Resource
import com.constantine.data.server.resource.MainResource
import com.constantine.data.server.resource.ServiceResource
import com.constantine.data.server.scope.Rest
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
