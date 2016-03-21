package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public class Queen extends Piece
{
    public Queen(boolean white, Position position)
    {
        super(white, position, PieceType.QUEEN);
    }

    public int getImageResource()
    {
        if(isWhite())
        {
            return R.drawable.white_ql;
        }
        else
        {
            return R.drawable.black_ql;
        }
    }

    @Override
    public HashSet<Integer> getPossibleMoves(GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        //scanOutward(xdirection, ydirection, maxdistance, gamelogicinstance)
        //north/west are -1, east/south are +1, 0 is not scanning in that direction.
        possiblemoves.addAll(scanOutward(0, UP, 8, game)); //scan up
        possiblemoves.addAll(scanOutward(0, DOWN, 8, game)); //scan down
        possiblemoves.addAll(scanOutward(LEFT, 0, 8, game)); //scan left
        possiblemoves.addAll(scanOutward(RIGHT, 0, 8, game)); //scan right
        possiblemoves.addAll(scanOutward(LEFT, UP, 8, game)); //scan diagupleft
        possiblemoves.addAll(scanOutward(RIGHT, UP, 8, game)); //scan diagupright
        possiblemoves.addAll(scanOutward(RIGHT, DOWN, 8, game)); //scan diagdownright
        possiblemoves.addAll(scanOutward(LEFT, DOWN, 8, game)); //scan diagdownleft
        return possiblemoves;
    }

    @Override
    public String toString()
    {
        return "Queen, Position:"+ getPosition().toString();
    }
}
