import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

public class AboutDialogue extends JDialog {
	
	private static final long serialVersionUID = 1L;
	
	private final CancelAction cancelAction;

	public AboutDialogue() {
		//super(view.getFrame(), "About", true);
		this.cancelAction = new CancelAction();
		
		add(createMainPanel(), BorderLayout.CENTER);
		add(createButtonPanel(), BorderLayout.SOUTH);
		
		pack();
		//setLocationRelativeTo(view.getFrame());
		setVisible(true);
	}
	/**
	 * @return JPanel
	 * Creates the main panel of the about dialog which 
	 * has the title (about connect n) a description of the 
	 * creator of the game and the author of this version, 
	 * me.
	 */
	private JPanel createMainPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(0, 5, 5, 30);
		
		gbc.gridwidth = 2;
		gbc.gridx = 0;
		gbc.gridy = 0;
		JLabel label = new JLabel("About TravelGo");
		label.setFont(new Font("Serif", Font.BOLD, 18));
		label.setHorizontalAlignment(JLabel.CENTER);
		Color backgroundColor = label.getBackground();
		panel.add(label, gbc);
		
		gbc.gridy++;
		String text = "TravelGo was created by four computer scientist "
				+ "students during their study abroad in Vienna Austria. "
				+ "This travel app was created to enhance tourists travels "
                + "in Vienna. We came up with this idea because Professor "
                + "Pettit encourages free time, but we aren't always sure "
                + "how best to spend our time in this new and exciting city. As a "
                + "result of these deliemmas, we created TravelGo so tourists " 
                + "visiting Vienna can maximize their travels and ensure they "
                + "have a good, unique visit. TravelGo lets users personalize "
                + "their day to day activities and creates a fun interactive "
                + "user expereience while exposing vistors to hidden gems in "
                + "the beautiful city of Vienna, Austria!";
		JTextArea textArea = new JTextArea(15, 22);
		textArea.setBackground(backgroundColor);
		textArea.setEditable(false);
		//textArea.setFont();
		textArea.setLineWrap(true);
		textArea.setText(text);
		textArea.setWrapStyleWord(true);
		panel.add(textArea, gbc);
		
		gbc.gridwidth = 1;
		gbc.gridy++;
		label = new JLabel("Authors:");
		label.setFont(new Font("Serif", Font.BOLD, 15));
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel("Annekke, Kaitlyn, Josabeth, McKenzie");
		//textArea.setFont();
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Date Created:");
		label.setFont(new Font("Serif", Font.BOLD, 15));
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel("01 July 2024");
		//textArea.setFont();
		panel.add(label, gbc);
		
		gbc.gridx = 0;
		gbc.gridy++;
		label = new JLabel("Version:");
		label.setFont(new Font("Serif", Font.BOLD, 15));
		panel.add(label, gbc);
		
		gbc.gridx++;
		label = new JLabel("1.0");
		//textArea.setFont();
		panel.add(label, gbc);
		
		return panel;
	}
	/**
	 * @return JPanel of buttons
	 * Creates the button panel where you can cancel out of
	 * the popup.
	 */
	/**
	 * @return JPanel of buttons
	 * Creates the button panel where you can cancel out of
	 * the popup.
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
	/*
	 * Cancel button actions (dispose of the frame)
	 */
	private class CancelAction extends AbstractAction {

		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent event) {
			dispose();
		}
		
	}

}