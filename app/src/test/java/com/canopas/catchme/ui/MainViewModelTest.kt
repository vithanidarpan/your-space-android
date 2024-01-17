package com.canopas.catchme.ui

import com.canopas.catchme.MainCoroutineRule
import com.canopas.catchme.data.storage.UserPreferences
import com.canopas.catchme.ui.navigation.MainNavigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
class MainViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel

    private val navigator = mock<MainNavigator>()
    private val userPreferences = mock<UserPreferences>()

    @Test
    fun `should navigate to intro screen if intro is not shown`() = runTest {
        whenever(userPreferences.isIntroShown()).thenReturn(false)
        viewModel = MainViewModel(userPreferences, navigator)
        verify(navigator).navigateTo(
            "intro",
            popUpToRoute = "home",
            inclusive = true
        )
    }

    @Test
    fun `should not navigate to intro screen if intro is shown`() = runTest {
        whenever(userPreferences.isIntroShown()).thenReturn(true)
        whenever(userPreferences.isOnboardShown()).thenReturn(true)

        viewModel = MainViewModel(userPreferences, navigator)
        verify(navigator, times(0)).navigateTo(
            "intro",
            popUpToRoute = "home",
            inclusive = true
        )
    }

    @Test
    fun `should navigate to onboard screen if onboard is not shown`() = runTest {
        whenever(userPreferences.currentUser).thenReturn(mock())
        whenever(userPreferences.isIntroShown()).thenReturn(true)
        whenever(userPreferences.isOnboardShown()).thenReturn(false)
        viewModel = MainViewModel(userPreferences, navigator)
        verify(navigator).navigateTo(
            "onboard",
            popUpToRoute = "home",
            inclusive = true
        )
    }

    @Test
    fun `should not navigate to onboard screen if onboard is shown`() = runTest {
        whenever(userPreferences.isIntroShown()).thenReturn(true)
        whenever(userPreferences.isOnboardShown()).thenReturn(true)

        viewModel = MainViewModel(userPreferences, navigator)
        verify(navigator, times(0)).navigateTo(
            "onboard",
            popUpToRoute = "home",
            inclusive = true
        )
    }

    @Test
    fun `should navigate to signIn screen if user is null`() = runTest {
        whenever(userPreferences.isIntroShown()).thenReturn(true)
        whenever(userPreferences.currentUser).thenReturn(null)

        viewModel = MainViewModel(userPreferences, navigator)
        verify(navigator).navigateTo(
            "sign-in",
            popUpToRoute = "home",
            inclusive = true
        )
    }
}