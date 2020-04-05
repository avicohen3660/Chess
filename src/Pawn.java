import java.awt.*;
import java.util.LinkedList;
import java.util.List;

public class Pawn extends Piece
{

    private boolean wasMoved;
    public Pawn(int color, Square initSquare, String imgName)
    {
        super(color, initSquare, imgName);
        wasMoved = false;
    }

    public void moveTo(Square dest)
    {
        super.moveTo(dest);
        wasMoved = true;
    }

    public List<Square> getLegalMoves(Board b)
    {
        LinkedList<Square> list = new LinkedList<Square>();
        int x = curSquare.getxNum();
        int y = curSquare.getyNum();
        Square[][] squares = b.getBoardSquares();
        if (color == 0) {
            if (!squares[x][y + 1].isOccupied()) {
                list.add(squares[x][y + 1]);
                if (!wasMoved) {
                    if (!squares[x][y + 2].isOccupied())
                        list.add(squares[x][y + 2]);
                }
            }
            for (int i = -1; i < 2; i += 2) {
                try {
                    if (squares[x + i][y + 1].isOccupied())
                        if (squares[x + i][y + 1].getOccupyingPiece().getColor() != this.color)
                            list.add(squares[x + i][y + 1]);
                }
                catch (IndexOutOfBoundsException e){}
            }

        } else {
            if (!squares[x][y - 1].isOccupied()) {
                list.add(squares[x][y - 1]);
                if (!wasMoved) {
                    if (!squares[x][y - 2].isOccupied())
                        list.add(squares[x][y - 2]);
                }
            }
            for (int i = -1; i < 2; i += 2) {
                try {
                    if (squares[x + i][y - 1].isOccupied())
                        if (squares[x + i][y - 1].getOccupyingPiece().getColor() != this.color)
                            list.add(squares[x + i][y - 1]);
                }
                catch (IndexOutOfBoundsException e){}
            }
        }



        return list;
    }

    public boolean isLegalMove(Board b,int destX, int destY)
    {
        int curX = this.getCurSquare().getxNum();
        int curY = this.getCurSquare().getyNum();
        Square[][] squares = b.getBoardSquares();
        if (color == 0)
        {
            if ((destX==(curX+1)) && ((destY==(curY+1))||(destY==(curY-1))))
                return true;
            return false;
        }
        else
        {
            if ((destX==(curX-1)) && ((destY==(curY+1))||(destY==(curY-1))))
                return true;
            return false;
        }
    }

    @Override
    public String getType() {
        return "Pawn";
    }

}
