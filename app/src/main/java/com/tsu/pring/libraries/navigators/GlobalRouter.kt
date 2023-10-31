package com.tsu.pring.libraries.navigators


interface GlobalRouter {

	fun open(destination: FragmentDestination)

	fun replace(destination: FragmentDestination)

	fun newRoot(destination: FragmentDestination)

	fun popToRoot()

	fun exit()

	fun finish()

	fun popTo(destination: Class<FragmentDestination>)
}