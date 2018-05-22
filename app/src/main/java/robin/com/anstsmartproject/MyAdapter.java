package robin.com.anstsmartproject;

import java.util.List;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	private LayoutInflater layoutInflater;	
	private List<OrderItem>  OrderItemList;
	private int cartecount = 0;

	public MyAdapter(Context context, List<OrderItem> OrderItemList){
		this.layoutInflater = LayoutInflater.from(context);
		this.OrderItemList = OrderItemList;			
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return OrderItemList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return OrderItemList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return OrderItemList.get(position).getId();
	}

	@Override
	public View getView(int position, View view, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHold viewHolder;
		
		/*final int nowPosition = position;
		
		if (view == null) {
			view = layoutInflater.inflate(R.layout.dishes_list, null);
			viewHolder =  new ViewHolder();
			viewHolder.tv_dish_name =  (TextView)view.findViewById(R.id.tv_dish_name);
			viewHolder.tv_dish_price =  (TextView)view.findViewById(R.id.tv_dish_price);
			viewHolder.tv_count =  (TextView)view.findViewById(R.id.tv_count);
			viewHolder.jian =  (ImageView)view.findViewById(R.id.jian);
			viewHolder.jia =  (ImageView)view.findViewById(R.id.jia);
			view.setTag(viewHolder);
		}else{
			viewHolder = (ViewHolder) view.getTag();
		}*/
		
		if(view == null){
			
			view = layoutInflater.inflate(R.layout.listview_child_view, null);
			viewHolder = new ViewHold();
			viewHolder.picture=(ImageView) view.findViewById(R.id.picture_imageView);
			viewHolder.name = (TextView) view.findViewById(R.id.watch_TV_textView);
			view.setTag(viewHolder);
		}else {
			viewHolder = (ViewHold) view.getTag();
		}
		
		String  bufString = OrderItemList.get(position).getName();
		viewHolder.name.setText(bufString);
//		viewHolder.price.setText(Float.toString(OrderItemList.get(position).getPrices()));
//		viewHolder.conutEditText.setText(Integer.toString(cartecount));
		viewHolder.picture.setImageResource(OrderItemList.get(position).getPicture());
					
		return view;
	}
	
	
	
	public class ViewHold
	{
		TextView name;
		ImageView picture;
	}
}
