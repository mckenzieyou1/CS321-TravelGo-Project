import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ArrayList;

public class ButtonPanel {
    private final JPanel panel;
    private JButton[] button;
    private JLabel label;
    private String categoryClicked;
    private FilterPanel filterPanel;
    private List<String> apiResult;
    private PanelAPI resultPanel;
    private JFrame mainFrame; // Main frame to display the result panel

    public ButtonPanel(JFrame mainFrame) {
        this.panel = createSelectionPanel();
        this.apiResult = null;
        this.mainFrame = mainFrame;
    }

    private JPanel createSelectionPanel() {
        JPanel panel = new JPanel();
        GridLayout layout = new GridLayout(2, 2);
        layout.setHgap(10);
        layout.setVgap(10);
        panel.setLayout(layout);
        String[] categories = categoriesSelection();
        String[] images = imageIcons();
        button = new JButton[4];
        for (int index = 0; index < button.length; index++) {
            BorderLayout buttonLayout = new BorderLayout();
            ImageIcon image = new ImageIcon(images[index]);
            label = new JLabel(categories[index]);
            label.setFont(new Font("Serif", Font.BOLD, 18));
            button[index] = new JButton(image);
            button[index].setLayout(buttonLayout);
            button[index].add(label, BorderLayout.NORTH);
            button[index].addActionListener(e -> buttonClicked(e)); // Handle button clicks
            button[index].setFocusable(false);
            button[index].setActionCommand(categories[index]); //Replaces setText()
            //button[index].setText(categories[index]); // Needed for API call but doesn't look so good.
            panel.add(button[index]);
        }
        return panel;
    }

    private String[] categoriesSelection() {
        String[] columns = {"Cafe", "Museum", "Restaurant", "Church"};
        return columns;
    }

    private String[] imageIcons() {
        String[] images = {"coffee.png", "museum.png", "rest.png", "church.png"};
        return images;
    }

    /**
     * This method opens a new GUI window when user selects a category. It creates a new FilterPanel object
     * from the filter panel class
     * @param e
     */
    private void buttonClicked(ActionEvent e) {
        JButton button = (JButton) e.getSource();
        categoryClicked = e.getActionCommand().toLowerCase();
        String buttonText = categoryClicked;

        // Open the filter panel and get user input
        this.filterPanel = new FilterPanel(categoryClicked);
        filterPanel.getUserInput(buttonText);

        // Create UserLocation object to make API call and get coordinates of address
        UserLocation location = new UserLocation(filterPanel.getAddress());
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        // Format coordinates and distance
        String lat = Double.toString(latitude);
        String lng = Double.toString(longitude);
        double dist = Double.parseDouble(filterPanel.getDistance()) * 1000;
        String distance = Double.toString(dist);

        // Call the Places API
        GoogleAPI apiCall = new GoogleAPI();
        apiResult = apiCall.makeCall(lat, lng, distance, buttonText, filterPanel.getPrice());
     

        if(apiResult.size() < 6 || apiResult == null) {
            System.out.println("Issue with list");
        }

        ImageIcon imgIcon = apiCall.getPhoto(apiResult.get(6));
        if (imgIcon == null) {
            //Don't display image / placeholder image / error message
        }
        
        // Create the result panel
        resultPanel = new PanelAPI(apiResult, imgIcon);  
        // Add the result panel to the main frame
        mainFrame.getContentPane().removeAll(); 
        mainFrame.add(resultPanel); 
        mainFrame.revalidate();
        mainFrame.repaint(); 
    }

    public JPanel getPanel() {
        return panel;
    }

    public String getCategoryClicked() {
        return categoryClicked;
    }

    public List<String> getApiResult() {
        return apiResult;
    }

    public PanelAPI getPanelAPI() {
        return resultPanel;
    }
}
