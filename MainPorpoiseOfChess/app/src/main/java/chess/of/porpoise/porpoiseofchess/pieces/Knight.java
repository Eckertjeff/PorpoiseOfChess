package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public class Knight extends Piece
{
    public Knight(boolean white, Position position)
    {
        super(white, position, PieceType.KNIGHT);
    }

    public int getImageResource()
    {
        if(isWhite())
        {
            return R.drawable.white_nl;
        }
        else
        {
            return R.drawable.black_nl;
        }
    }

    @Override
    public HashSet<Integer> getPossibleMoves(GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        /*position descriptions: first direction is two spaces in that direction,
        second direction is one space in that direction. So, for example
        upleft = two up, then one left from the origin point. */
        Position upleftpos = new Position(getPosition().getRank()-2, getPosition().getFile()-1);
        Position uprightpos = new Position(getPosition().getRank()-2, getPosition().getFile()+1);
        Position rightuppos = new Position(getPosition().getRank()-1, getPosition().getFile()+2);
        Position rightdownpos = new Position(getPosition().getRank()+1, getPosition().getFile()+2);
        Position downrightpos = new Position(getPosition().getRank()+2, getPosition().getFile()+1);
        Position downleftpos = new Position(getPosition().getRank()+2, getPosition().getFile()-1);
        Position leftdownpos = new Position(getPosition().getRank()+1, getPosition().getFile()-2);
        Position leftuppos = new Position(getPosition().getRank()-1, getPosition().getFile()-2);

        if (upleftpos.isValid())
        {
            if (game.getPieceAtPosition(upleftpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(upleftpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(upleftpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(upleftpos.hashCode());
                }
            }
        }

        if (uprightpos.isValid())
        {
            if (game.getPieceAtPosition(uprightpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(uprightpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(uprightpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(uprightpos.hashCode());
                }
            }
        }

        if (rightuppos.isValid())
        {
            if (game.getPieceAtPosition(rightuppos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(rightuppos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(rightuppos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(rightuppos.hashCode());
                }
            }
        }

        if (rightdownpos.isValid())
        {
            if (game.getPieceAtPosition(rightdownpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(rightdownpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(rightdownpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(rightdownpos.hashCode());
                }
            }
        }

        if (downrightpos.isValid())
        {
            if (game.getPieceAtPosition(downrightpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(downrightpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(downrightpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(downrightpos.hashCode());
                }
            }
        }

        if (downleftpos.isValid())
        {
            if (game.getPieceAtPosition(downleftpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(downleftpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(downleftpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(downleftpos.hashCode());
                }
            }
        }

        if (leftdownpos.isValid())
        {
            if (game.getPieceAtPosition(leftdownpos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(leftdownpos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(leftdownpos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(leftdownpos.hashCode());
                }
            }
        }

        if (leftuppos.isValid())
        {
            if (game.getPieceAtPosition(leftuppos) == null)
            {
                //Position is valid, and it's empty, we can move there.
                possiblemoves.add(leftuppos.hashCode());
            }
            else //There is a piece there.
            {
                if (game.getPieceAtPosition(leftuppos).isWhite() != isWhite())
                {
                    //The two pieces are different colors, so we can move there and capture it.
                    possiblemoves.add(leftuppos.hashCode());
                }
            }
        }
        return possiblemoves;
    }

    @Override
    public String toString()
    {
        return "Knight, Position:"+ getPosition().toString();
    }
}
