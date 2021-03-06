package kr.co.hs.fragmentpager;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import java.util.HashMap;

import kr.co.hs.app.HsFragment;
import kr.co.hs.fragmentpager.app.HsFragmentPagerActivity;

/**
 * 생성된 시간 2017-01-04, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsFragmentPager
 * 패키지명 : kr.co.hs.view.fragmentpager
 */

public class HsFragmentPager extends ViewPager {

    private PageWatcher mPageWatcher = null;

    public HsFragmentPager(Context context) {
        super(context);
    }

    public HsFragmentPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setOnPageChangedListener(OnPageChangedListener listener){
        mPageWatcher = new PageWatcher(this, listener);
        super.addOnPageChangeListener(mPageWatcher);
    }

    public interface OnPageChangedListener{
        void onPageChanged(HsFragmentPager viewPager, int position);
    }



    class PageWatcher implements OnPageChangeListener {

        private HsFragmentPager mFragmentPager;
        private OnPageChangedListener mListener;
        private int currentScrollState = 0;
        private int currentPageSelected = 0;

        public PageWatcher(HsFragmentPager mHsFragmentPager, OnPageChangedListener mListener) {
            this.mFragmentPager = mHsFragmentPager;
            this.mListener = mListener;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentPageSelected = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            currentScrollState = state;
            if(currentScrollState == SCROLL_STATE_IDLE && this.mListener != null){
                this.mListener.onPageChanged(mFragmentPager, currentPageSelected);
            }
        }
    }


    public static abstract class HsPageFragment extends HsFragment implements IHsPageFragment{
        int mPagerAdapterPosition;

        @Override
        public void setPagerAdapterPosition(int position) {
            this.mPagerAdapterPosition = position;
        }
        @Override
        public int getPagerAdapterPosition(){
            return this.mPagerAdapterPosition;
        }

        @Override
        public HsFragmentPager getFragmentPager() {
            HsFragmentPagerActivity activity = (HsFragmentPagerActivity) getHsActivity();
            if(activity != null)
                return activity.getHsFragmentPager();
            else
                return null;
        }

        @Override
        public HsFragmentStatePagerAdapter getFragmentStatePagerAdapter() {
            HsFragmentPagerActivity activity = (HsFragmentPagerActivity) getHsActivity();
            if(activity != null)
                return activity.getHsFragmentStatePagerAdapter();
            else
                return null;
        }

        @Override
        public boolean isPageVisible() {
            return this.mPagerAdapterPosition==getFragmentPager().getCurrentItem();
        }
    }

    public interface IHsPageFragment{
        void setPagerAdapterPosition(int position);
        int getPagerAdapterPosition();
        HsFragmentPager getFragmentPager();
        HsFragmentStatePagerAdapter getFragmentStatePagerAdapter();
        boolean isPageVisible();
    }


    public static abstract class HsFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private HashMap<Integer, Boolean> mReloadState;

        public HsFragmentStatePagerAdapter(FragmentManager fm) {
            super(fm);
            mReloadState = new HashMap<>();
        }

        @Override
        public Fragment getItem(int position) {
            IHsPageFragment hsFragment = getHsPageFragment(position);
            hsFragment.setPagerAdapterPosition(position);
            mReloadState.put(position, false);
            return (Fragment) hsFragment;
        }

        @Override
        public int getItemPosition(Object object) {
            if(object instanceof IHsPageFragment){
                IHsPageFragment hsFragment = (IHsPageFragment) object;
                if(isReloadState(hsFragment)){
                    setReloadState(hsFragment.getPagerAdapterPosition(), false);
                    return POSITION_NONE;
                }
            }
            return super.getItemPosition(object);
        }

        private boolean isReloadState(IHsPageFragment fragment){
            try{
                int position = fragment.getPagerAdapterPosition();
                if(position < 0)
                    return false;
                return mReloadState.get(position);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
        }
        private void setReloadState(int position, boolean isReload){
            mReloadState.put(position, isReload);
        }

        public void notifyDataSetChanged(int position) {
            setReloadState(position, true);
            super.notifyDataSetChanged();
        }
        @Override
        public void notifyDataSetChanged() {
            for(int i=0;i<getCount();i++){
                setReloadState(i, true);
            }
            super.notifyDataSetChanged();
        }

        public abstract IHsPageFragment getHsPageFragment(int position);
    }
}
