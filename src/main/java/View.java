import sweeper.controller.Box;
import sweeper.controller.Coord;
import sweeper.controller.Game;
import sweeper.controller.Ranges;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class View extends JFrame {

    private Game game;

    private JPanel panel;
    private JLabel label;

    private final int COLS = 9;


    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args) throws IOException {
        new View();
    }

    private View() {
        game = new Game(COLS, ROWS, BOMBS);
        game.start();
        setImage();
        initLabel();
        initPanel();
        initFrame();
    }

    public View(int bombs, String[] coords) throws IOException {
        game = new Game(COLS, ROWS, bombs);

        List<Coord> coordList = new ArrayList<Coord>();
        for (int i = 0; i < bombs; i++) {
            coordList.add(new Coord(Integer.parseInt(coords[i].split(",")[0]),
                    Integer.parseInt(coords[i].split(",")[1])));
        }
        game.start2(coordList);
        setImage();
        initLabel();
        initPanel();
        initFrame();
    }

    private void initLabel() {
        label = new JLabel(" ");
        add(label, BorderLayout.SOUTH);
    }

    private void initPanel() {
        panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                for (Coord coord : Ranges.getAllCoords()) {
                    g.drawImage((Image) game.getBox(coord).image,
                            coord.x * IMAGE_SIZE,
                            coord.y * IMAGE_SIZE, this);
                }
            }
        };

        panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coord coord = new Coord(x, y);
                if (e.getButton() == MouseEvent.BUTTON1) {
                    game.pressLeftButton(coord);
                    panel.repaint();
                }
                if (e.getButton() == MouseEvent.BUTTON3) {
                    game.pressRightButton(coord);
                    panel.repaint();
                }
                if (e.getButton() == MouseEvent.BUTTON2) {
                    game.start();
                    panel.repaint();
                }
                label.setText(getMessage());
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add(panel);
    }

    private String getMessage() {
        switch (game.getState()) {
            case INGAME:
                return "Thinking";
            case EXPLODED:
                return "Exploded";
            case VICTORY:
                return "VICTORY !!!";
            default:
                return "";
        }
    }

    private void initFrame() {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Сапёр");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }

    private void setImage() {
        for (Box box : Box.values()) {
            box.image = getImage(box.name());
        }
    }

    private Image getImage(String name) {
        String fileName = "img/" + name.toLowerCase() + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(fileName));
        return icon.getImage();
    }

    public Game getGame() {
        return game;
    }

    public int getCOLS() {
        return COLS;
    }

    public int getROWS() {
        return ROWS;
    }

}
