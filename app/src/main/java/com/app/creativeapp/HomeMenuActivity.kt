package com.app.creativeapp
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.app.LoginActivity
import com.app.creativeapp.ui.CategoryFragment
import com.app.creativeapp.ui.gallery.GalleryFragment
import com.app.creativeapp.ui.home.HomeFragment
import com.app.interfaces.FragmentTransactionInter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView


class HomeMenuActivity : AppCompatActivity(), FragmentTransactionInter {
    private var mHandler: Handler? = null
    private val TAG_HOME = "home"
    var CURRENT_TAG: String = TAG_HOME
    var toolbar: Toolbar? = null
    var navItemIndex = 0
    var  fab: FloatingActionButton? = null
    var drawerLayout: DrawerLayout? = null
    private lateinit var appBarConfiguration: AppBarConfiguration
    var navView: NavigationView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_menu)
         toolbar= findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        mHandler = Handler()
        val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            loadCategoryFragment()
        }
        drawerLayout = findViewById(R.id.drawer_layout)
         navView = findViewById(R.id.nav_view)
       /* val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(
                 R.id.nav_home,R.id.nav_login, R.id.nav_gallery, R.id.nav_slideshow), drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView?.setupWithNavController(navController)*/
        setUpNavigationView()
    }

    private fun loadCategoryFragment() {
        CURRENT_TAG = "CATEGORY"
        loadHomeFragment()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
    private fun setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navView?.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            // This method will trigger on item Click of navigation menu
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
                fab?.visibility = View.GONE
                //Check to see which item was being clicked and perform appropriate action
                when (menuItem.getItemId()) {
                    R.id.nav_login -> {
                        startLoginActivity()
                    }R.id.nav_profile_write -> {
                        navItemIndex = 2
                        CURRENT_TAG = "PROFILE"
                    }R.id.nav_project -> {
                        navItemIndex = 3
                        CURRENT_TAG = "PROJECT"
                    }R.id.nav_contact -> {
                        navItemIndex = 4
                        CURRENT_TAG = "CONTACT"
                    }
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false)
                } else {
                    menuItem.setChecked(true)
                }
                menuItem.setChecked(true)
                loadHomeFragment()
                return true
            }
        })
        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout!!,
            toolbar,
            R.string.openDrawer,
            R.string.closeDrawer
        ) {

        }

        //Setting the actionbarToggle to drawer layout
        drawerLayout?.setDrawerListener(actionBarDrawerToggle)

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState()
    }

    private fun startLoginActivity() {
        val intent = Intent(this,LoginActivity::class.java)
        startActivity(intent)
    }

    private fun loadHomeFragment() {
        // selecting appropriate nav menu item
//        selectNavMenu()

        // set toolbar title
//        setToolbarTitle()

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (supportFragmentManager.findFragmentByTag(CURRENT_TAG) != null) {
            drawerLayout?.closeDrawers()

            // show or hide the fab button
            toggleFab()
            return
        }


        loadFragment()

        // show or hide the fab button
        toggleFab()

        //Closing drawer on item click
        drawerLayout?.closeDrawers()

        // refresh toolbar menu
        invalidateOptionsMenu()
    }

    private fun loadFragment() {
        val fragment: Fragment = getHomeFragment()
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        /*fragmentTransaction.setCustomAnimations(
            R.anim.fade_in,
            R.anim.fade_out
        )*/
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment, CURRENT_TAG)
        fragmentTransaction.commitAllowingStateLoss()
        /*val mPendingRunnable = Runnable { // update the main content by replacing fragments
            val fragment: Fragment = getHomeFragment()
            val fragmentTransaction: FragmentTransaction =
                supportFragmentManager.beginTransaction()
            fragmentTransaction.setCustomAnimations(
                R.anim.fade_in,
                R.anim.fade_out
            )
            fragmentTransaction.replace(R.id.nav_host_fragment, fragment, CURRENT_TAG)
            fragmentTransaction.commitAllowingStateLoss()
        }
        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler?.post(mPendingRunnable)
        }*/
    }

    // show or hide the fab
    private fun toggleFab() {
        if (navItemIndex === 0) fab?.show() else fab?.hide()
    }
    private fun getHomeFragment() : Fragment {
        when(navItemIndex) {
            0 -> {

            fab?.visibility = View.VISIBLE
                return   HomeFragment()
        }
             1->
                 return LoginFragment.newInstance("","")
            2->
                 return CategoryFragment.newInstance("","")
            3->
                 return CategoryFragment.newInstance("","")
           else -> HomeFragment()

        }
        return GalleryFragment()
    }

    override fun loadNewFragment() {
        CURRENT_TAG = "WRITE"
        val fragmentTransaction: FragmentTransaction =
            supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.nav_host_fragment, WriteFragment.newInstance("",""), CURRENT_TAG)
        fragmentTransaction.commitAllowingStateLoss()
    }

}