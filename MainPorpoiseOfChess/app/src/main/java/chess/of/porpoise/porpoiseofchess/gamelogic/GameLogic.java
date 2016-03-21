package chess.of.porpoise.porpoiseofchess.gamelogic;

import android.util.Log;

import java.util.HashSet;

import chess.of.porpoise.porpoiseofchess.gameboard.GameBoard;
import chess.of.porpoise.porpoiseofchess.gameboard.Position;
import chess.of.porpoise.porpoiseofchess.pieces.Bishop;
import chess.of.porpoise.porpoiseofchess.pieces.King;
import chess.of.porpoise.porpoiseofchess.pieces.Knight;
import chess.of.porpoise.porpoiseofchess.pieces.Pawn;
import chess.of.porpoise.porpoiseofchess.pieces.Piece;
import chess.of.porpoise.porpoiseofchess.pieces.Queen;
import chess.of.porpoise.porpoiseofchess.pieces.Rook;

/**
 * Created by Jeffrey on 11/4/2015.
 */
//Singleton Class
public class GameLogic {
    private GameBoard m_Board;
    private HashSet<Integer> m_WhitePiecePositions; //Used for AI computations
    private HashSet<Integer> m_BlackPiecePositions; //Used for AI computations
    private Position m_CurrentSelectedPosition; //not initialized in constructor
    private HashSet<Integer> m_ValidMovesOfSelectedPiece;
    private boolean m_WhiteTurn;
    private Integer m_TurnCounter;
    private static GameLogic game = new GameLogic();
    private GameLogic previousgame;

    private GameLogic(){
        this.m_Board = new GameBoard();
        this.m_WhitePiecePositions = new HashSet<>(32);
        this.m_BlackPiecePositions = new HashSet<>(32);
        this.m_WhiteTurn = true;
        this.m_TurnCounter = 0;
        this.previousgame = null;
        initializePiecePositions();
        updatePiecePositions();
    }

    public void resetGame(){
        game = new GameLogic();
    }

    public static GameLogic getGameLogic(){
        return game;
    }

    public void setCurrentSelectedPosition(Position pos){
        //Set the position we clicked as the current selected position.
        m_CurrentSelectedPosition = pos;
        //If the position we just set isn't null. Update the possible moves that the piece we selected can do.
        if(pos != null){
            Piece currentpiece = m_Board.getPieceAtPosition(getCurrentSelectedPosition());
            if (currentpiece != null){
                m_ValidMovesOfSelectedPiece = currentpiece.getPossibleMoves(this);
            }
        }
    }

    public Position getCurrentSelectedPosition(){
        return m_CurrentSelectedPosition;
    }

    public void initializePiecePositions(){
        //Initialize Pawns
        for(int i=0; i<8; i++){

            Pawn wp = new Pawn(true, new Position(1,i));
            Pawn bp = new Pawn(false, new Position(6,i));
            Log.v("Created Piece", wp.toString());
            Log.v("Created Piece", bp.toString());
            m_Board.putPieceOnBoard(wp.getPosition(), wp);
            m_Board.putPieceOnBoard(bp.getPosition(), bp);
        }

        //Initialize Rooks
        Rook wr1 = new Rook(true, new Position(0,0));
        Rook wr2 = new Rook(true, new Position(0,7));
        Rook br1 = new Rook(false, new Position(7,0));
        Rook br2 = new Rook(false, new Position(7,7));
        m_Board.putPieceOnBoard(wr1.getPosition(), wr1);
        m_Board.putPieceOnBoard(wr2.getPosition(), wr2);
        m_Board.putPieceOnBoard(br1.getPosition(), br1);
        m_Board.putPieceOnBoard(br2.getPosition(), br2);

        //Initialize Knights
        Knight wk1 = new Knight(true, new Position(0,1));
        Knight wk2 = new Knight(true, new Position(0,6));
        Knight bk1 = new Knight(false, new Position(7,1));
        Knight bk2 = new Knight(false, new Position(7,6));
        m_Board.putPieceOnBoard(wk1.getPosition(), wk1);
        m_Board.putPieceOnBoard(wk2.getPosition(), wk2);
        m_Board.putPieceOnBoard(bk1.getPosition(), bk1);
        m_Board.putPieceOnBoard(bk2.getPosition(), bk2);

        //Initialize Bishops
        Bishop wb1 = new Bishop(true, new Position(0,2));
        Bishop wb2 = new Bishop(true, new Position(0,5));
        Bishop bb1 = new Bishop(false, new Position(7,2));
        Bishop bb2 = new Bishop(false, new Position(7,5));
        m_Board.putPieceOnBoard(wb1.getPosition(), wb1);
        m_Board.putPieceOnBoard(wb2.getPosition(), wb2);
        m_Board.putPieceOnBoard(bb1.getPosition(), bb1);
        m_Board.putPieceOnBoard(bb2.getPosition(), bb2);

        //Initialize Queens
        Queen wq = new Queen(true, new Position(0,4));
        Queen bq = new Queen(false, new Position(7,4));
        m_Board.putPieceOnBoard(wq.getPosition(), wq);
        m_Board.putPieceOnBoard(bq.getPosition(), bq);

        //Initialize Kings
        King wking = new King(true, new Position(0,3));
        King bking = new King(false, new Position(7,3));
        m_Board.putPieceOnBoard(wking.getPosition(), wking);
        m_Board.putPieceOnBoard(bking.getPosition(), bking);
    }

    public void updatePiecePositions(){
        //todo: Used primarily for AI move evaluation. NYI
        //We need to keep track of what positions are occupied at the present time.
        //We don't really care what's there, just what color it is.
        //(unless it's a king that we're attacking, but we'll handle that elsewhere)
        //So let's put them in a hashset for quick lookup of specific locations.

        //Cleanup the old positions so we don't have old data.
        /*m_BlackPiecePositions.clear();
        m_WhitePiecePositions.clear();
        //For each piece in the pieces list, if it's white, add it to white's positions
        //else, add it to black
        for (Piece piece: pieces) {
            if (piece.isWhite()){
                m_WhitePiecePositions.add(piece.getPosition());
            }
            else
                m_BlackPiecePositions.add(piece.getPosition());
        }*/
    }

    public Piece getPieceAtPosition(Position pos){
        return m_Board.getPieceAtPosition(pos);
    }

    public void putPieceOnBoard(Position pos, Piece piece) {
        m_Board.putPieceOnBoard(pos, piece);
    }

    public void removePieceOnBoard(Position pos, boolean capturing){
        m_Board.removePieceOnBoard(pos, capturing);
    }
    public void removePieceOnBoard(Position pos){
        m_Board.removePieceOnBoard(pos);
    }

    public void completeTurn(Piece piecemoved){
        m_WhiteTurn = !m_WhiteTurn;
        m_TurnCounter++;
        piecemoved.setHasmoved(true);

        //If the piece is a pawn, check to see if we need to promote it.
        if(piecemoved.getPiecetype() == Piece.PieceType.PAWN){
            attemptPawnPromotion(piecemoved);
        }
        previousgame = this;
    }

    public String currentTurnToString(){
        if (m_WhiteTurn){
            return "White's Turn";
        }
        else
            return "Black's Turn";
    }

    public boolean isWhiteTurn(){
        return m_WhiteTurn;
    }

    public HashSet<Integer> getValidMovesOfSelectedPiece(){
        return m_ValidMovesOfSelectedPiece;
    }

    public Integer getCapturedResource(Integer pos, boolean white){
        return m_Board.getCapturedResource(pos, white);
    }

    public void attemptPawnPromotion(Piece Pawn){
        if(Pawn.isWhite() && (Pawn.getPosition().getRank() == 7)){
            m_Board.removePieceOnBoard(Pawn.getPosition());
            m_Board.putPieceOnBoard(Pawn.getPosition(), new Queen(true, new Position(Pawn.getPosition())));
        }
        else if(!Pawn.isWhite() && (Pawn.getPosition().getRank() == 0)){
            m_Board.removePieceOnBoard(Pawn.getPosition());
            m_Board.putPieceOnBoard(Pawn.getPosition(), new Queen(false, new Position(Pawn.getPosition())));
        }
    }

    public Integer getTurnCount(){
        return m_TurnCounter;
    }

    public void undoMove(){
        game = previousgame;
    }
}
