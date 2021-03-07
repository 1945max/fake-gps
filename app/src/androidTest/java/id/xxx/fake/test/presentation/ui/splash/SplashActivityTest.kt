package id.xxx.fake.test.presentation.ui.splash

import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SplashActivityTest {
    @get:Rule
    val activityScenario = ActivityScenarioRule(SplashActivity::class.java)

    @Test
    fun test() {
        Thread.sleep(1000)
    }
}