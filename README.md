# HsFragmentPager

ViewPager, FragmentPagerAdapter, TabLayout을 사용하기 쉽도록 제작한 라이브러리 입니다.

자세한 사용법은 Sample프로젝트를 참조하세요.


##Dependency 설정

[![](https://jitpack.io/v/hsbaewa/HsFragmentPager.svg)](https://jitpack.io/#hsbaewa/HsFragmentPager)

* root 수준의 build.gradle 설정
<pre><code>
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
</code></pre>

* 프로젝트 단위의 build.gradle 설정
<pre><code>
dependencies {
	        compile 'com.github.hsbaewa:HsFragmentPager:0.0.1'
	}
	</code></pre>

##HsFragmentPagerActivity 사용법
* HsFragmentPagerActivity 안에 TabLayout과 HsFragmentPager가 내장되어 있으며 create 단계에서 리소스 아이디만 명시하면됨.

<pre><code>
public class SampleActivity extends HsFragmentPagerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //파라미터 순서 - Activity 레이아웃, TabLayout 리소스 아이디, HsFragmentPager 리소스 아이디
        setContentView(R.layout.activity_sample, R.id.TabLayout, R.id.HsFragmentPager);
    }

    @Override
    protected void onCreatePageFragment(HsFragmentPagerActivity.HsFragmentPagerActivityAdapter mAdapter) {
        //여기서 추가할 Fragment를 추가해줍니다.
        mAdapter.addFragment("타이틀1", TabFragment.newInstance("타이틀1"));
        mAdapter.addFragment("타이틀2", TabFragment.newInstance("타이틀2"));
        mAdapter.addFragment("타이틀3", TabFragment.newInstance("타이틀3"));
    }
}
</code></pre>

*HsFragmentPager 안에 들어가는 Fragment로는 HsPageFragment를 사용
<pre><code>
class TabFragment extends HsFragmentPager.HsPageFragment{

        @Override
        public void onCreateView(@Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
            setContentView(R.layout.레이아웃);  //Activity와 비슷하게 사용할수 있도록 setContentView 함수 내장
            TextView v = findViewById(R.id.textView);  //findViewById 함수도 자체 내장
        }
    }
</code></pre>

*특정 position에 해당하는 fragment를 새로고침하고 싶을때 아래와 같이 호출
<pre><code>
//HsFragmentPagerActivity 내장함수
notifyDataSetChanged((int)새로고침하고 싶은 position);
</code></pre>

*모든 fragment 새로고침
<pre><code>
//HsFragmentPagerActivity 내장함수
HsFragmentPagerAdapter.notifyDataSetChanged();
</code></pre>