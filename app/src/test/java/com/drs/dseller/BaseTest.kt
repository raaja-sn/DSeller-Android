package com.drs.dseller

import org.junit.Rule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

open class BaseTest {

    @get:Rule val testDispatcherRule = DispatcherRule()


}