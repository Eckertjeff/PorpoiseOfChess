package chess.of.porpoise.porpoiseofchess.pieces;

import java.util.HashSet;

import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.gamelogic.GameLogic;

/**
 * Created by Jeffrey on 10/28/2015.
 */
public abstract class Piece
{
    private boolean m_White;
    private boolean m_HasMoved;
    private boolean m_Captured;
    private boolean m_ProvidesCheck;
    private Position m_Position;
    private PieceType m_PieceType;
    final int UP = -1, LEFT = -1, RIGHT = 1, DOWN = 1;

    public enum PieceType { PAWN, ROOK, KNIGHT, BISHOP, QUEEN, KING, INVALID }

    public Piece(boolean white, Position position, PieceType type)
    {
        m_White = white;
        m_Position = position;
        m_HasMoved = false;
        m_Captured = false;
        m_ProvidesCheck = false;
        m_PieceType = type;
    }

    public boolean isWhite()
    {
        return m_White;
    }

    public void setWhite(boolean white)
    {
        this.m_White = white;
    }

    public boolean isHasmoved()
    {
        return m_HasMoved;
    }

    public void setHasmoved(boolean hasmoved)
    {
        this.m_HasMoved = hasmoved;
    }

    public boolean isCaptured()
    {
        return m_Captured;
    }

    public void setCaptured(boolean captured)
    {
        this.m_Captured = captured;
    }

    public boolean isProvidescheck()
    {
        return m_ProvidesCheck;
    }

    public void setProvidescheck(boolean providescheck)
    {
        this.m_ProvidesCheck = providescheck;
    }

    public Position getPosition()
    {
        return m_Position;
    }

    public void setPosition(Position position)
    {
        this.m_Position = position;
    }

    public abstract int getImageResource();

    //Returns a list of valid move positions.
    public abstract HashSet<Integer> getPossibleMoves(GameLogic game);

    public PieceType getPiecetype()
    {
        return m_PieceType;
    }

    public HashSet<Integer> scanOutward(int x, int y, int maxdistance, GameLogic game)
    {
        HashSet<Integer> possiblemoves = new HashSet<>();
        for(int i=0; i<maxdistance; i++) //Iterate through each position from the current position, to the max distance.
        {
            //Each iteration, the position we're checking the position one square further away in the direction we were passed.
            //ex: x = -1, y = 1 is one square left, one square down, which is the square on the down-left diagonal.
            Position pos = new Position(getPosition().getRank() + (i*y) + y, getPosition().getFile() + (i*x) + x );
            if(pos.isValid())
            {
                if (game.getPieceAtPosition(pos) == null)
                {
                    // If the position is valid and empty, we can move there.
                    possiblemoves.add(pos.hashCode());
                }
                else
                {
                    if (game.getPieceAtPosition(pos).isWhite() != isWhite())
                    {
                        //If the position is valid and has a different color piece, we can move there, but no further.
                        possiblemoves.add(pos.hashCode());
                        break;
                    }
                    else
                    {
                        //If the position is valid, but has the same color piece as the current one, we can't move there, or any further.
                        break;
                    }
                }
            }
        }
        return possiblemoves;
    }
}
