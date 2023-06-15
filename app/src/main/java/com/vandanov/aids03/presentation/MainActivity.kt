package com.vandanov.aids03.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.vandanov.aids03.R
import com.vandanov.aids03.data.auth.ActivityRequired
import com.vandanov.aids03.databinding.ActivityMainBinding
import com.vandanov.aids03.domain.auth.repository.AuthRepository
import com.vandanov.aids03.presentation.tabs.TabsFragment
import dagger.hilt.android.AndroidEntryPoint
import java.util.regex.Pattern
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var activityRequiredStuffs: Set<@JvmSuppressWildcards ActivityRequired>

    private lateinit var binding: ActivityMainBinding

    // nav controller of the current screen
    private var navController: NavController? = null
    private val topLevelDestinations = setOf(getTabsDestination(), getSignInDestination())

    // fragment listener is sued for tracking current nav controller
    private val fragmentListener = object : FragmentManager.FragmentLifecycleCallbacks() {
        override fun onFragmentViewCreated(fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?) {
            super.onFragmentViewCreated(fm, f, v, savedInstanceState)
            if (f is TabsFragment || f is NavHostFragment) return
            onNavControllerActivated(f.findNavController())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        setSupportActionBar(binding.toolbar)

        // preparing root nav controller
        val navController = getRootNavController()
        prepareRootNavController(navController)
        onNavControllerActivated(navController)

        supportFragmentManager.registerFragmentLifecycleCallbacks(fragmentListener, true)

//        // updating username in the toolbar
//        viewModel.username.observe(this) {
//            binding.usernameTextView.text = it
//        }

        activityRequiredStuffs.forEach {
            it.onActivityCreated(this)
        }

        Log.d("MyLog", "MainActivity: $activityRequiredStuffs")

    }

    //стрелка назад
    override fun onSupportNavigateUp(): Boolean = (navController?.navigateUp() ?: false) || super.onSupportNavigateUp()

    private fun onNavControllerActivated(navController: NavController) {
        if (this.navController == navController) return
        this.navController?.removeOnDestinationChangedListener(destinationListener)
        navController.addOnDestinationChangedListener(destinationListener)
        this.navController = navController
    }

    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
        supportActionBar?.title = prepareTitle(destination.label, arguments)
        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
    }

    private fun prepareTitle(label: CharSequence?, arguments: Bundle?): String {
        // code for this method has been copied from Google sources :)
        if (label == null) return ""
        val title = StringBuffer()
        val fillInPattern = Pattern.compile("\\{(.+?)\\}")
        val matcher = fillInPattern.matcher(label)
        while (matcher.find()) {
            val argName = matcher.group(1)
            if (arguments != null && arguments.containsKey(argName)) {
                matcher.appendReplacement(title, "")
                title.append(arguments[argName].toString())
            } else {
                throw IllegalArgumentException(
                    "Could not find $argName in $arguments to fill label $label"
                )
            }
        }
        matcher.appendTail(title)
        return title.toString()
    }

    private fun getRootNavController(): NavController {
        val navHost = supportFragmentManager.findFragmentById(R.id.fragmentContainer) as NavHostFragment
        return navHost.navController
    }

    private fun prepareRootNavController(navController: NavController) {
        val graph = navController.navInflater.inflate(getMainNavigationGraphID())
        graph.setStartDestination(
//            if (isSignedIn) {
//                getTabsDestination()
//            } else {
                getSignInDestination()
//            }
        )
        navController.graph = graph
    }

//    private val destinationListener = NavController.OnDestinationChangedListener { _, destination, arguments ->
//        supportActionBar?.title = prepareTitle(destination.label, arguments)
//        supportActionBar?.setDisplayHomeAsUpEnabled(!isStartDestination(destination))
//    }
//
//    private fun onNavControllerActivated(navController: NavController) {
//        if (this.navController == navController) return
//        this.navController?.removeOnDestinationChangedListener(destinationListener)
//        navController.addOnDestinationChangedListener(destinationListener)
//        this.navController = navController
//    }

    private fun getMainNavigationGraphID(): Int = R.navigation.main_graph

    private fun getTabsDestination(): Int = R.id.tabsFragment

    private fun getSignInDestination(): Int = R.id.signInFragment

    override fun onBackPressed() {
        if (isStartDestination(navController?.currentDestination)) {
            onBackPressedDispatcher.onBackPressed()
        } else {
            navController?.popBackStack()
        }
    }

    private fun isStartDestination(destination: NavDestination?): Boolean {
        if (destination == null) return false
        val graph = destination.parent ?: return false
        val startDestinations = topLevelDestinations + graph.startDestinationId
        return startDestinations.contains(destination.id)
    }

    override fun onStart() {
        super.onStart()
        activityRequiredStuffs.forEach {
            it.onActivityStarted()
        }
    }

    override fun onStop() {
        super.onStop()
        activityRequiredStuffs.forEach {
            it.onActivityStopped()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityRequiredStuffs.forEach {
            it.onActivityDestroyed()
        }
    }

}