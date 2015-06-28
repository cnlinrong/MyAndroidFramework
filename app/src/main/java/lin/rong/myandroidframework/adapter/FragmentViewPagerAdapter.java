package lin.rong.myandroidframework.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;

import in.srain.cube.request.JsonData;
import in.srain.cube.request.RequestFinishHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import lin.rong.myandroidframework.data.RequestData;
import lin.rong.myandroidframework.fragment.ViewPagerFragment;

/**
 * Created by Administrator on 2015/6/28.
 */
public class FragmentViewPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<ViewPagerFragment> mViewPagerFragments;
    private ViewPagerFragment mCurrentFragment;
    private PtrFrameLayout mPtrFrame;

    public FragmentViewPagerAdapter(FragmentManager fm, PtrFrameLayout mPtrFrame, ArrayList<ViewPagerFragment> list) {
        super(fm);
        this.mPtrFrame = mPtrFrame;
        mViewPagerFragments = list;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
    }

    @Override
    public Fragment getItem(int position) {
        return mViewPagerFragments.get(position);
    }

    public void updateData() {
        final ViewPagerFragment fragment = mCurrentFragment;
        RequestData.getImageList(new RequestFinishHandler<JsonData>() {

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
