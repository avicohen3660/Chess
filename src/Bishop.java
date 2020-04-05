import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(int color, Square initSquare, String imgName)
    {
        super(color, initSquare, imgName);
    }

    public List<Square> getLegalMoves(Board b)
    {
        int x = curSquare.getxNum();
        int y = curSquare.getyNum();
        Square[][] squares = b.getBoardSquares();

        List<Square> list = getDiagonalOcs(b,x,y);
        return list;
    }

    @Override
    public boolean isLegalMove(Board b, int x, int y) {

        List<Square> list = getLegalMoves(b);
        for (Square s : list)
        {
            if (s.getxNum()==x && s.getyNum()==y)
                return true;
        }
        return false;
    }

    @Override
    public String getType() {
        return "Bishop";
    }
}
