package chess.of.porpoisegui.gui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;


public class MainBoard extends Activity
{
    private DrawGameBoard m_DrawGameBoard;
    private DrawTakenPieces m_takenPiecesP1;
    private DrawTakenPieces m_takenPiecesP2;
    private GridView m_ListP1;
    private GridView m_ListP2;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        Intent intent = getIntent();
        String player1name = intent.getStringExtra("FIRST_PLAYER");
        String player2name = intent.getStringExtra("SECOND_PLAYER");
        //drawing pieces in grids to be set later
        m_takenPiecesP1 = new DrawTakenPieces(this, false);
        m_takenPiecesP2 = new DrawTakenPieces(this, true);
        m_DrawGameBoard = new DrawGameBoard(this, m_takenPiecesP1, m_takenPiecesP2, player1name, player2name);

        //game setup
        //GameLogic game = GameLogic.getGameLogic();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_board);

        //create various objects based on resources from xml designs
        GridView gridview = (GridView) findViewById(R.id.gridView);

        TextView player1 = (TextView) findViewById(R.id.player1);
        TextView player2 = (TextView) findViewById(R.id.player2);

        m_ListP1 = (GridView) findViewById(R.id.listP1);
        m_ListP2 = (GridView) findViewById(R.id.listP2);
        LinearLayout gridLayout = (LinearLayout) findViewById(R.id.gridLayout);
        Space spaceLeft = (Space) findViewById(R.id.spaceLeft);
        Space spaceRight = (Space) findViewById(R.id.spaceRight);

        //set the names for the players based on passed intent
        player1.setText(intent.getStringExtra("FIRST_PLAYER"));
        player2.setText(intent.getStringExtra("SECOND_PLAYER"));

        //set box size for board based on screen
        m_DrawGameBoard.setBoxSize(getWindowManager().getDefaultDisplay());
        //setup width of taken piece gridview
        m_takenPiecesP2.setWidth(m_DrawGameBoard.getWidth());
        m_takenPiecesP1.setWidth(m_DrawGameBoard.getWidth());

        //set the adapters to their related objects
        m_ListP1.setAdapter(m_takenPiecesP1);
        m_ListP2.setAdapter(m_takenPiecesP2);

        gridview.setAdapter(m_DrawGameBoard);

        //set minimum and column widths
        gridLayout.setMinimumWidth(m_DrawGameBoard.getWidth() * 8);
        gridview.setColumnWidth(m_DrawGameBoard.getWidth());
        m_ListP1.setColumnWidth(m_DrawGameBoard.getWidth());
        m_ListP2.setColumnWidth(m_DrawGameBoard.getWidth());

        //check if we are rotated for a landscape view, if so setup spaces to make grid appear centered
        if(getWindowManager().getDefaultDisplay().getWidth() > getWindowManager().getDefaultDisplay().getHeight())
        {
            int width = getWindowManager().getDefaultDisplay().getWidth();
            width = width - m_DrawGameBoard.getWidth() * 12;
            width = width / 2;
            spaceLeft.setMinimumWidth(width);
            spaceRight.setMinimumWidth(width);
        }
    }


    /*public void undoMove()
    {
        //NYI, Need make a deep copy
        GameLogic game = GameLogic.getGameLogic();
        game.undoMove();
    }*/

    @Override
    public void onBackPressed()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.quit_confirmation)
                .setPositiveButton(R.string.button_quit, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        Intent setIntent = new Intent(getApplicationContext(), StartUpActivity.class);
                        setIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(setIntent);
                        final GameLogic game = GameLogic.getGameLogic();
                        game.resetGame();
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        //Do nothing, they don't want to quit.
                    }
                });
        builder.show();
    }
}

