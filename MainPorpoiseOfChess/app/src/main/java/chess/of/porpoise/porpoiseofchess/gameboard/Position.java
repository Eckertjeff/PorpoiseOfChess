package chess.of.porpoise.porpoiseofchess.gameboard;

/**
 * Created by Jeffrey on 11/4/2015.
 */
public class Position
{

    private int m_Rank;
    private int m_File;
    private int m_Pos;

    public int getFile()
    {
        return m_File;
    }

    public void setFile(int file)
    {
        this.m_File = file;
    }

    public int getRank()
    {
        return m_Rank;
    }

    public void setRank(int rank)
    {
        this.m_Rank = rank;
    }

    public Position(int rank, int file)
    {
        this.m_Rank = rank;
        this.m_File = file;
        this.m_Pos = file * 8 + rank;
    }
    // If the position is a single integer, mold it into the correct rank and file form,
    // and store the original value.
    public Position(int pos)
    {
        this.m_Rank = pos / 8;
        this.m_File = pos % 8;
        this.m_Pos = pos;
    }

    public Position(Position pos)
    {
        this.m_Rank = pos.getRank();
        this.m_File = pos.getFile();
    }

    public int getPos()
    {
        return m_Pos;
    }

    @Override
    public int hashCode()
    {
        // Just something lame to avoid collisions, change it if you feel inspired.
        // 2161 is a random prime.
        return m_Rank * 2161 + m_File;
    }

    @Override
    public String toString()
    {
        return m_File + "," + m_Rank;
    }

    public boolean equals(Position a, Position b)
    {
        if((a.getRank() == b.getRank()) && (a.getFile() == b.getFile()))
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean isValid()
    {
        return (((getRank() <8) && (getRank() >=0)) && ((getFile() <8) && (getFile() >=0)));
    }
}
