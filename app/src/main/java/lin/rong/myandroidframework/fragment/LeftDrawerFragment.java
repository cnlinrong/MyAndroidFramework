package lin.rong.myandroidframework.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import lin.rong.myandroidframework.R;

public class LeftDrawerFragment extends Fragment {

    private final String[] data = new String[] {"选项1", "选项2", "选项3"};

    private DrawerLayout mDrawerLayout;
    private LeftDrawerFragment leftDrawerFragment;
    private ActionBarDrawerToggle mActionBarDrawerToggle;
    private ListView drawerListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        drawerListView = (ListView) inflater.inflate(R.layout.fragment_drawer_list, null);
        drawerListView.setAdapter(new DrawerListAdapter());
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data[position], Toast.LENGTH_SHORT).show();
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }

        });
        drawerListView.setItemChecked(0, true);
        return drawerListView;
    }

    public void init(DrawerLayout mDrawerLayout, LeftDrawerFragment leftDrawerFragment) {
        this.mDrawerLayout = mDrawerLayout;
        this.leftDrawerFragment = leftDrawerFragment;

        mActionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), mDrawerLayout,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

        };
        mDrawerLayout.post(new Runnable() {

            @Override
            public void run() {
                mActionBarDrawerToggle.syncState();
            }

        });
        mDrawerLayout.setDrawerListener(mActionBarDrawerToggle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class DrawerListAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return data.length;
        }

        @Override
        public Object getItem(int position) {
            return data[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View root = layoutInflater.inflate(R.layout.fragment_drawer_list_item, null);
            TextView myTextView = (TextView) root.findViewById(R.id.myTextView);
            myTextView.setText(data[position]);
            return root;
        }

    }

}
