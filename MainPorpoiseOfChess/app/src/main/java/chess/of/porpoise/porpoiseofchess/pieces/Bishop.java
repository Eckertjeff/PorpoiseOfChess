package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public class Bishop extends Piece
{
    public Bishop(boolean white, Position position)
    {
        super(white, position, PieceType.BISHOP);
    }

    public int getImageResource()
    {
        if(isWhite())
        {
            return R.drawable.white_bl;
        }
        else
        {
            return R.drawable.black_bl;
        }
    }

    @Override
    public HashSet<Integer> getPossibleMoves(GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        //scanOutward(xdirection, ydirection, maxdistance, gamelogicinstance)
        //north/west are -1, east/south are +1, 0 is not scanning in that direction.
        possiblemoves.addAll(scanOutward(LEFT, UP, 8, game)); //scan diagupleft
        possiblemoves.addAll(scanOutward(RIGHT, UP, 8, game)); //scan diagupright
        possiblemoves.addAll(scanOutward(RIGHT, DOWN, 8, game)); //scan diagdownright
        possiblemoves.addAll(scanOutward(LEFT, DOWN, 8, game)); //scan diagdownleft
        return possiblemoves;
    }

    @Override
    public String toString()
    {
        return "Bishop, Position:"+ getPosition().toString();
    }
}
