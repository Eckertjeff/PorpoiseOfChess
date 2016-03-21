package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public class King extends Piece
{
    private boolean m_InCheck;

    public King(boolean white, Position position)
    {
        super(white, position, PieceType.KING);
    }

    public int getImageResource()
    {
        if(isWhite())
        {
            return R.drawable.white_kl;
        }
        else
        {
            return R.drawable.black_kl;
        }
    }

    public boolean isIncheck()
    {
        return m_InCheck;
    }

    public void setIncheck(boolean incheck)
    {
        this.m_InCheck = incheck;
    }

    @Override
    public HashSet<Integer> getPossibleMoves(GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        //scanOutward(xdirection, ydirection, maxdistance, gamelogicinstance)
        //north/west are -1, east/south are +1, 0 is not scanning in that direction.
        possiblemoves.addAll(scanOutward(0, UP, 1, game)); //scan up
        possiblemoves.addAll(scanOutward(0, DOWN, 1, game)); //scan down
        possiblemoves.addAll(scanOutward(LEFT, 0, 1, game)); //scan left
        possiblemoves.addAll(scanOutward(RIGHT, 0, 1, game)); //scan right
        possiblemoves.addAll(scanOutward(LEFT, UP, 1, game)); //scan diagupleft
        possiblemoves.addAll(scanOutward(RIGHT, UP, 1, game)); //scan diagupright
        possiblemoves.addAll(scanOutward(RIGHT, DOWN, 1, game)); //scan diagdownright
        possiblemoves.addAll(scanOutward(LEFT, DOWN, 1, game)); //scan diagdownleft
        return possiblemoves;
    }

    @Override
    public String toString()
    {
        return "King, Position:"+ getPosition().toString();
    }
}
