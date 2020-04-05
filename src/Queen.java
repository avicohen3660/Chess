import java.util.LinkedList;
import java.util.List;

public class Queen extends Piece{
    public Queen(int color, Square initSquare, String imgName)
    {
        super(color, initSquare, imgName);
    }

    public List<Square> getLegalMoves(Board b)
    {
        int x = curSquare.getxNum();
        int y = curSquare.getyNum();

        List<Square> list1 = getLinearOcs(b,x,y);
        List<Square> list2 = getDiagonalOcs(b,x,y);
        List<Square> list = new LinkedList<Square>();
        list.addAll(list1);
        list.addAll(list2);

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
        return "Queen";
    }
}
