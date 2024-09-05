import javax.swing.*;
import java.awt.*;
import java.util.List;

class PanelAPI extends JPanel {
    private String name;
    private String address;
    private String priceLevel;
    private String website;
    private ImageIcon imageIcon;

    public PanelAPI(List<String> APIresults, ImageIcon imageIcon) {
        name = APIresults.get(0);
        address = APIresults.get(3);
        website = APIresults.get(4);
        priceLevel = APIresults.get(5);
        this.imageIcon = imageIcon;
        displayPanel();
    }

    public void displayPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
      
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 0;
    
        JLabel nameLabel = new JLabel("GO to " + name + "!");
        nameLabel.setFont(new Font("Serif", Font.BOLD, 12));
        add(nameLabel, gbc);
    
        gbc.gridy++;
        JLabel addressLabel = new JLabel("Address: " + address);
        addressLabel.setFont(new Font("Serif", Font.BOLD, 12));
        add(addressLabel, gbc);
    
        gbc.gridy++;
        JLabel websiteLabel = new JLabel("Website: " + website);
        websiteLabel.setFont(new Font("Serif", Font.BOLD, 12));
        add(websiteLabel, gbc);
    
        gbc.gridy++;
        JLabel priceLevelLabel = new JLabel("Price Level: " + priceLevel);
        priceLevelLabel.setFont(new Font("Serif", Font.BOLD, 12));
        add(priceLevelLabel, gbc); 
        
    
        gbc.gridy++;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.VERTICAL;
        add(Box.createVerticalGlue(), gbc);
    
        // Add the image icon at the bottom
        gbc.gridy++;
        gbc.weighty = 0;
        gbc.anchor = GridBagConstraints.PAGE_END;
        JLabel imageIconLabel = new JLabel(imageIcon);
        add(imageIconLabel, gbc);
    }
    
}
