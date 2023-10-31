package com.tsu.pring.libraries.navigators

import androidx.fragment.app.Fragment

interface FragmentDestination {

	fun createInstance(): Fragment
}