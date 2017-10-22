package jasonzou.demo.mview.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.*;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import jasonzou.demo.mview.fragment.MFragment;
import jasonzou.demo.view.R;

import java.util.ArrayList;
import java.util.List;

public class MTabLayout extends FragmentActivity {
    private static final String TAG = "MTabLayout";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;

    private LayoutInflater mInflater;
    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private View view1, view2, view3, view4, view5;//页卡视图
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    List<MFragment> fragments = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_m_tab_layout);

        mViewPager = (ViewPager) findViewById(R.id.vp_view);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        initViewPagerSViews();
        for (int i = 0; i < 11; i++) {
            mTitleList.add("No:" + (i + 1));

            fragments.add(new MFragment());
            Bundle bundle = new Bundle();
            bundle.putString("title", "No:" + (i + 1));
            fragments.get(i).setArguments(bundle);
        }

        MFragmentPagerAdapter mAdapter = new MFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);//给ViewPager设置适配器
        mTabLayout.setupWithViewPager(mViewPager);//将TabLayout和ViewPager关联起来。
        //        MTabLayout.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器 ，废弃，使用viewpager的适配器即可
    }


    /**
     * 生成ViewPager的views
     */
    private void initViewPagerSViews() {
        mInflater = LayoutInflater.from(this);
        view1 = mInflater.inflate(R.layout.activity_main, null);
        view2 = mInflater.inflate(R.layout.activity_main, null);
        view3 = mInflater.inflate(R.layout.activity_main, null);
        view4 = mInflater.inflate(R.layout.activity_main, null);
        view5 = mInflater.inflate(R.layout.activity_main, null);

        //添加页卡视图
        mViewList.add(view1);
        mViewList.add(view2);
        mViewList.add(view3);
        mViewList.add(view4);
        mViewList.add(view5);
    }

    /**
     * Fragment
     */
    class MFragmentPagerAdapter extends FragmentStatePagerAdapter {
        public MFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
            //结合TabLayout使用
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            Log.i(TAG, "destroyItem: 销毁pos=" + position);
            super.destroyItem(container, position, object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Log.i(TAG, "destroyItem: 初始化pos=" + position);
            return super.instantiateItem(container, position);
        }
    }


    //ViewPager适配器
    class MyPagerAdapter extends PagerAdapter {
        private List<View> mViewList;

        public MyPagerAdapter(List<View> mViewList) {
            this.mViewList = mViewList;
        }

        @Override
        public int getCount() {
            return mViewList.size();//页卡数
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;//官方推荐写法
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mViewList.get(position));//添加页卡
            return mViewList.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(mViewList.get(position));//删除页卡
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitleList.get(position);//页卡标题
        }

    }
}
