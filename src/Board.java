import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Period;
import java.util.LinkedList;
import java.util.List;

public class Board extends JPanel implements MouseListener
{
    private JFrame frame;
    private JPanel gui = new JPanel();
    private int width, height;
    private int square_size;
    private JMenuBar menuBar;
    private JPanel board;
    private Square[][] boardSquares = new Square[8][8];
    private static final String COLS = "ABCDEFGH";
    private Piece curPiece;
    private boolean isChosen;
    private boolean whitesTurn;
    JLabel turn;
    private List<Square> legalMoves;
    private List<Piece> whitePieces;
    private List<Piece> blackPieces;

    public Board()
    {
        initBoard();
    }

    public void initBoard()
    {
        width=500;
        height=500;
        square_size=(int)((height)/11);
        isChosen = false;
        whitesTurn = true;

        whitePieces = new LinkedList<Piece>();
        blackPieces = new LinkedList<Piece>();
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(width, height);
        frame.setLayout(new BorderLayout());
        //frame.setLayout(null);

        gui.setBorder(new EmptyBorder(5, 5, 5, 5));

        menuBar = new JMenuBar();
        //menuBar.add(new JMenu("test"));
        JMenu menu = new JMenu("menu");
        JMenuItem item1 = new JMenuItem("Opt1");
        JMenuItem item2 = new JMenuItem("Opt2");
        menu.add(item1);
        menu.add(item2);
        menuBar.add(menu);



        board = new JPanel(new GridLayout(0,9,0,0));
        board.setBorder(new LineBorder(Color.black));

        Insets margins = new Insets(0,0,0,0);
        for (int i = 0; i < boardSquares.length; i++)
        {
            for (int j = 0; j < boardSquares[i].length; j++)
            {
                Square s = new Square((i+j)%2,i,j);

                Border emptyBorder = BorderFactory.createEmptyBorder();
                s.setBorder(emptyBorder);
                s.setPreferredSize(new Dimension(square_size, square_size));

                boardSquares[i][j] = s;
            }

        }

        board.add(new JLabel(""));
        for (int i = 0; i < 8; i++) {
            board.add(new JLabel(COLS.substring(i,i+1),SwingConstants.CENTER));
        }

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                if (j==0)
                    board.add(new JLabel("" + (i + 1), SwingConstants.CENTER));
                else
                    board.add(boardSquares[j-1][i]);
            }
        }

        frame.addMouseListener(this);
        initializePieces();

        //gui.setLayout(new GridLayout(0,1));
        gui.add(board);

        turn = new JLabel("White's turn");
        gui.add(turn);

        frame.setJMenuBar(menuBar);
        frame.add(gui);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

    }

    private ImageIcon getScaledImage(ImageIcon srcImg, int w, int h){
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = resizedImg.createGraphics();

        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        //g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg.getImage(), 0, 0, w, h, null);
        g2.dispose();

        return new ImageIcon(resizedImg);
    }

    void initializePieces()
    {
        for (int i = 0; i < 8; i++) {
            boardSquares[i][1].putPiece(new Pawn(0,boardSquares[i][1],"BlackPawn"));
            boardSquares[i][6].putPiece(new Pawn(1,boardSquares[i][6],"WhitePawn"));
        }

        boardSquares[0][0].putPiece(new Rook(0,boardSquares[0][0],"BlackRook"));
        boardSquares[1][0].putPiece(new Knight(0,boardSquares[1][0],"BlackKnight"));
        boardSquares[2][0].putPiece(new Bishop(0,boardSquares[2][0],"BlackBishop"));
        boardSquares[3][0].putPiece(new Queen(0,boardSquares[3][0],"BlackQueen"));
        boardSquares[4][0].putPiece(new King(0,boardSquares[4][0],"BlackKing"));
        boardSquares[5][0].putPiece(new Bishop(0,boardSquares[5][0],"BlackBishop"));
        boardSquares[6][0].putPiece(new Knight(0,boardSquares[6][0],"BlackKnight"));
        boardSquares[7][0].putPiece(new Rook(0,boardSquares[7][0],"BlackRook"));

        boardSquares[0][7].putPiece(new Rook(1,boardSquares[0][7],"WhiteRook"));
        boardSquares[1][7].putPiece(new Knight(1,boardSquares[1][7],"WhiteKnight"));
        boardSquares[2][7].putPiece(new Bishop(1,boardSquares[2][7],"WhiteBishop"));
        boardSquares[3][7].putPiece(new Queen(1,boardSquares[3][7],"WhiteQueen"));
        boardSquares[4][7].putPiece(new King(1,boardSquares[4][7],"WhiteKing"));
        boardSquares[5][7].putPiece(new Bishop(1,boardSquares[5][7],"WhiteBishop"));
        boardSquares[6][7].putPiece(new Knight(1,boardSquares[6][7],"WhiteKnight"));
        boardSquares[7][7].putPiece(new Rook(1,boardSquares[7][7],"WhiteRook"));

        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 8; j++) {
                blackPieces.add(boardSquares[j][i].getOccupyingPiece());
            }
        }

        for (int i = 6; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                whitePieces.add(boardSquares[j][i].getOccupyingPiece());
            }
        }


    }


    public Square[][] getBoardSquares() {
        return boardSquares;
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

        Square s = componentAt(e.getX(),e.getY());
        System.out.println(s.getOccupyingPiece());

        if(!isChosen)  // לא נבחר חייל
        {
            if (s.isOccupied()) {
                if ((whitesTurn && s.getOccupyingPiece().getColor() == 1)
                || (!whitesTurn && s.getOccupyingPiece().getColor() == 0)) {
                    curPiece = s.getOccupyingPiece();
                    legalMoves = curPiece.getLegalMoves(this);
                    markLegal(legalMoves);
                    if (legalMoves.size() != 0)
                        isChosen = true;
                }
            }
        }

        else // נבחר חייל
        {
//            if (s.isOccupied() &&  s.getBackground()!= Color.green)
//            {
//                unMarkLegal(legalMoves);
//                curPiece = s.getOccupyingPiece();
//                legalMoves = curPiece.getLegalMoves(this);
//                markLegal(legalMoves);
//                if (legalMoves.size() != 0)
//                    isChosen = true;
//            }
            if (s.getBackground()==Color.green) // נבחר מקום חוקי
            {
                int fromX = curPiece.getCurSquare().getxNum();
                int fromY = curPiece.getCurSquare().getyNum();
                System.out.println(isCheck(fromX,fromY,s.getxNum(),s.getyNum()));
                unMarkLegal(legalMoves);
                legalMoves = null;
                curPiece.moveTo(s);
                animation(curPiece,s);
                isChosen = false;
                whitesTurn = !whitesTurn;
                setTurnLabel();

            }
            else
            {
                unMarkLegal(legalMoves);
                legalMoves = null;
                isChosen = false;
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    public Square componentAt(int x, int y) {

        Point p = SwingUtilities.convertPoint(boardSquares[0][0], 0, 0, frame);
        int fromX=(int)p.getX();
        int fromY=(int)p.getY();
        int toX=+8*boardSquares[7][7].getWidth()+(int)fromX;
        int toY=+8*boardSquares[7][7].getWidth()+(int)fromY;

        if (fromX<x && x<toX && fromY<y && y<toY)
        {
            return boardSquares[(x-fromX)/boardSquares[0][0].getWidth()][(y-fromY)/boardSquares[0][0].getHeight()];
        }
        return null;
    }

    public void markLegal(List<Square> list)
    {
        for (Square s: list)
        {
            s.mark();
        }
    }

    public void unMarkLegal(List<Square> list)
    {
        for (Square s: list)
        {
            s.unMark();
        }
    }

    public void animation(Piece cur, Square dest)
    {
        //Point from = new Point(cur.getCurSquare().ge)
        Point p = SwingUtilities.convertPoint(boardSquares[0][0], 0, 0, frame);
        int startX = (int)p.getX();
        int startY = (int)p.getY();
        //System.out.println(cur.getCurSquare().getX());
        //System.out.println(cur.getCurSquare().getY());
    }

    public void setTurnLabel()
    {
        if (whitesTurn)
            turn.setText("White's turn");
        else
            turn.setText("Black's turn");

    }


    public boolean isCheck(int fromX, int fromY, int toX, int toY)
    {
        int color = boardSquares[fromX][fromY].getOccupyingPiece().getColor();
        Point kingPos = new Point();

        Square[][] newSquares = new Square[8][8];
        for (int i = 0; i < newSquares.length; i++) {
            for (int j = 0; j < newSquares[0].length; j++) {
                newSquares[i][j] = new Square(0,i,j);
                if (boardSquares[i][j].isOccupied()) {
                    newSquares[i][j].putPiece(boardSquares[i][j].getOccupyingPiece());
                    if (newSquares[i][j].getOccupyingPiece().getType().equals("King") &&
                            newSquares[i][j].getOccupyingPiece().getColor()==color)
                        kingPos = new Point(newSquares[i][j].getxNum(),newSquares[i][j].getyNum());
                }

            }
        }
        newSquares[fromX][fromY].moveTo(newSquares[toX][toY]);

        List<Piece> pieces = new LinkedList<Piece>();
        if (color == 1)
            pieces = blackPieces;
        else
            pieces = whitePieces;

        for (Piece p: pieces)
        {
            if (p.isLegalMove(this, (int)kingPos.getX(),(int)kingPos.getY()))
                return true;
        }

        return false;

    }
}
