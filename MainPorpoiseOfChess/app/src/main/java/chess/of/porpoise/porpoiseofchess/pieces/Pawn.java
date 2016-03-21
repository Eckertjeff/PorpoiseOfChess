package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public class Pawn extends Piece
{
    private boolean m_DoubleMoved;

    public Pawn(boolean white, Position position)
    {
        super(white, position, PieceType.PAWN);
    }

    public int getImageResource()
    {
        if(isWhite())
        {
            return R.drawable.white_pl;
        }
        else
        {
            return R.drawable.black_pl;
        }
    }

    public boolean isDoublemoved()
    {
        //used to check for en-passant.
        return m_DoubleMoved;
    }

    public void setDoublemoved(boolean doublemoved)
    {
        this.m_DoubleMoved = doublemoved;
    }

    @Override
    public HashSet<Integer> getPossibleMoves(GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        if(isWhite())
        {
            //If the pawn is white, we can move down.
            Position downone = new Position(getPosition().getRank()+1, getPosition().getFile());
            Position downtwo = new Position(getPosition().getRank()+2, getPosition().getFile());
            Position diagleft = new Position(getPosition().getRank()+1, getPosition().getFile()-1);
            Position diagright = new Position(getPosition().getRank()+1, getPosition().getFile()+1);
            if(game.getPieceAtPosition(downone) == null)
            {
                //If there's nothing directly in front of us, we can move forward one square.
                possiblemoves.add(downone.hashCode());
            }
            if(game.getPieceAtPosition(diagleft) != null && !game.getPieceAtPosition(diagleft).isWhite())
            {
                //If there is a piece that's a different color to our diagonal left, we can move there and capture it.
                possiblemoves.add(diagleft.hashCode());
            }
            if(game.getPieceAtPosition(diagright) != null && !game.getPieceAtPosition(diagright).isWhite())
            {
                //If there is a piece that's a different color to our diagonal right, we can move there and capture it.
                possiblemoves.add(diagright.hashCode());
            }
            if (!isHasmoved()&& (game.getPieceAtPosition(downone) == null && game.getPieceAtPosition(downtwo) == null))
            {
                //If the pawn hasn't moved yet, and both squares in front of it are empty, it can move two squares forward.
                possiblemoves.add(downtwo.hashCode());
            }
        }
        else //If the pawn is black, we can move up.
        {
            Position upone = new Position(getPosition().getRank()-1, getPosition().getFile());
            Position uptwo = new Position(getPosition().getRank()-2, getPosition().getFile());
            Position diagleft = new Position(getPosition().getRank()-1, getPosition().getFile()-1);
            Position diagright = new Position(getPosition().getRank()-1, getPosition().getFile()+1);
            if (game.getPieceAtPosition(upone) == null)
            {
                //If there's nothing directly in front of us, we can move forward one square
                possiblemoves.add(upone.hashCode());
            }
            if (game.getPieceAtPosition(diagleft) != null && game.getPieceAtPosition(diagleft).isWhite())
            {
                //If there is a piece that's a different color to our diagonal left, we can move there and capture it.
                possiblemoves.add(diagleft.hashCode());
            }
            if (game.getPieceAtPosition(diagright) != null && game.getPieceAtPosition(diagright).isWhite())
            {
                //If there is a piece that's a different color to our diagonal right, we can move there and capture it.
                possiblemoves.add(diagright.hashCode());
            }
            if (!isHasmoved()&& (game.getPieceAtPosition(upone) == null && game.getPieceAtPosition(uptwo) == null))
            {
                //If the pawn hasn't moved yet, and both squares in front of it are empty, it can move two squares forward.
                possiblemoves.add(uptwo.hashCode());
            }
        }
        return possiblemoves;
    }

    @Override
    public String toString()
    {
        return "Pawn, Position:"+ getPosition().toString();
    }
}
