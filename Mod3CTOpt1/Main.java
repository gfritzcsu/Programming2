package Programming2.Mod3CTOpt1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Main extends JFrame {

    private final JTextArea output;
    private final JPanel contentPanel;
    private final JScrollPane scrollPane;

    private static final Random RNG = new Random();

    // Tracks the color currently shown
    private Color currentColor;

    // Action so we can update the label after each click
    private AbstractAction greenAction;

    public Main() {
        super("Menu Demo");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 420);
        setLocationRelativeTo(null);

        contentPanel = new JPanel(new BorderLayout());
        setContentPane(contentPanel);

        output = new JTextArea(8, 40);
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        scrollPane = new JScrollPane(output);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Start with the default background color
        currentColor = contentPanel.getBackground();

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        // Option 1 print data and time stamp
        menu.add(new JMenuItem(new AbstractAction("Print date & time") {
            @Override public void actionPerformed(ActionEvent e) {
                String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
                output.append(stamp + System.lineSeparator());
                output.setCaretPosition(output.getDocument().getLength());
            }
        }));

        // Option 2 write to log.txt in the current directory where the program is ran from (NOTE: this option overwrites the previous log)
        menu.add(new JMenuItem(new AbstractAction("Write text box to log.txt") {
            @Override public void actionPerformed(ActionEvent e) { writeToLog(); }
        }));

        // Option 3 change to a random hue of green upon each click and label current to new color
        greenAction = new AbstractAction(labelFor(currentColor)) {
            @Override public void actionPerformed(ActionEvent e) {
                Color prev = currentColor;                       // current color
                Color fresh = nextRandomGreenDifferentFrom(prev); // new random green that is now in use
                applyBackground(fresh);

                output.append("Changed background from " + toHex(prev)
                        + " to " + toHex(fresh) + System.lineSeparator());
                output.setCaretPosition(output.getDocument().getLength());

                currentColor = fresh;                            // used as previous color when clicked
                greenAction.putValue(Action.NAME, labelFor(currentColor));
            }
        };
        menu.add(new JMenuItem(greenAction));

        // Option 4 exit the program
        menu.add(new JMenuItem(new AbstractAction("Exit") {
            @Override public void actionPerformed(ActionEvent e) { System.exit(0); }
        }));
    }

    private static String labelFor(Color c) {
        return "Set background to random green (prev " + toHex(c) + ")";
    }

    private void writeToLog() {
        File file = new File("log.txt");

        // Prompt only if the log already has content (size > 0).
        boolean hasContent = file.exists() && file.length() > 0;

        if (hasContent) {
            int choice = JOptionPane.showConfirmDialog(
                    this,
                    "<html>This will <b>overwrite</b> the current contents of:<br><code>" +
                            file.getAbsolutePath() + "</code><br><br>Proceed?</html>",
                    "Confirm overwrite",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.WARNING_MESSAGE
            );
            if (choice != JOptionPane.YES_OPTION) {
                output.append("Write canceled. File not modified." + System.lineSeparator());
                return;
            }
        }

        try (FileWriter fw = new FileWriter(file, false)) { // overwrite
            fw.write(output.getText());
            JOptionPane.showMessageDialog(this,
                    (hasContent ? "Overwrote" : "Created") + " log.txt (" +
                            output.getText().length() + " chars) at\n" + file.getAbsolutePath(),
                    "Saved", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this,
                    "Failed to write log.txt:\n" + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Apply the background across visible components so the color changes can be seen in the background
    private void applyBackground(Color bg) {
        getContentPane().setBackground(bg);
        contentPanel.setBackground(bg);
        scrollPane.setBackground(bg);
        scrollPane.getViewport().setBackground(bg);
        output.setBackground(bg);
        output.setForeground(contrastingTextColor(bg));
        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // Randomizes green hues
    private static Color randomGreen() {
        float hue = clamp(0.28f + RNG.nextFloat() * 0.10f, 0f, 1f); // ~[0.28, 0.38]
        float sat = 0.5f + RNG.nextFloat() * 0.5f;                  // [0.5, 1.0]
        float bri = 0.6f + RNG.nextFloat() * 0.4f;                  // [0.6, 1.0]
        return Color.getHSBColor(hue, sat, bri);
    }

    // Avoid picking the exact same hue twice that way a fresh color will always be used
    private static Color nextRandomGreenDifferentFrom(Color prev) {
        String prevHex = toHex(prev);
        for (int i = 0; i < 10; i++) {
            Color c = randomGreen();
            if (!toHex(c).equals(prevHex)) return c;
        }
        return randomGreen();
    }

    private static float clamp(float v, float min, float max) {
        return Math.max(min, Math.min(max, v));
    }

    private static String toHex(Color c) {
        return String.format("#%02X%02X%02X", c.getRed(), c.getGreen(), c.getBlue());
    }

    private static Color contrastingTextColor(Color bg) {
        double r = bg.getRed() / 255.0;
        double g = bg.getGreen() / 255.0;
        double b = bg.getBlue() / 255.0;
        double luminance = 0.299 * r + 0.587 * g + 0.114 * b;
        return (luminance > 0.55) ? Color.BLACK : Color.WHITE;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Main().setVisible(true));
    }
}