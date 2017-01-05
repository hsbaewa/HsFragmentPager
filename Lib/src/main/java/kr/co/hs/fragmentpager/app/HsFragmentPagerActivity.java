package kr.co.hs.fragmentpager.app;

import android.support.annotation.LayoutRes;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.List;

import kr.co.hs.app.HsActivity;
import kr.co.hs.fragmentpager.HsFragmentPager;

/**
 * 생성된 시간 2017-01-04, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsFragmentPager2
 * 패키지명 : kr.co.hs.fragmentpager.app
 */

public abstract class HsFragmentPagerActivity extends HsActivity {

    private TabLayout mTabLayout;
    private HsFragmentPager mHsFragmentPager;
    private final HsFragmentPagerActivityAdapter mHsFragmentStatePagerAdapter = new HsFragmentPagerActivityAdapter(getSupportFragmentManager());


    public HsFragmentPager getHsFragmentPager() {
        return mHsFragmentPager;
    }
    public void setHsFragmentPager(HsFragmentPager hsFragmentPager) {
        mHsFragmentPager = hsFragmentPager;
    }

    public void setContentView(@LayoutRes int layoutResID, int tabLayout, int hsFragmentPagerID) {
        super.setContentView(layoutResID);
        mTabLayout = (TabLayout) findViewById(tabLayout);
        mHsFragmentPager = (HsFragmentPager) findViewById(hsFragmentPagerID);

        onCreatePageFragment(mHsFragmentStatePagerAdapter);
        if(mHsFragmentPager != null){
            mHsFragmentPager.setAdapter(mHsFragmentStatePagerAdapter);
            if(mTabLayout != null)
                mTabLayout.setupWithViewPager(mHsFragmentPager);
        }
    }

    protected void notifyDataSetChanged(){
        mHsFragmentStatePagerAdapter.notifyDataSetChanged();
    }

    protected void notifyDataSetChanged(int position){
        mHsFragmentStatePagerAdapter.notifyDataSetChanged(position);
    }

    public HsFragmentPagerActivityAdapter getHsFragmentStatePagerAdapter() {
        return mHsFragmentStatePagerAdapter;
    }

    protected abstract void onCreatePageFragment(HsFragmentPagerActivityAdapter mAdapter);



    public class HsFragmentPagerActivityAdapter extends HsFragmentPager.HsFragmentStatePagerAdapter{
        final private List<HsFragmentPager.HsPageFragment> mTabs;
        final private List<String> mTabTitle;

        public HsFragmentPagerActivityAdapter(FragmentManager fm) {
            super(fm);
            mTabs = new ArrayList<>();
            mTabTitle = new ArrayList<>();
        }

        @Override
        public HsFragmentPager.HsPageFragment getHsPageFragment(int position) {
            return mTabs.get(position);
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTabTitle.get(position);
        }

        public void addFragment(String title, HsFragmentPager.HsPageFragment fragment){
            mTabTitle.add(title);
            mTabs.add(fragment);
            notifyDataSetChanged();
        }
    }

}
