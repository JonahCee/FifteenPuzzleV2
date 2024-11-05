import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class FifteenPuzzleVTwo extends JFrame implements ActionListener {
   private JButton[] buttons = new JButton[16]; // 15 brickor + tom plats
    private JPanel gridPanel;
    private JButton newGameButton;
    private Font buttonFont = new Font("Arial", Font.BOLD, 20);

    // Definiera färger för brickorna
    Color tileColor = new Color(70, 130, 180);
    Color textColor = Color.WHITE;
    Color emptyTileColor = Color.LIGHT_GRAY;

        // Konstruktor
    protected FifteenPuzzleVTwo() {
        setTitle("15-spel");
        setSize(400, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        //Rutnät (4x4)
        gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(4, 4, 5, 5));
        add(gridPanel, BorderLayout.CENTER);

        //Knapp "Nytt spel"
        newGameButton = new JButton("Nytt spel");
        newGameButton.addActionListener(e -> shuffleTiles());
        add(newGameButton, BorderLayout.SOUTH);

        initializeTiles();
        //(viktig)
        setVisible(true);
    }
    // Skapar och ställer in knappar
    private void initializeTiles() {
        for (int i = 0; i < 15; i++) {
            buttons[i] = new JButton(String.valueOf(i + 1));
            buttons[i].setFont(buttonFont);
            buttons[i].setBackground(tileColor);
            buttons[i].setForeground(textColor);
            buttons[i].setOpaque(true);
            buttons[i].setBorderPainted(false);
            buttons[i].addActionListener(this);
            gridPanel.add(buttons[i]);
        }

        // Den sista knappen representerar den tomma platsen
        buttons[15] = new JButton("");
        buttons[15].setFont(buttonFont);
        buttons[15].setBackground(emptyTileColor);
        buttons[15].setOpaque(true);
        buttons[15].setBorderPainted(false);
        buttons[15].setEnabled(false); // Gör den otillgänglig
        buttons[15].addActionListener(this);
        gridPanel.add(buttons[15]);
    }

    // Metod för att blanda brickorna
    private void shuffleTiles() {
        ArrayList<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= 15; i++) {
            numbers.add(i);
        }
        Collections.shuffle(numbers);

        for (int i = 0; i < 15; i++) {
            buttons[i].setText(String.valueOf(numbers.get(i)));
            buttons[i].setEnabled(true); // Aktivera numrerade knappar
            buttons[i].setFont(buttonFont); // Återställ fontstorlek
            buttons[i].setBackground(tileColor); // Sätt bakgrundsfärg
            buttons[i].setForeground(textColor); // Sätt textfärg
        }

        buttons[15].setText(""); // Gör sista knappen tom
        buttons[15].setEnabled(false); // Sätt tomma knappen som otillgänglig
        buttons[15].setBackground(emptyTileColor); // Sätt tom bakgrundsfärg
    }

    // Metod som kallas när knapp klickas
    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clickedButton = (JButton) e.getSource();
        int clickedIndex = -1;
        int emptyIndex = -1;

        // Hitta index för klickade knappen och tomma knappen
        for (int i = 0; i < 16; i++) {
            if (buttons[i] == clickedButton) {
                clickedIndex = i;
            }
            if (buttons[i].getText().isEmpty()) {
                emptyIndex = i;
            }
        }

        // Kolla om den klickade knappen ligger intill den tomma (metod nedan)
        if (isAdjacent(clickedIndex, emptyIndex)) {
            changeTiles(clickedIndex, emptyIndex); // Byt plats på knapparna
        }

        // Om spelaren vunnit (metod nedan)
        if (checkIfSolved()) {
            JOptionPane.showMessageDialog(this, "Grattis, du vann!");
        }
    }


    // Metod för att byta plats mellan två knappar
    private void changeTiles(int clickedIndex, int emptyIndex) {
        // Byter text
        buttons[emptyIndex].setText(buttons[clickedIndex].getText());
        buttons[clickedIndex].setText("");

        // Byter bakgrundsfärg
        buttons[emptyIndex].setBackground(tileColor);
        buttons[clickedIndex].setBackground(emptyTileColor);


        // Byter textfärg
        buttons[emptyIndex].setForeground(textColor);
        buttons[clickedIndex].setForeground(textColor);

        // Byter tillgänglighet
        buttons[emptyIndex].setEnabled(true);
        buttons[clickedIndex].setEnabled(false);
    }

    // Metod om två knappar är bredvid varandra
    private boolean isAdjacent(int index1, int index2) {
        int row1 = index1 / 4;
        int col1 = index1 % 4;
        int row2 = index2 / 4;
        int col2 = index2 % 4;

        return (Math.abs(row1 - row2) + Math.abs(col1 - col2)) == 1;
    }

    // Bool om brickorna är i rätt ordning
    private boolean checkIfSolved() {
        for (int i = 0; i < 15; i++) {
            if (!buttons[i].getText().equals(String.valueOf(i + 1))) {
                return false;
            }
        }
        return buttons[15].getText().isEmpty();
    }

    public static void main(String[] args) {
        FifteenPuzzleVTwo Femton = new FifteenPuzzleVTwo();
    }
}