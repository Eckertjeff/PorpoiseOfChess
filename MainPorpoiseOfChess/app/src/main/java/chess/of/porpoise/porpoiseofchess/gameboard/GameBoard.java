package chess.of.porpoise.porpoiseofchess.gameboard;

import java.util.ArrayList;
import java.util.HashMap;

import chess.of.porpoisegui.gui.R;
import chess.of.porpoise.porpoiseofchess.pieces.Piece;
/**
 * Created by Jeffrey on 10/28/2015.
 */
public class GameBoard
{
    private HashMap<Integer, Piece> m_GameBoardMap;
    private ArrayList<Piece> m_CapturedWhitePieces;
    private ArrayList<Piece> m_CapturedBlackPieces;

    public GameBoard()
    {
        m_GameBoardMap = new HashMap<>(64);
        m_CapturedWhitePieces = new ArrayList<>(16);
        m_CapturedBlackPieces = new ArrayList<>(16);
    }

    public HashMap<Integer, Piece> getGameBoardMap()
    {
        return m_GameBoardMap;
    }

    public boolean isPositionOccupied(Position pos)
    {
        return m_GameBoardMap.containsKey(pos);
    }

    public Piece getPieceAtPosition(Position pos)
    {
        return m_GameBoardMap.get(pos.hashCode());
    }

    public void putPieceOnBoard(Position pos, Piece piece)
    {
        if(piece == null)
        {
            throw new IllegalArgumentException("Piece is null!");
        }
        piece.setPosition(pos);
        m_GameBoardMap.put(pos.hashCode(), piece);
    }

    public void removePieceOnBoard(Position pos, boolean capturing)
    {
        //Overloaded function with a boolean for capturing.
        //If there's a piece at the position passed
        if(m_GameBoardMap.containsKey(pos.hashCode()))
        {
            if(capturing)
            {
                //and we want to capture it, add it to the appropriate captured pieces list.
                if(getPieceAtPosition(pos).isWhite())
                {
                    getPieceAtPosition(pos).setCaptured(true);
                    m_CapturedWhitePieces.add(getPieceAtPosition(pos));
                }
                else
                {
                    getPieceAtPosition(pos).setCaptured(true);
                    m_CapturedBlackPieces.add(getPieceAtPosition(pos));
                }
            }
            //Remove the piece.
            m_GameBoardMap.remove(pos.hashCode());
        }
    }

    public void removePieceOnBoard(Position pos)
    {
        //If there's a piece in the position on the board, remove it from the position.
        if(m_GameBoardMap.containsKey(pos.hashCode()))
        {
            m_GameBoardMap.remove(pos.hashCode());
        }
    }

    public Integer getCapturedResource(Integer pos, boolean white)
    {
        Integer imageresource = 0;
        if(white)
        {
            //Gets the appropriate resource from the piece in the capturedpieces list to display to the user.
            if(m_CapturedWhitePieces.size() > pos)
            {
                imageresource = m_CapturedWhitePieces.get(pos).getImageResource();
            }
            else
            {
                imageresource = R.drawable.blank_square;
            }
        }
        else
        {
            if(m_CapturedBlackPieces.size() > pos)
            {
                imageresource = m_CapturedBlackPieces.get(pos).getImageResource();
            }
            else
            {
                imageresource = R.drawable.blank_square;
            }
        }
        return imageresource;
    }
}
