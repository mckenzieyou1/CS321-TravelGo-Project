import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class travelappFrame {
    private final JFrame frame;
    private final ButtonPanel buttonSelection;

    public travelappFrame() {
        this.frame = createAndShowGUI();
        this.buttonSelection = new ButtonPanel(frame); // Pass the frame to ButtonPanel
        frame.add(buttonSelection.getPanel());
        frame.pack();
        frame.setSize(400, 700);
        frame.setVisible(true);
    }

    private JFrame createAndShowGUI() {
        JFrame frame = new JFrame("TravelGo");
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.setJMenuBar(createMenuBar());
        frame.setResizable(false);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) {
                shutdown();
            }
        });
        return frame;
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu helpMenu = new JMenu("Help");
        menuBar.add(helpMenu);

        JMenuItem instructionsItem = new JMenuItem("Instructions...");
        instructionsItem.addActionListener(event -> new InstructionsDialog());
        helpMenu.add(instructionsItem);

        JMenuItem aboutItem = new JMenuItem("About...");
        aboutItem.addActionListener(event -> new AboutDialogue());
        helpMenu.add(aboutItem);

        return menuBar;
    }

    private void shutdown() {
        frame.dispose();
        System.exit(0);
    }

    public static void main(String[] args) {
        new travelappFrame();
    }
}
