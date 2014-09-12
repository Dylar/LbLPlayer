package de.lbl.LbLPlayer.gui;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import de.lbl.LbLPlayer.*;
import java.util.*;

public class DrawerMenuAdapter extends BaseAdapter
{

	Context context;
	List<DrawerRowInfo> rowItem;

	public DrawerMenuAdapter(Context context, List<DrawerRowInfo> rowItem)
	{
		this.context = context;
		this.rowItem = rowItem;
	}


	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		if (convertView == null)
		{
            LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.drawer_row, null);
        }

        ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.title);

		convertView.setBackgroundColor(context.getResources().getColor(R.color.aqua));
		DrawerRowInfo row_pos = rowItem.get(position);
        // setting the image resource and title
        imgIcon.setImageResource(row_pos.icon);
        txtTitle.setText(row_pos.title);

        return convertView;
	}

	@Override
	public int getCount()
	{
		return rowItem.size();
	}

	@Override
	public Object getItem(int position)
	{
		return rowItem.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return rowItem.indexOf(getItem(position));
	}

}


