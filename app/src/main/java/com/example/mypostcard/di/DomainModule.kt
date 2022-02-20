package app.di

import com.example.domain.LoadDetailsDomain
import com.example.domain.LoadOverviewDomain
import org.koin.dsl.module


val domainModule = module {

    factory<LoadOverviewDomain> {
        LoadOverviewDomain(repository = get())
    }

    factory<LoadDetailsDomain> {
        LoadDetailsDomain(repository = get())
    }
}