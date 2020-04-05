

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class Square extends JLabel //implements MouseListener
{


    private boolean dispPiece;
    private Piece occupyingPiece;
    private int xNum;
    private int yNum;

    public Square(int c, int xNum, int yNum) {
        this.setOpaque(true);
        this.setHorizontalAlignment(SwingConstants.CENTER);
        if (c==1)
            this.setBackground(new Color(101,67,33));
        else
            this.setBackground(new Color(221,192,127));
        this.dispPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;

        //this.addMouseListener(this);
        this.setBorder(BorderFactory.createEmptyBorder());
    }

    public void putPiece(Piece p)
    {
        this.occupyingPiece = p;
        this.setIcon(new ImageIcon(p.getImg()));
    }

    public void removePiece()
    {
        this.occupyingPiece = null;
        this.setIcon(null);
    }

    public void moveTo(Square dest)
    {
        dest.putPiece(this.occupyingPiece);
        this.removePiece();
    }

    public void mark()
    {
        setBackground(Color.green);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    public void unMark()
    {
        if((xNum+yNum)%2==0)
            setBackground(new Color(221,192,127));
        else
            setBackground(new Color(101,67,33));

        setBorder(new EmptyBorder(new Insets(0,0,0,0)));
    }

    public void setOccupyingPiece(Piece occupyingPiece) {
        this.occupyingPiece = occupyingPiece;
    }

    public Piece getOccupyingPiece() {
        return occupyingPiece;
    }

    public boolean isOccupied()
    {
        if(occupyingPiece == null)
            return  false;
        return true;
    }

    public int getxNum() {
        return xNum;
    }

    public int getyNum() {
        return yNum;
    }

    /*@Override
    public void mouseClicked(MouseEvent e) {
        Square s = (Square)getComponentAt(e.getX(),e.getY());
        System.out.println(s.xNum);
        System.out.println(s.yNum);
        System.out.println();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }*/
}
