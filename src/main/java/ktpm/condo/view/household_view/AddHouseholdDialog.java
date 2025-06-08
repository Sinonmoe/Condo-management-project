package ktpm.condo.view.household_view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/**
 * Custom dialog để thêm hộ khẩu mới với giao diện đẹp và hiện đại
 */
public class AddHouseholdDialog extends JDialog {
    private JTextField apartmentField;
    private JTextField householdCodeField;
    private boolean confirmed = false;
    
    public AddHouseholdDialog(JFrame parent) {
        super(parent, "Thêm hộ khẩu mới", true);
        initializeComponents();
        setupLayout();
        setupEventHandlers();
        setupDialog();
    }
    
    private void initializeComponents() {
        // Tạo các text field với style đẹp
        apartmentField = new JTextField(20);
        householdCodeField = new JTextField(20);
        
        // Cải thiện appearance của text field
        setupTextField(apartmentField, "Nhập số căn hộ (VD: A101, B205)");
        setupTextField(householdCodeField, "Nhập mã hộ khẩu (VD: HK001)");
    }
    
    private void setupTextField(JTextField field, String placeholder) {
        field.setFont(new Font("SansSerif", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        field.setPreferredSize(new Dimension(280, 40));
        
        // Thêm placeholder effect
        field.setForeground(Color.GRAY);
        field.setText(placeholder);
        
        field.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (field.getText().equals(placeholder)) {
                    field.setText("");
                    field.setForeground(Color.BLACK);
                }
            }
            
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (field.getText().isEmpty()) {
                    field.setForeground(Color.GRAY);
                    field.setText(placeholder);
                }
            }
        });
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Header panel
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);
        
        // Main content panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel, BorderLayout.CENTER);
        
        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(70, 130, 180));
        headerPanel.setBorder(new EmptyBorder(20, 25, 20, 25));
        
        // Icon
        JLabel iconLabel = new JLabel("🏠");
        iconLabel.setFont(new Font("SansSerif", Font.PLAIN, 24));
        
        // Title
        JLabel titleLabel = new JLabel("Thêm hộ khẩu mới");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        
        // Subtitle
        JLabel subtitleLabel = new JLabel("Nhập thông tin căn hộ và mã hộ khẩu");
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(220, 220, 220));
        
        JPanel textPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        textPanel.setOpaque(false);
        textPanel.add(titleLabel);
        textPanel.add(subtitleLabel);
        
        headerPanel.add(iconLabel, BorderLayout.WEST);
        headerPanel.add(textPanel, BorderLayout.CENTER);
        
        return headerPanel;
    }
    
    private JPanel createMainPanel() {
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(new EmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(0, 0, 20, 15); // spacing between label and field
        gbc.anchor = GridBagConstraints.WEST;
        
        // First row: Apartment label and field
        gbc.gridx = 0; gbc.gridy = 0;
        JLabel apartmentLabel = createStyledLabel("Căn hộ:", "📍");
        mainPanel.add(apartmentLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0; // Allow field to expand
        gbc.insets = new Insets(0, 0, 20, 0); // no right margin for field
        mainPanel.add(apartmentField, gbc);
        
        // Second row: Household code label and field
        gbc.gridx = 0; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0;
        gbc.insets = new Insets(0, 0, 0, 15); // spacing between label and field
        JLabel codeLabel = createStyledLabel("Mã hộ khẩu:", "🏷️");
        mainPanel.add(codeLabel, gbc);
        
        gbc.gridx = 1; gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(0, 0, 0, 0);
        mainPanel.add(householdCodeField, gbc);
        
        return mainPanel;
    }
    
    private JLabel createStyledLabel(String text, String icon) {
        JLabel label = new JLabel(icon + " " + text);
        label.setFont(new Font("SansSerif", Font.BOLD, 14));
        label.setForeground(new Color(60, 60, 60));
        return label;
    }
    
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 20));
        buttonPanel.setBackground(new Color(248, 249, 250));
        buttonPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(230, 230, 230)));
        
        // Cancel button
        JButton cancelButton = createStyledButton("Hủy", new Color(108, 117, 125), Color.WHITE);
        cancelButton.setPreferredSize(new Dimension(100, 35));
        
        // OK button
        JButton okButton = createStyledButton("Thêm", new Color(40, 167, 69), Color.WHITE);
        okButton.setPreferredSize(new Dimension(100, 35));
        
        // Add action listeners
        cancelButton.addActionListener(e -> {
            confirmed = false;
            dispose();
        });
        
        okButton.addActionListener(e -> {
            if (validateInput()) {
                confirmed = true;
                dispose();
            }
        });
        
        buttonPanel.add(cancelButton);
        buttonPanel.add(okButton);
        
        // Set default button
        getRootPane().setDefaultButton(okButton);
        
        return buttonPanel;
    }
    
    private JButton createStyledButton(String text, Color bgColor, Color textColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(textColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            Color originalColor = bgColor;
            
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(originalColor);
            }
        });
        
        return button;
    }
    
    private void setupEventHandlers() {
        // Event handlers are now set up in createButtonPanel()
    }
    
    private boolean validateInput() {
        String apartment = getApartmentText();
        String code = getHouseholdCodeText();
        
        if (apartment.isEmpty()) {
            showErrorMessage("Vui lòng nhập số căn hộ!");
            apartmentField.requestFocus();
            return false;
        }
        
        if (code.isEmpty()) {
            showErrorMessage("Vui lòng nhập mã hộ khẩu!");
            householdCodeField.requestFocus();
            return false;
        }
        
        // Validate apartment format (basic)
        if (!apartment.matches("^[A-Z]\\d{3}$")) {
            showErrorMessage("Số căn hộ không hợp lệ!\nVui lòng nhập theo định dạng: A101, B205, C303...");
            apartmentField.requestFocus();
            return false;
        }
        
        // Validate household code format
        if (!code.matches("^HK\\d{3}$")) {
            showErrorMessage("Mã hộ khẩu không hợp lệ!\nVui lòng nhập theo định dạng: HK001, HK002...");
            householdCodeField.requestFocus();
            return false;
        }
        
        return true;
    }
    
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            message,
            "Lỗi nhập liệu",
            JOptionPane.ERROR_MESSAGE
        );
    }
    
    private void setupDialog() {
        setSize(450, 350); // Tăng chiều rộng để phù hợp với layout ngang
        setLocationRelativeTo(getParent());
        setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    // Getter methods
    public String getApartmentText() {
        String text = apartmentField.getText();
        return text.startsWith("Nhập số căn hộ") ? "" : text.trim().toUpperCase();
    }
    
    public String getHouseholdCodeText() {
        String text = householdCodeField.getText();
        return text.startsWith("Nhập mã hộ khẩu") ? "" : text.trim().toUpperCase();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
}