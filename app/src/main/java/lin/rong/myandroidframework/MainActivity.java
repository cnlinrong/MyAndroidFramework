package lin.rong.myandroidframework;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private ViewPager myViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myViewPager = (ViewPager) findViewById(R.id.myViewPager);
        myViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Toast.makeText(MainActivity.this, ((MyPagerAdapter) myViewPager.getAdapter()).data[position], Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }

        });
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

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public String[] data = new String[] {"页面1", "页面2", "页面3"};

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyViewPagerFragment.newInstance(data[position]);
        }

        @Override
        public int getCount() {
            return data.length;
        }

    }

    public static class MyViewPagerFragment extends Fragment {

        public static String KEY = "key";

        public MyViewPagerFragment() {}

        public static MyViewPagerFragment newInstance(String data) {
            MyViewPagerFragment fragment = new MyViewPagerFragment();
            Bundle bundle = new Bundle();
            bundle.putString(KEY, data);
            fragment.setArguments(bundle);
            return fragment;
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View root = inflater.inflate(R.layout.fragment_viewpager, null);
            Bundle bundle = getArguments();
            TextView myTextView = (TextView) root.findViewById(R.id.myTextView);
            myTextView.setText(bundle.getString(KEY, "默认内容"));
            return root;
        }

    }

}
