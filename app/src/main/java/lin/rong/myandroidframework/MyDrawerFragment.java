package lin.rong.myandroidframework;

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

public class MyDrawerFragment extends Fragment {

    private String[] data = new String[] {"选项1", "选项2", "选项3"};

    private DrawerLayout myDrawerLayout;
    private MyDrawerFragment myDrawerFragment;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private ListView drawerListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        drawerListView = (ListView) inflater.inflate(R.layout.fragment_drawer_list, null);
        drawerListView.setAdapter(new MyAdapter());
        drawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity(), data[position], Toast.LENGTH_SHORT).show();
                myDrawerLayout.closeDrawer(GravityCompat.START);
            }

        });
        return drawerListView;
    }

    public void init(DrawerLayout myDrawerLayout, MyDrawerFragment myDrawerFragment) {
        this.myDrawerLayout = myDrawerLayout;
        this.myDrawerFragment = myDrawerFragment;

        actionBarDrawerToggle = new ActionBarDrawerToggle(getActivity(), myDrawerLayout,
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
        myDrawerLayout.post(new Runnable() {

            @Override
            public void run() {
                actionBarDrawerToggle.syncState();
            }

        });
        myDrawerLayout.setDrawerListener(actionBarDrawerToggle);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setHasOptionsMenu(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyAdapter extends BaseAdapter {

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
