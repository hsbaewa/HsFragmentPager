package kr.co.hs.fragmentpager.sample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.ViewGroup;
import android.widget.TextView;

import kr.co.hs.fragmentpager.HsFragmentPager;
import kr.co.hs.fragmentpager.app.HsFragmentPagerActivity;

/**
 * 생성된 시간 2017-01-04, Bae 에 의해 생성됨
 * 프로젝트 이름 : HsFragmentPager2
 * 패키지명 : kr.co.hs.fragmentpager.sample
 */

public class SampleActivity extends HsFragmentPagerActivity{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //파라미터 순서 - Activity 레이아웃, TabLayout 리소스 아이디, HsFragmentPager 리소스 아이디
        setContentView(R.layout.activity_sample, R.id.TabLayout, R.id.HsFragmentPager);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreatePageFragment(HsFragmentPagerActivity.HsFragmentPagerActivityAdapter mAdapter) {
        //여기서 추가할 Fragment를 추가해줍니다.
        mAdapter.addFragment("타이틀1", TabFragment.newInstance("타이틀1"));
        mAdapter.addFragment("타이틀2", TabFragment.newInstance("타이틀2"));
        mAdapter.addFragment("타이틀3", TabFragment.newInstance("타이틀3"));
    }

    static class TabFragment extends HsFragmentPager.HsPageFragment{
        private TextView mTextView;
        public static TabFragment newInstance(String value) {
            Bundle args = new Bundle();
            args.putString("value", value);
            TabFragment fragment = new TabFragment();
            fragment.setArguments(args);
            return fragment;
        }
        @Override
        public void onCreateView(@Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
            setContentView(R.layout.fragment_tab);
            mTextView = (TextView) findViewById(R.id.textView);
            Bundle args = getArguments();
            mTextView.setText(args.getString("value"));
        }
        @Override
        public boolean onBackPressed() {
//            return super.onBackPressed();
            if(mTextView.getText().toString().equals("뒤로")){
                return true;
            }else{
                mTextView.setText("뒤로");
                return false;
            }
        }
    }
}
