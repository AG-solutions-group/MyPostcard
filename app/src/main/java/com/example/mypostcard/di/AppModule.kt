package app.di

import app.presentation.ArchiveVM
import com.example.domain.LoadOverviewDomain
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    viewModel<ArchiveVM>() {
        ArchiveVM(
            getOverviewData = get(),
            getDetailsData = get(),
        )
    }
}