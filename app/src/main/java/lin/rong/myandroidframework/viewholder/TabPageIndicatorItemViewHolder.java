package lin.rong.myandroidframework.viewholder;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.TextView;

import in.srain.cube.util.LocalDisplay;
import in.srain.cube.views.pager.TabPageIndicator;
import lin.rong.myandroidframework.R;

/**
 * Created by Administrator on 2015/6/28.
 */
public class TabPageIndicatorItemViewHolder extends TabPageIndicator.ViewHolderBase {

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
        mNameView.setText(position + 1 + "");
        if (isCurrent) {
            mTagView.setVisibility(View.VISIBLE);
        } else {
            mTagView.setVisibility(View.INVISIBLE);
        }
    }
}
