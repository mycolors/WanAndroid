package com.fengniao.wanandroid

import android.os.Bundle
import com.fengniao.mysdk.MyUtils
import com.fengniao.wanandroid.base.BaseActivity
import com.fengniao.wanandroid.ui.HomeFragment

class MainActivity : BaseActivity() {

    lateinit var homeFragment: HomeFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
                .add(R.id.container, homeFragment)
                .show(homeFragment).commit()
        MyUtils.showToast(this, "This is my first SDK method")
    }
}
