package zgc.org.demo.widget.tag;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import java.util.List;

import zgc.org.demo.R;

/**
 * Author:Nick
 * Time2018/11/5 14:47
 * Description
 */
public class FlowLayoutAdapter extends BaseAdapter {
    private Context mContext;
    private List<String> mList;

    public FlowLayoutAdapter(Context context, List<String> list) {
        mContext = context;
        mList = list;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public String getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_tag, null);
            holder = new ViewHolder();
            holder.mBtnTag = (Button) convertView.findViewById(R.id.btn_tag);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.mBtnTag.setText(getItem(position));
        return convertView;
    }

    static class ViewHolder {
        Button mBtnTag;
    }
}
