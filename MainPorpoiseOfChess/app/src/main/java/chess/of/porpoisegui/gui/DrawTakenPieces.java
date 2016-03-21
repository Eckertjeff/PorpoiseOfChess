package chess.of.porpoisegui.gui;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

public class DrawTakenPieces extends BaseAdapter
{
    private Context mContext;
    private int m_Width;
    private Integer[] m_ThumbIds = {R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square,
                                    R.drawable.blank_square,R.drawable.blank_square};
    private int m_Increment = 0;
    private GameLogic game;
    private boolean color; //True = White, False = Black.

    public DrawTakenPieces(Context c, boolean color)
    {
        mContext = c;
        Log.v("ConstructingTakenPieces", "Constructing");
        m_Width = 85;
        game = GameLogic.getGameLogic();
        this.color = color;
    }

    public int getCount()
    {
        return m_ThumbIds.length;
    }

    public Object getItem(int position)
    {
        return null;
    }

    public long getItemId(int position)
    {
        return 0;
    }

    public int getWidth()
    {
        return m_Width;
    }

    public void setWidth(int width)
    {
        m_Width = width;
    }
    //need to create a setview and remove the getView setup, or just use getView?

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {

        ImageView imageView = null;

        if (convertView == null)
        {
            imageView = new ImageView(mContext);

            imageView.setLayoutParams(new GridView.LayoutParams(m_Width, m_Width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);
        }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setAdjustViewBounds(true);

        //Gets the resource number from the captured piece lists of the corresponding color.
        Integer tmpResource = game.getCapturedResource(position, color);
        if(tmpResource != null)
        {
            //if there is a captured piece to put in the respective position, put it there.
            m_ThumbIds[position] = tmpResource;
            imageView.setImageResource(m_ThumbIds[position]);
        }

        return imageView;
    }

    public void setImage(Integer takenpieceres)
    {
        Log.v("Set Image", "" + takenpieceres + "Increment: " + m_Increment);
        m_ThumbIds[m_Increment] = takenpieceres;
        this.notifyDataSetChanged();
        m_Increment++;
    }

    public void resetGame()
    {
        DrawTakenPieces thistakenpieces = this;
        thistakenpieces = new DrawTakenPieces(mContext, color);
        this.notifyDataSetChanged();
    }
}
