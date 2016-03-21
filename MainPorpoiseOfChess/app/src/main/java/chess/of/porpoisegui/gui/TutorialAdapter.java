package chess.of.porpoisegui.gui;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class TutorialAdapter extends BaseAdapter
{
    private Context context;
    private int itemBackground;
    Integer[] imageIDs = {
            R.drawable.pic1,
            R.drawable.pic2,
            R.drawable.pic3,
            R.drawable.pic4,
            R.drawable.pic5,
    };

    public TutorialAdapter(Context c)
    {
        context = c;
    }
    // returns the number of images
    public int getCount()
    {
        return imageIDs.length;
    }
    // returns the ID of an item
    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }
    // returns an ImageView view
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(imageIDs[position]);
        //imageView.setBackgroundResource(itemBackground);
        return imageView;
    }
}