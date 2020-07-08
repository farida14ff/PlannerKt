package com.example.plannerkt.adapters

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import java.util.*


class FixedTabsPagerAdapter(fm: FragmentManager?) :
    FragmentStatePagerAdapter(fm!!, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    private val mFragmentList: MutableList<Fragment> =
        ArrayList()
    private val mFragmentTitleList: MutableList<String> =
        ArrayList()

    fun addFragment(fragment: Fragment, title: String) {
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getItem(position: Int): Fragment {
           return mFragmentList[position]
    }

    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitleList[position]
    }

    override fun saveState(): Parcelable? {
        return null
    }
}
