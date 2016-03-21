package chess.of.porpoisegui.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;
import chess.of.porpoise.porpoiseofchess.pieces.Piece;

/**
 * Created by Jeffrey on 11/1/2015.
 */
public class DrawGameBoard extends BaseAdapter
{
    private Context m_Context;
    private GameLogic Game;
    private Integer[] m_ThumbIds;
    private ImageView m_PrevView;
    private int m_Width;
    private DrawTakenPieces m_TakenList1, m_TakenList2;
    private String m_player1name, m_player2name;

    static int m_row;
    static int m_offset;

    public DrawGameBoard(Context c, DrawTakenPieces TakenList1, DrawTakenPieces TakenList2, String player1, String player2)
    {
        Game = GameLogic.getGameLogic();
        m_Context = c;

        m_PrevView = null;

        m_Width = 85;
        m_row = 0;
        m_offset = 7;

        m_ThumbIds = new Integer[64];

        m_TakenList1 = TakenList1;
        m_TakenList2 = TakenList2;

        m_player1name = player1;
        m_player2name = player2;
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

    public void setBoxSize(Display display)
    {

        //when display is portrait use width, else use height as we want the smallest dimension to avoid scrolling
        if(display.getWidth() < display.getHeight())
        {
            m_Width = display.getWidth() / 12;
        }
        else
        {
            m_Width = display.getHeight() / 12;
        }

    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent)
    {
        final ImageView imageView;

        //Turn integer position from gridview into position class.
        final Position thisPos = new Position(position);

        if (convertView == null)
        {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(m_Context);

            imageView.setLayoutParams(new GridView.LayoutParams(m_Width, m_Width));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(0, 0, 0, 0);

            imageView.setOnClickListener(new View.OnClickListener()
            {
                public void onClick(View V)
                {
                    Log.v("Clicked Position", String.valueOf(thisPos));
                    //When a square is clicked, check to see if there's a piece at the clicked position, or if there was a previously selected position.
                    //If there is a piece at the clicked position, and there is no previously selected position, check to see if it's color matches the color who's turn it is.
                    if (m_PrevView != null || ((Game.getPieceAtPosition(thisPos) != null) && Game.getPieceAtPosition(thisPos).isWhite() == Game.isWhiteTurn()))
                    {
                        //If it is the correct player's turn and there is no previously selected piece, highlight the piece and mark it as selected.
                        if (Game.getCurrentSelectedPosition() == null)
                        {
                            imageView.setColorFilter(0xff00ffff, PorterDuff.Mode.MULTIPLY);
                            Game.setCurrentSelectedPosition(thisPos);
                            m_PrevView = imageView;
                            Log.v("SelectedPosSet", "Changed currentselectedpos from null to " + thisPos.toString());
                        }
                        //If the same square is clicked twice, clear the selection.
                        else if (Game.getCurrentSelectedPosition().equals(thisPos))
                        {
                            Game.setCurrentSelectedPosition(null);
                            m_PrevView = null;
                            //bug fix, if this isn't cleared, its stays highlighted and will show up when a piece is moved to it.
                            imageView.setColorFilter(null);
                            Log.v("SelectedPosNeutralized", "currentselectedpos set to null");
                        }
                        else //not equal or null
                        {
                            Piece movingpiece = Game.getPieceAtPosition(Game.getCurrentSelectedPosition());
                            if (movingpiece.getPossibleMoves(Game).contains(thisPos.hashCode()))
                            {
                                if(Game.getPieceAtPosition(thisPos) != null)
                                {
                                    //If we can move to this position, and there is a piece here, it must mean we can capture it.
                                    boolean capturing = true;
                                    Piece capturedpiece = Game.getPieceAtPosition(thisPos);
                                    Log.v("Start Capture", "There's a piece at:" + thisPos.toString());
                                    if (capturedpiece.isWhite())
                                    {
                                        Log.v("Capturing White", "Captured: "+capturedpiece.toString());
                                        m_TakenList2.setImage(capturedpiece.getImageResource());
                                        Game.removePieceOnBoard(thisPos, capturing);
                                        //Check to see if the king was captured.
                                        if(capturedpiece.getPiecetype() == Piece.PieceType.KING)
                                        {
                                            //If the king is captured, show a dialog box to announce the winner and ask what to do next.
                                            AlertDialog.Builder builder = new AlertDialog.Builder(m_Context);
                                            builder.setMessage(R.string.win_black)
                                            .setPositiveButton(R.string.game_new, new DialogInterface.OnClickListener()
                                            {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which)
                                                {
                                                    Intent intent = new Intent(m_Context, MainBoard.class);
                                                    //Make sure to save our player's names for the new game.
                                                    intent.putExtra("FIRST_PLAYER", m_player1name);
                                                    intent.putExtra("SECOND_PLAYER", m_player2name);
                                                    //Reset everything we need for the next game.
                                                    resetGame();
                                                    notifyDataSetChanged();
                                                    //Start the new game activity.
                                                    m_Context.startActivity(intent);
                                                }
                                            })
                                            .setNegativeButton(R.string.game_quit, (new DialogInterface.OnClickListener()
                                            {
                                                  @Override
                                                  public void onClick(DialogInterface dialog, int which)
                                                  {
                                                      resetGame();
                                                      m_Context.startActivity(new Intent(m_Context, StartUpActivity.class));
                                                  }
                                            }));
                                            builder.show();
                                        }
                                    }
                                    else
                                    {
                                        Log.v("Capturing Black", "Captured: "+capturedpiece.toString());
                                        m_TakenList1.setImage(capturedpiece.getImageResource());
                                        Game.removePieceOnBoard(thisPos, capturing);
                                        //Check to see if the king was captured.
                                        if(capturedpiece.getPiecetype() == Piece.PieceType.KING)
                                        {
                                            //If the king is captured, show a dialog box to announce the winner and ask what to do next.
                                            AlertDialog.Builder builder = new AlertDialog.Builder(m_Context);
                                            builder.setMessage(R.string.win_white)
                                                    .setPositiveButton(R.string.game_new, new DialogInterface.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which)
                                                        {
                                                            Intent intent = new Intent(m_Context, MainBoard.class);
                                                            //Make sure to save our player's names for the new game.
                                                            intent.putExtra("FIRST_PLAYER", m_player1name);
                                                            intent.putExtra("SECOND_PLAYER", m_player2name);
                                                            //Reset everything we need for the next game.
                                                            resetGame();
                                                            notifyDataSetChanged();
                                                            //Start the new game activity.
                                                            m_Context.startActivity(intent);
                                                        }
                                                    })
                                                    .setNegativeButton(R.string.game_quit, (new DialogInterface.OnClickListener()
                                                    {
                                                        @Override
                                                        public void onClick(DialogInterface dialog, int which) {
                                                            resetGame();
                                                            m_Context.startActivity(new Intent(m_Context, StartUpActivity.class));
                                                        }
                                                    }));
                                            builder.show();
                                        }
                                    }
                                }

                                //Move is legal, now we actually need to move the piece.
                                Log.v("Piece Hashmap", movingpiece.toString());

                                Game.putPieceOnBoard(thisPos, movingpiece);

                                Log.v("Piece Movement", "Moved Piece from" + Game.getCurrentSelectedPosition() + " to " + thisPos);

                                m_ThumbIds[thisPos.getPos()] = m_ThumbIds[Game.getCurrentSelectedPosition().getPos()];
                                imageView.setImageResource(m_ThumbIds[thisPos.getPos()]);
                                m_ThumbIds[Game.getCurrentSelectedPosition().getPos()] = 0;
                                m_PrevView.setImageResource(0);
                                m_PrevView.setColorFilter(null);
                                Game.removePieceOnBoard(Game.getCurrentSelectedPosition());

                                Game.setCurrentSelectedPosition(null);
                                m_PrevView = null;
                                Log.v("SelectedPosSet", "Moved a piece, then set Current Selected Position to null.");
                                //The piece has been moved, and we've cleaned up after ourselves, now let's complete our turn.
                                Game.completeTurn(movingpiece);

                                //Now we need to show the user the turncounter has been updated.
                                //We get the textview by casting the context to an an activity so we can use findViewById to get the turncount textview.
                                TextView turncount = (TextView) ((Activity)m_Context).findViewById(R.id.turnCount);
                                turncount.setText("" + Game.getTurnCount());

                                //Next we need to update the name textview to signify that it's the other player's turn.
                                if(Game.isWhiteTurn())
                                {
                                    TextView player1 = (TextView) ((Activity)m_Context).findViewById(R.id.player1);
                                    TextView player2 = (TextView) ((Activity)m_Context).findViewById(R.id.player2);
                                    player1.setTextColor(Color.YELLOW);
                                    player2.setTextColor(Color.BLACK);
                                }
                                else
                                {
                                    TextView player2 = (TextView) ((Activity)m_Context).findViewById(R.id.player2);
                                    TextView player1 = (TextView) ((Activity)m_Context).findViewById(R.id.player1);
                                    player2.setTextColor(Color.YELLOW);
                                    player1.setTextColor(Color.BLACK);

                                }
                                notifyDataSetChanged();
                                Log.v("Turn Complete", "It is now " + Game.currentTurnToString());
                            }
                            else
                            {
                                Log.v("Illegal Move", "You tried to do an illegal move.");
                            }
                        }
                    }
                    else
                    {
                        Log.v("No Piece to Select", "You tried to select an empty space, there's nothing here.");
                    }
                }
            });
            }
        else
        {
            imageView = (ImageView) convertView;
        }

        imageView.setAdjustViewBounds(true);

        //Set the background of the cell based on it's overall position.
        //If the position is on an odd row (row numbering starts at 1).
        if((position/8)%2 == 1)
        {
            //If position is odd, set it white (first position starts at 1).
            if(position%2 == 1)
            {
                imageView.setBackgroundColor(Color.WHITE);
            }
            else //Otherwise the position is even, set it black
            {
                imageView.setBackgroundColor(Color.BLACK);
            }

        }
        else //The position is in an even row.
        {
            //If the position is even, set it white
            if ((position % 2 == 0))
            {
                imageView.setBackgroundColor(Color.WHITE);
            }
            else //Otherwise the position is odd, set it black
            {
                imageView.setBackgroundColor(Color.BLACK);
            }
        }

        //load images based on resource associated with the piece
        Piece tempPiece = Game.getPieceAtPosition(new Position(position));
        if(tempPiece != null)
        {
            m_ThumbIds[position] = tempPiece.getImageResource();
            imageView.setImageResource(m_ThumbIds[position]);
        }

        return imageView;
    }

    public void resetGame()
    {
        Game.resetGame();
        m_TakenList1.resetGame();
        m_TakenList2.resetGame();
    }
}