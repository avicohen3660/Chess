import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.util.LinkedList;
import java.util.List;

import static java.lang.Integer.max;
import static java.lang.Integer.min;
import static jdk.xml.internal.SecuritySupport.getClassLoader;

public abstract class Piece
{
    protected int color;
    protected Square initSquare;
    protected Square curSquare;
    protected BufferedImage img;

    public Piece(int color, Square initSquare, String imgName)
    {
        this.color = color;
        this.initSquare = initSquare;
        this.curSquare = initSquare;
        try {
            this.img = getScaledImage(ImageIO.read(getClass().getResource("/Icons/"+imgName+".png")),50,50);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void moveTo(Square dest)
    {
        //dest.setOccupyingPiece(this);
        curSquare.removePiece();
        curSquare=dest;
        curSquare.putPiece(this);

    }

    public int getColor() {
        return color;
    }

    public Square getInitSquare() {
        return initSquare;
    }

    public Square getCurSquare() {
        return curSquare;
    }

    public BufferedImage getImg() {
        return img;
    }

    private BufferedImage getScaledImage(BufferedImage srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();

        return resizedImg;
    }

    public List<Square> getLinearOcs(Board b, int x, int y)
    {
        int firstX = -1;
        int lastX = 8;
        int firstY = -1;
        int lastY = 8;
        Square[][]  squares = b.getBoardSquares();

        for (int i = 1; i < 8-x; i++) {
            if(squares[x+i][y].isOccupied())
            {
                lastX = x+i;
                break;
            }
        }

        for (int i = 1; i < x+1; i++) {
            if(squares[x-i][y].isOccupied())
            {
                firstX = x-i;
                break;
            }
        }

        for (int i = 1; i < 8-y; i++) {
            if(squares[x][y+i].isOccupied())
            {
                lastY = y+i;
                break;
            }
        }

        for (int i = 1; i < y+1; i++) {
            if (squares[x][y-i].isOccupied()) {
                firstY = y - i;
                break;
            }
        }

        int[] ocs = new int[]{firstX,lastX,firstY,lastY};

        LinkedList<Square> list = new LinkedList<Square>();
        //int[] ocs = getLinearOcs(b,x,y);

        for (int i = ocs[0]+1; i < ocs[1]; i++) {
            if(i==x) continue;
            list.add(squares[i][y]);
        }
        for (int i = ocs[2]+1; i < ocs[3]; i++) {
            if (i==y) continue;
            list.add(squares[x][i]);
        }

        for (int i = 0; i < 2; i++) {
            try {
                if (squares[ocs[i]][y].isOccupied())
                    if (squares[ocs[i]][y].getOccupyingPiece().getColor() != color)
                    {
                        list.add(squares[ocs[i]][y]);
                    }
            }
            catch (IndexOutOfBoundsException e){}
        }

        for (int i = 2; i < 4; i++) {
            try {
                if (squares[x][ocs[i]].isOccupied())
                    if (squares[x][ocs[i]].getOccupyingPiece().getColor() != color) {
                        list.add(squares[x][ocs[i]]);
                    }
            }
            catch (IndexOutOfBoundsException e){}
        }


        return list;
    }

    public List<Square> getDiagonalOcs(Board b, int x,int y)
    {
        int stepsDR = min(8-x,8-y)-1;
        int stepsUL = min(x+1,y+1)-1;
        int stepsDL = min(8-x,y+1)-1;
        int stepsUR = min(x+1,8-y)-1;
        Square[][]  squares = b.getBoardSquares();

        for (int i = 1; i < min(8-x,8-y); i++) {
            if(squares[x+i][y+i].isOccupied())
            {
                stepsDR = i-1;
                break;
            }
        }

        for (int i = 1; i < min(x+1,y+1); i++) {
            if(squares[x-i][y-i].isOccupied())
            {
                stepsUL = i-1;
                break;
            }
        }

        for (int i = 1; i < min(8-x,y+1); i++) {
            if(squares[x+i][y-i].isOccupied())
            {
                stepsDL = i-1;
                break;
            }
        }

        for (int i = 1; i < min(x+1,8-y); i++) {
            if (squares[x-i][y+i].isOccupied()) {
                stepsUR = i-1;
                break;
            }
        }

        int[] ocs = new int[]{stepsUL,stepsUR,stepsDL,stepsDR};
        LinkedList<Square> list = new LinkedList<Square>();

        for (int i = 1; i <= ocs[0]; i++) {
            list.add(squares[x-i][y-i]);
        }
        for (int i = 1; i <= ocs[1]; i++) {
            list.add(squares[x-i][y+i]);
        }

        for (int i = 1; i <= ocs[2]; i++) {
            list.add(squares[x+i][y-i]);
        }
        for (int i = 1; i <= ocs[3]; i++) {
            list.add(squares[x+i][y+i]);
        }

        Square temp;
        try{
            temp = squares[x-ocs[0]-1][y-ocs[0]-1];
            if (temp.isOccupied()
                    && temp.getOccupyingPiece().getColor()!=color)
                list.add(temp);
        }
        catch (IndexOutOfBoundsException e) {}

        try{
            temp = squares[x-ocs[1]-1][y+ocs[1]+1];
            if (temp.isOccupied()
                    && temp.getOccupyingPiece().getColor()!=color)
                list.add(temp);
        }
        catch (IndexOutOfBoundsException e) {}

        try{
            temp = squares[x+ocs[2]+1][y-ocs[2]-1];
            if (temp.isOccupied()
                    && temp.getOccupyingPiece().getColor()!=color)
                list.add(temp);
        }
        catch (IndexOutOfBoundsException e) {}

        try{
            temp = squares[x+ocs[3]+1][y+ocs[3]+1];
            if (temp.isOccupied()
                    && temp.getOccupyingPiece().getColor()!=color)
                list.add(temp);
        }
        catch (IndexOutOfBoundsException e) {}

        return list;
    }



    public abstract List<Square> getLegalMoves(Board b);

    public abstract boolean isLegalMove(Board b,int x, int y);

    public abstract String getType();
}
