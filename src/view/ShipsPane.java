package view;

import view.Panels.GamePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashSet;

import static view.Frames.SelectModeFrame.scH;
import static view.Frames.SelectModeFrame.scW;
import static view.Panels.GamePanel.gameFieldW;

/**
 * Created by Kimiya :) on 08/07/2017.
 */
public class ShipsPane extends JLayeredPane {

    private String[] shipsName = {"4H.png", "4V.png", "3H.png", "3V.png", "2H.png", "2V.png", "1.png"};
    private int[] width = {((int) (scW / 3) / 10) * 4, (int) (scW / 3) / 10, ((int) (scW / 3) / 10) * 3, (int) (scW / 3) / 10, ((int) (scW / 3) / 10) * 2, (int) (scW / 3) / 10, (int) (scW / 3) / 10};
    private int[] height = {(int) (scW / 3) / 10, ((int) (scW / 3) / 10) * 4, (int) (scW / 3) / 10, ((int) (scW / 3) / 10) * 3, (int) (scW / 3) / 10, ((int) (scW / 3) / 10) * 2, (int) (scW / 3) / 10};

    private final int[] columns = new int[11];
    private final int[] rows = new int[11];
    private final int length = gameFieldW / 10;

    private Point firstLocation;


    private ImageIcon icone = ReadImage.makeIcon("explosion.png", width[6], (height[6]));
    private ImageIcon iconw = ReadImage.makeIcon("water.png", width[6], (height[6]));

    private boolean dragEnable = true;


    private int number;
    private int shipCounter;

    public static ArrayList<Integer> shippedButtons;


    private HashSet<Integer> forbiddenAreas;
    private ArrayList<Point> forbidAreasPoint;


    static int counter = 0;

    private StickerLayer[] stickerArray;
    private static ImageIcon[] ships;

    public void setDragEnable(boolean enable){dragEnable = enable;}


    public ShipsPane() {
        super();
        setFocusable(true);
        makeShips();

    }

    public void makeShips() {
        doMeasure();
        ships = new ImageIcon[7];

        ships[0] = ReadImage.makeIcon(shipsName[0], width[0], height[0]);
        ships[1] = ReadImage.makeIcon(shipsName[1], width[1], height[1]);

        ships[2] = ReadImage.makeIcon(shipsName[2], width[2], height[2]);
        ships[3] = ReadImage.makeIcon(shipsName[3], width[3], height[3]);

        ships[4] = ReadImage.makeIcon(shipsName[4], width[4], height[4]);
        ships[5] = ReadImage.makeIcon(shipsName[5], width[5], height[5]);

        ships[6] = ReadImage.makeIcon(shipsName[6], width[6], height[6]);

    }


    public void applyPanel(JPanel p) {
        add(p, new Integer(counter));
        counter++;
    }

    public void resetShips(){
        removeShips();
        makeShipsSticker();

    }

    public void removeShips(){
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < getComponentCount() ; i++) {
                if(getComponent(i) instanceof StickerLayer){
                    remove(getComponent(i));
                }
            }
        }
    }

    public void setShipsInvisible(){
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < getComponentCount() ; i++) {
                if(getComponent(i) instanceof StickerLayer){
                    getComponent(i).setVisible(false);
                }
            }
        }
    }

    public void setShipsVisible(){
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < getComponentCount() ; i++) {
                if(getComponent(i) instanceof StickerLayer){
                    getComponent(i).setVisible(true);
                }
            }
        }
    }

    public void removeredundant(){
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < getComponentCount() ; i++) {
                if(getComponent(i) instanceof JButton){
                    getComponent(i).setVisible(false);
                }
            }
        }
    }


    public void makeShipsSticker() {
        stickerArray = new ShipsPane.StickerLayer[10];

        stickerArray[0] = makePanels(1, rows[7], columns[4]);

        stickerArray[1] = makePanels(2, rows[3], columns[4]);
        stickerArray[2] = makePanels(3, rows[5], columns[0]);

        stickerArray[3] = makePanels(4, rows[8], columns[1]);
        stickerArray[4] = makePanels(5, rows[3], columns[6]);
        stickerArray[5] = makePanels(5, rows[1], columns[2]);

        stickerArray[6] = makePanels(6, rows[3], columns[1]);
        stickerArray[7] = makePanels(6, rows[1], columns[7]);
        stickerArray[8] = makePanels(6, rows[9], columns[3]);
        stickerArray[9] = makePanels(6, rows[9], columns[9]);

        number = counter;

        for (int i = 0; i < 10; i++) {
            shipCounter = counter;
            counter++;
            add(stickerArray[i], new Integer(counter));
        }

        setShippedArea();

    }

    public StickerLayer makePanels(int n, int x, int y) {

        StickerLayer ship = new StickerLayer(ships[n]);
        ship.addMouseMotionListener(new MouseAdapter() {
            public void mouseDragged(MouseEvent e) {
                if(dragEnable){
                    super.mouseDragged(e);
                    setShippedArea();
                    //Point p = findLocation(e);
                    //ship.setLocation(p);
                    //setShippedArea();
                    ship.setLocation(e.getLocationOnScreen());
                    ship.repaint();
                }}
        });

        ship.addMouseListener(new MouseAdapter() {

            /*public void mouseReleased(MouseEvent e){
                setShippedArea();
                    if (dragEnable) {
                        super.mouseReleased(e);
                        setShippedArea();
                        Point p = findLocation(e);
                        ship.setLocation(p);
                        setShippedArea();
                        ship.repaint();
                    }

            }*/

            boolean flag = false;

            public void mousePressed(MouseEvent e){
                setForbiddenArea(-1);
                firstLocation = ship.getLocation();

                if(dragEnable){
                    int xShip = ship.getX();
                    int yShip = ship.getY();
                    for (int i = xShip; i < xShip + ship.getWidth() ; i+= width[6]) {
                        for (int j = yShip ; j <  yShip + ship.getHeight() ; j += width[6]) {
                            Integer integer = new Integer(findButton(i , j));
                            System.out.println("The Button Number is : " + integer);
                            setForbiddenArea(integer);
                        }
                    }
                }


            }

            public void mouseReleased(MouseEvent e) {
                setShippedArea();
                //setForbiddenArea(-1);

                if (dragEnable) {

                    super.mouseReleased(e);

                    setShippedArea();

                    Point p = findLocation(e);

                    /*
                    int xShip = ship.getX();
                    int yShip = ship.getY();
                    for (int i = xShip; i < xShip + ship.getWidth() ; i+= width[6]) {
                        for (int j = yShip ; j <  yShip + ship.getHeight() ; j += width[6]) {
                            Integer integer = new Integer(findButton(i , j));
                            System.out.println("The Button Number is : " + integer);
                            setForbiddenArea(integer);
                        }
                    }
                    if(forbidAreasPoint.contains(p)){
                        ship.setLocation(firstLocation);
                    }else {
                        ship.setLocation(p);
                    }
                    */

                    if(forbidAreasPoint.contains(p)){
                        ship.setLocation(firstLocation);
                    }else {
                        ship.setLocation(p);
                    }

                    setShippedArea();
                    //setForbiddenArea(-1);
                    ship.repaint();
                }
            }


            public void mouseClicked(MouseEvent e) {
                if(dragEnable){
                    if (e.getClickCount() == 2) {
                        if ((n != 6)) {
                            if (n % 2 == 0) {
                                remove(ship);
                                stickerArray[n] = makePanels(n + 1, e.getXOnScreen(), e.getYOnScreen());
                                add(stickerArray[n], new Integer(counter));
                                counter++;
                            } else {
                                remove(ship);
                                ship.setVisible(false);
                                stickerArray[n] = makePanels(n - 1, e.getXOnScreen(), e.getYOnScreen());

                                add(stickerArray[n], new Integer(counter));
                                counter++;
                            }

                            setShippedArea();
                            Point p = findLocation(e);

                            int xShip = stickerArray[n].getX();
                            int yShip = stickerArray[n].getY();
                            for (int i = xShip; i < xShip + stickerArray[n].getWidth() ; i+= width[6]) {
                                for (int j = yShip ; j <  yShip + stickerArray[n].getHeight() ; j += width[6]) {
                                    Integer integer = new Integer(findButton(i , j));
                                    //setForbiddenArea(integer);
                                    if(forbidAreasPoint.contains(new Point(i,j))){
                                        forbidAreasPoint.remove(new Point(i,j))  ;
                                    }

                                }
                            }
                            if(forbidAreasPoint.contains(p)){
                                stickerArray[n].setLocation(firstLocation);
                            }else {
                                stickerArray[n].setLocation(p);
                            }

                            setShippedArea();
                            //setForbiddenArea(-1);
                            stickerArray[n].repaint();
                        }
                        repaint();
                    }
                }}
        });
        ship.setLocation(x, y);
        ship.setCursor(new Cursor(Cursor.MOVE_CURSOR));
        return ship;
    }

    public void setShippedArea() {

        shippedButtons = new ArrayList(20);

        for (int i = 0; i < getComponentCount(); i++) {

            if (getComponent(i) instanceof StickerLayer) {
                StickerLayer s = (StickerLayer) getComponent(i);
                ImageIcon ic = (ImageIcon) s.getIcon();

                int buttonP = findButton(s.getX(), s.getY());
                shippedButtons.add(buttonP);


                if (ic.equals(ships[0])) {
                    shippedButtons.add(buttonP + 1);
                    shippedButtons.add(buttonP + 2);
                    shippedButtons.add(buttonP + 3);

                }
                if (ic.equals(ships[1])) {
                    shippedButtons.add(buttonP + 10);
                    shippedButtons.add(buttonP + 20);
                    shippedButtons.add(buttonP + 30);

                }
                if (ic.equals(ships[2])) {
                    shippedButtons.add(buttonP + 1);
                    shippedButtons.add(buttonP + 2);

                }
                if (ic.equals(ships[3])) {
                    shippedButtons.add(buttonP + 10);
                    shippedButtons.add(buttonP + 20);

                }
                if (ic.equals(ships[4])) {
                    shippedButtons.add(buttonP + 1);

                }
                if (ic.equals(ships[5])) {
                    shippedButtons.add(buttonP + 10);
                }
                if (ic.equals(ships[6])) {
                }
            }
        }
        //setForbiddenArea();
    }

    public void setForbiddenArea(Integer selfButton) {
        forbiddenAreas = new HashSet<>();
        forbidAreasPoint = new ArrayList<>();

        for (Integer i : shippedButtons) {
            if( !i.equals(selfButton)) {
                System.out.println("it is not equal = " + selfButton + "    i =  " + i);
                //System.out.println("the i is : " + i);
                forbiddenAreas.add(i);
                forbiddenAreas.add(i + 1);
                forbiddenAreas.add(i + -9);
                forbiddenAreas.add(i + 11);
                forbiddenAreas.add(i + 9);
                forbiddenAreas.add(i + 10);
                forbiddenAreas.add(i + -11);
                forbiddenAreas.add(i + -1);
                forbiddenAreas.add(i + -10);
            }
        }

        for (Integer i : forbiddenAreas) {
            if (!(i < 0) && !(i > 100)) {
                findPlace(i);
            }
        }

        //System.out.println(forbiddenAreas);
    }

    public int findButton(int sx, int sy) {

        int x = sx, y = sy;
        int bX = 0, bY = 0;

        for (int i = 0; i < rows.length; i++) {
            if (x == rows[i]) {
                bX = i;
                break;
            }
        }

        for (int i = 0; i < columns.length; i++) {
            if (y == columns[i]) {
                bY = i;
                break;
            }
        }

        int buttonP = (10 * bY) + (bX + 1);
        return buttonP;
    }


    public Point findPlace(int b) {
        int rowElelement = -1 , columnselement = -1;
        rowElelement = ((b - 1) % 10);
        columnselement = (b - 1) / 10;

        Point p = null;

        if( (rowElelement > -1) && (rowElelement < 11) && (columnselement > -1) && (columnselement < 11)){
            int bX = rows[((b - 1) % 10)];
            int bY = columns[(b - 1) / 10];
            forbidAreasPoint.add(new Point(bX, bY));
            p = new Point(bX, bY);
        }
        return p;
    }

    public void doMeasure() {
        rows[0] = GamePanel.boundX;
        columns[0] = GamePanel.boundY;
        //int length = GamePanel.gameFieldW /10;

        for (int i = 0; i < 10; i++) {
            rows[i + 1] = rows[i] + length;
            columns[i + 1] = columns[i] + length;
        }
    }

    public Point findLocation(MouseEvent e) {

        boolean move;

        int theX = -1, theY = -1;
        for (int i = 1; i < 11; i++) {
            if (e.getXOnScreen() < rows[i]) {
                theX = rows[i - 1];
                break;
            }
            if (i == 10) {
                theX = rows[9];
            }
        }

        for (int i = 1; i < 11; i++) {
            if (e.getYOnScreen() < rows[i]) {
                theY = columns[i - 1];
                break;
            }

            if (i == 10) {
                theY = columns[9];
            }
        }
        if (theX == -1 || theY == -1) {
            theX = rows[0];
            theY = columns[0];
        }

        return new Point(theX, theY);
    }

    public void addWaterSticker(int nn){
        counter++;
        Point p = findPlace(nn);
        JButton water = new JButton();
        water.setIcon(iconw);
        water.setLocation(p);
        water.setBorder(BorderFactory.createLineBorder(new Color(133, 193, 233), (int) (scH / 400)));
        water.setBounds((int)p.getX(), (int)p.getY(), (int)iconw.getIconWidth(), (int)iconw.getIconHeight());
        add(water, new Integer(counter));
    }

    public void addExplosionSticker(int nn){

        counter++;
        Point p = findPlace(nn);
        JButton explosion = new JButton();
        explosion.setIcon(icone);
        explosion.setLocation(p);
        explosion.setBorder(BorderFactory.createLineBorder(new Color(133, 193, 233), (int) (scH / 400)));
        explosion.setBounds((int)p.getX(), (int)p.getY(), (int)icone.getIconWidth(), (int)icone.getIconHeight());
        add(explosion, new Integer(counter));
    }

    public class StickerLayer extends JLabel {

        public StickerLayer(ImageIcon icon) {
            setBounds(0, 0, icon.getIconWidth(), icon.getIconHeight());
            setIcon(icon);
            setOpaque(false);
        }
    }

}
