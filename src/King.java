import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class King extends Piece
{
    public King(int color, Square initSquare, String imgName)
    {
        super(color, initSquare, imgName);
    }

    public List<Square> getLegalMoves(Board b)
    {
        LinkedList<Square> list = new LinkedList<Square>();
        int x = curSquare.getxNum();
        int y = curSquare.getyNum();
        Square[][] squares = b.getBoardSquares();
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                if (!(i==0 && j==0))
                {
                    try {
                        if (!squares[x + i][y + j].isOccupied())
                            list.add(squares[x + i][y + j]);
                        else {
                            if (squares[x + i][y + j].getOccupyingPiece().getColor() != this.getColor())
                                list.add(squares[x + i][y + j]);
                        }
                    }
                    catch (IndexOutOfBoundsException e) {
                        continue;
                    }
                }
            }
        }

        return list;
    }

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
        return "King";
    }
}
