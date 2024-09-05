import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;

public class InstructionsDialog extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;
	
	private JEditorPane editorPane;
	
	/**
	 * @param view ConnectNGamFrame
	 * This is the constructor for the normal instructions diaglog
	 * which is accessible from the ConnectN Frame and can be clicked
	 * any time during the game.
	 */
	public InstructionsDialog() {
		//super(view.getFrame(), "Instructions", true);
		this.cancelAction = new CancelAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		//setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}

	/**
	 * @return JPanel of main panel
	 * Creates the main panel of the game with the instructions which are
	 * located in the resouces folder of the project.
	 */
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		URL url = InstructionsDialog.class.getResource("instructions.htm");
		
		editorPane = new JEditorPane();
		editorPane.setEditable(false);
		editorPane.setContentType("text/html");
		try {
			editorPane.setPage(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JScrollPane scrollPane = new JScrollPane(editorPane);
		//scrollPane.setPreferredSize(new Dimension(400, 700));
		scrollPane.setPreferredSize(new Dimension(400, 500));
		panel.add(scrollPane, BorderLayout.CENTER);
		
		return panel;
	}
	/**
	 * @return JPanel of button panel
	 * Creates the button panel which includes a cancel button to get
	 * out of the instructions window.
	 */
	private JPanel createButtonPanel() {
		JPanel panel = new JPanel(new FlowLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		InputMap inputMap = panel.getInputMap(JPanel.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelAction");
		ActionMap actionMap = panel.getActionMap();
		actionMap.put("cancelAction", cancelAction);
		
		JButton button = new JButton("Cancel");
		button.addActionListener(cancelAction);
		panel.add(button);
		
		return panel;
	}
	/**
	 * Cancel action which disposes of the frame.
	 */
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
		
	}

}