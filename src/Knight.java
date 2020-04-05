import java.util.LinkedList;
import java.util.List;

import static java.lang.Math.abs;

public class Knight extends Piece{
    public Knight(int color, Square initSquare, String imgName)
    {
        super(color, initSquare, imgName);
    }

    public List<Square> getLegalMoves(Board b){
        int x = curSquare.getxNum();
        int y = curSquare.getyNum();
        Square[][] squares = b.getBoardSquares();

        List<Square> list = new LinkedList<Square>();

        for (int i = -2; i < 3; i++) {
            for (int j = -2; j < 3; j++) {
                if (abs(i)==abs(j) || i == 0 || j == 0)
                    continue;
                try {
                    Square cur = squares[x + i][y + j];
                    if (!cur.isOccupied())
                        list.add(cur);
                    else if (cur.getOccupyingPiece().getColor()!=color)
                        list.add(cur);
                }
                catch (IndexOutOfBoundsException e) {}
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
        return "Knight";
    }
}
