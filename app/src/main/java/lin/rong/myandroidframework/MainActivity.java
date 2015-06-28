package lin.rong.myandroidframework;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.pager.TabPageIndicator;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

public class MainActivity extends FragmentActivity {

    private String[] data = new String[]{"页面1", "页面2", "页面3"};

    private ViewPager myViewPager;
    private ActionBar actionBar;
    private DrawerLayout myDrawerLayout;
    private MyDrawerFragment myDrawerFragment;
    private TabPageIndicator mCatTabPageIndicator;
    private PtrFrameLayout mPtrFrame;
    private FragmentViewPagerAdapter mPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int startIndex = 0;

        mCatTabPageIndicator = (TabPageIndicator) findViewById(R.id.view_pager_tab_indicator);
        ArrayList<ViewPagerFragment> list = new ArrayList<ViewPagerFragment>();

        for (int i = 1; i <= 8; i++) {
            list.add(ViewPagerFragment.create(i));
        }
        mPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), list);
        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        myViewPager.setAdapter(mPagerAdapter);

        mCatTabPageIndicator.setViewHolderCreator(new TabPageIndicator.ViewHolderCreator() {

            @Override
            public TabPageIndicator.ViewHolderBase createViewHolder() {
                return new HomeCatItemViewHolder();
            }

        });
        mCatTabPageIndicator.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

            @Override
            public void onPageSelected(int i) {
                switchTo(i);
            }

        });
        mCatTabPageIndicator.setViewPager(myViewPager);

        mPtrFrame = (PtrClassicFrameLayout) findViewById(R.id.view_pager_ptr_frame);
        mPtrFrame.disableWhenHorizontalMove(true);
        mPtrFrame.setPtrHandler(new PtrHandler() {

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return mPagerAdapter.checkCanDoRefresh();
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                mPagerAdapter.updateData();
            }

        });

        myDrawerLayout = (DrawerLayout) findViewById(R.id.myDrawerLayout);
        myDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);

        myDrawerFragment = (MyDrawerFragment) getFragmentManager().findFragmentById(R.id.myDrawerFragment);
        myDrawerFragment.init(myDrawerLayout, myDrawerFragment);

        actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        myViewPager.setCurrentItem(startIndex);
    }

    private void switchTo(int position) {
        mPagerAdapter.switchTO(position);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Toast.makeText(MainActivity.this, "我的安卓框架", Toast.LENGTH_LONG).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCatTabPageIndicator.moveToItem(myViewPager.getCurrentItem());
    }

    private class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

        private final ArrayList<ViewPagerFragment> mViewPagerFragments;
        private ViewPagerFragment mCurrentFragment;

        public FragmentViewPagerAdapter(FragmentManager fm, ArrayList<ViewPagerFragment> list) {
            super(fm);
            mViewPagerFragments = list;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
        }

        @Override
        public Fragment getItem(int position) {
            return mViewPagerFragments.get(position);
        }

        protected void updateData() {
            final ViewPagerFragment fragment = mCurrentFragment;
            DemoRequestData.getImageList(new RequestFinishHandler<JsonData>() {

                @Override
                public void onRequestFinish(final JsonData data) {
                    if (fragment == mCurrentFragment) {
                        fragment.update(data);
                        mPtrFrame.refreshComplete();
                    }
                }

            });
        }

        @Override
        public int getCount() {
            return mViewPagerFragments.size();
        }

        public void switchTO(final int position) {
            int len = mViewPagerFragments.size();
            for (int i = 0; i < len; i++) {
                ViewPagerFragment fragment = mViewPagerFragments.get(i);
                if (i == position) {
                    mCurrentFragment = fragment;
                    fragment.show();
                } else {
                    fragment.hide();
                }
            }
        }

        public boolean checkCanDoRefresh() {
            if (mCurrentFragment == null) {
                return true;
            }
            return mCurrentFragment.checkCanDoRefresh();
        }
    }

    private class HomeCatItemViewHolder extends TabPageIndicator.ViewHolderBase {

        private TextView mNameView;
        private View mTagView;

        @Override
        public View createView(LayoutInflater layoutInflater, int position) {
            View view = layoutInflater.inflate(R.layout.view_pager_indicator_item, null);
            view.setLayoutParams(new AbsListView.LayoutParams(LocalDisplay.dp2px(40), -1));
            mNameView = (TextView) view.findViewById(R.id.view_pager_indicator_name);
            mTagView = view.findViewById(R.id.view_pager_indicator_tab_current);
            return view;
        }

        @Override
        public void updateView(int position, boolean isCurrent) {
            mNameView.setText(position + "");
            if (isCurrent) {
                mTagView.setVisibility(View.VISIBLE);
            } else {
                mTagView.setVisibility(View.INVISIBLE);
            }
        }
    }

}
