import javax.swing.*;
import java.awt.*;

class FilterPanel extends JPanel{

    //fields for filtering options 
    private String address;
    private String distance;
    private String price;

    private String buttonClicked;
    /**constructor intializing filter panel
     */
    public FilterPanel(String buttonClicked){
        this.buttonClicked = buttonClicked;
    }


    public JPanel getUserInput(String buttonText) {
   
        // Price options
        String[] priceOptions = {"0", "1", "2", "3","4"};
        JComboBox<String> priceDropdown = new JComboBox<>(priceOptions);


        JTextField distanceField = new JTextField(10);
        JTextField addressField = new JTextField(10);

        // Panel for user inputs
        JPanel inputPanel = new JPanel(new GridLayout(4, 7));
        inputPanel.setSize(400,700);
      
        inputPanel.add(new JLabel("Price Range:"));
        inputPanel.add(priceDropdown);

        inputPanel.add(new JLabel("Distance (km):"));
        inputPanel.add(distanceField);

        inputPanel.add(new JLabel("Address:"));
        inputPanel.add(addressField);

      

        int result = JOptionPane.showConfirmDialog(this, inputPanel, "TravelGo: " + buttonClicked, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            price = (String) priceDropdown.getSelectedItem();
            distance = distanceField.getText();
            address = addressField.getText();
        }
   
        return inputPanel;
    }

    /**Getter methods for filter fields 
     */
    public String getAddress(){
        return address;
    }
    public String getPrice(){
        return price;
    }
    public String getDistance(){
        return distance;
    }

}
