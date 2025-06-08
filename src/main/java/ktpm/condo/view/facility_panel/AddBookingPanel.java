package ktpm.condo.view.facility_panel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ktpm.condo.controller.FacilityController;
import ktpm.condo.model.entity.Facility;
import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.view.BasePanel;

public class AddBookingPanel extends BasePanel {
    private final JComboBox<Facility> cbFacility;
    private final JTextField tfHouseholdId;
    private final JTextField tfDate;
    private final JButton btnDatePicker;
    private final FacilityController controller;

    // Colors for modern UI
    private static final Color PRIMARY_COLOR = new Color(70, 130, 180);
    private static final Color SECONDARY_COLOR = new Color(240, 248, 255);
    private static final Color SUCCESS_COLOR = new Color(46, 125, 50);
    private static final Color ERROR_COLOR = new Color(211, 47, 47);
    private static final Color TEXT_COLOR = new Color(33, 37, 41);

    public AddBookingPanel(FacilityController controller) {
        this.controller = controller;
        
        // Initialize components directly in constructor
        List<Facility> facilities = controller.getAllFacilities();
        this.cbFacility = new JComboBox<>(facilities.toArray(new Facility[0]));
        this.tfHouseholdId = new JTextField(15);
        this.tfDate = new JTextField(15);
        this.btnDatePicker = new JButton("");
        
        initializeComponents();
        setupLayout();
        styleComponents();
    }

    private void initializeComponents() {
        // Setup component behaviors
        setupPlaceholders();
        
        // Add date picker functionality
        btnDatePicker.addActionListener(e -> showDatePicker());
        btnDatePicker.setToolTipText("Chọn ngày");
    }

    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        setBorder(new EmptyBorder(20, 20, 20, 20));
        setBackground(Color.WHITE);

        // Title panel
        JPanel titlePanel = createTitlePanel();
        add(titlePanel, BorderLayout.NORTH);

        // Main form panel
        JPanel formPanel = createMainFormPanel();
        add(formPanel, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JPanel createTitlePanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBackground(Color.WHITE);
        
        JLabel titleLabel = new JLabel("Thêm lượt đặt tiện ích");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        titleLabel.setIcon(new ImageIcon(createColoredIcon()));
        
        panel.add(titleLabel);
        return panel;
    }

    private JPanel createMainFormPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new TitledBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1), 
                                       "Thông tin đặt chỗ", 
                                       TitledBorder.LEFT, 
                                       TitledBorder.TOP, 
                                       new Font("Segoe UI", Font.BOLD, 12), 
                                       PRIMARY_COLOR));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        // Facility selection
        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(createStyledLabel("Tên tiện ích:", ""), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(cbFacility, gbc);

        // Household ID
        gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(createStyledLabel("ID hộ khẩu:", ""), gbc);
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(tfHouseholdId, gbc);

        // Date selection
        gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
        panel.add(createStyledLabel("Ngày sử dụng:", ""), gbc);
        
        JPanel datePanel = new JPanel(new BorderLayout(5, 0));
        datePanel.setBackground(Color.WHITE);
        datePanel.add(tfDate, BorderLayout.CENTER);
        datePanel.add(btnDatePicker, BorderLayout.EAST);
        
        gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
        panel.add(datePanel, gbc);

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        panel.setBackground(Color.WHITE);

        JButton btnSave = createStyledButton("Lưu", SUCCESS_COLOR, "");
        JButton btnCancel = createStyledButton("Hủy", Color.GRAY, "");

        btnSave.addActionListener(e -> {
            if (saveBooking()) {
                showSuccessMessage();
                clearForm();
            }
        });

        btnCancel.addActionListener(e -> {
            if (confirmCancel()) {
                clearForm();
                // Close dialog if this is in a dialog
                SwingUtilities.getWindowAncestor(this).dispose();
            }
        });

        panel.add(btnCancel);
        panel.add(btnSave);
        return panel;
    }

    private void styleComponents() {
        // Style ComboBox
        cbFacility.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        cbFacility.setBackground(Color.WHITE);
        cbFacility.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        // Style TextFields
        styleTextField(tfHouseholdId);
        styleTextField(tfDate);

        // Style Date Picker Button
        btnDatePicker.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        btnDatePicker.setBackground(SECONDARY_COLOR);
        btnDatePicker.setBorder(BorderFactory.createLineBorder(PRIMARY_COLOR, 1));
        btnDatePicker.setFocusPainted(false);
        btnDatePicker.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    private void styleTextField(JTextField textField) {
        textField.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 220, 220), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        textField.setBackground(Color.WHITE);
        textField.setForeground(TEXT_COLOR);
    }

    private JLabel createStyledLabel(String text, String emoji) {
        JLabel label = new JLabel(emoji + " " + text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 12));
        label.setForeground(TEXT_COLOR);
        return label;
    }

    private JButton createStyledButton(String text, Color bgColor, String emoji) {
        JButton button = new JButton(emoji + " " + text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 12));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Add hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }

    private void setupPlaceholders() {
        tfHouseholdId.setToolTipText("Nhập ID hộ khẩu (số nguyên)");
        tfDate.setToolTipText("Định dạng: YYYY-MM-DD (ví dụ: 2025-06-08)");
        cbFacility.setToolTipText("Chọn tiện ích muốn đặt");
    }

    private void showDatePicker() {
        // Simple date picker dialog
        String currentDate = tfDate.getText().isEmpty() ? 
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE) : tfDate.getText();
        
        String newDate = JOptionPane.showInputDialog(
            this,
            "Nhập ngày sử dụng (YYYY-MM-DD):",
            "Chọn ngày",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (newDate != null && !newDate.trim().isEmpty()) {
            try {
                LocalDate.parse(newDate.trim());
                tfDate.setText(newDate.trim());
            } catch (DateTimeParseException e) {
                showErrorMessage("Định dạng ngày không hợp lệ. Vui lòng sử dụng YYYY-MM-DD");
            }
        }
    }

    private void showSuccessMessage() {
        JOptionPane.showMessageDialog(
            this,
            " Đã thêm lượt đặt tiện ích thành công!",
            "Thành công",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(
            this,
            " " + message,
            "Lỗi",
            JOptionPane.ERROR_MESSAGE
        );
    }

    private boolean confirmCancel() {
        return JOptionPane.showConfirmDialog(
            this,
            "Bạn có chắc chắn muốn hủy? Dữ liệu chưa lưu sẽ bị mất.",
            "Xác nhận hủy",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        ) == JOptionPane.YES_OPTION;
    }

    private void clearForm() {
        cbFacility.setSelectedIndex(0);
        tfHouseholdId.setText("");
        tfDate.setText("");
    }

    private byte[] createColoredIcon() {
        // Create a simple colored icon (you can replace this with actual icon)
        return new byte[]{};
    }

    /**
     * Lưu dữ liệu lượt đặt tiện ích với validation được cải thiện.
     * 
     * @return true nếu thành công, false nếu lỗi dữ liệu hoặc lưu thất bại
     */
    public boolean saveBooking() {
        try {
            // Validate facility selection
            Facility selectedFacility = (Facility) cbFacility.getSelectedItem();
            if (selectedFacility == null) {
                showErrorMessage("Vui lòng chọn một tiện ích.");
                return false;
            }

            // Validate household ID
            String householdIdText = tfHouseholdId.getText().trim();
            if (householdIdText.isEmpty()) {
                showErrorMessage("Vui lòng nhập ID hộ khẩu.");
                tfHouseholdId.requestFocus();
                return false;
            }

            int householdId;
            try {
                householdId = Integer.parseInt(householdIdText);
                if (householdId <= 0) {
                    showErrorMessage("ID hộ khẩu phải là số nguyên dương.");
                    tfHouseholdId.requestFocus();
                    return false;
                }
            } catch (NumberFormatException e) {
                showErrorMessage("ID hộ khẩu phải là số nguyên hợp lệ.");
                tfHouseholdId.requestFocus();
                return false;
            }

            // Validate date
            String dateText = tfDate.getText().trim();
            if (dateText.isEmpty()) {
                showErrorMessage("Vui lòng nhập ngày sử dụng.");
                tfDate.requestFocus();
                return false;
            }

            LocalDate usageDate;
            try {
                usageDate = LocalDate.parse(dateText);
                if (usageDate.isBefore(LocalDate.now())) {
                    showErrorMessage("Ngày sử dụng không thể là ngày trong quá khứ.");
                    tfDate.requestFocus();
                    return false;
                }
            } catch (DateTimeParseException e) {
                showErrorMessage("Định dạng ngày không hợp lệ. Vui lòng sử dụng YYYY-MM-DD");
                tfDate.requestFocus();
                return false;
            }

            // Create and save booking
            FacilityBooking fb = new FacilityBooking();
            fb.setFacilityId(selectedFacility.getId());
            fb.setHouseholdId(householdId);
            fb.setUsageDate(usageDate);

            boolean success = controller.addBooking(fb);
            if (!success) {
                showErrorMessage("Không thể thêm lượt đặt. Vui lòng thử lại.");
            }
            return success;

        } catch (Exception ex) {
            showErrorMessage("Đã xảy ra lỗi không mong muốn: " + ex.getMessage());
            return false;
        }
    }
}