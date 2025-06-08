package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.FeeController;
import ktpm.condo.model.entity.Fee;

public class FeePanel extends BasePanel {
    private final FeeController controller = new FeeController();

    private final JTable tableUnpaid;
    private final JTable tablePaid;
    private final DefaultTableModel modelUnpaid;
    private final DefaultTableModel modelPaid;

    private final JTextField tfFilterId = createTextField(5);
    private final JTextField tfFilterType = createTextField(10);

    private final JFrame parentFrame;

    public FeePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // Bộ lọc
        JPanel filterPanel = new JPanel();
        filterPanel.setBorder(BorderFactory.createTitledBorder("🔎 Bộ lọc tìm kiếm"));
        filterPanel.add(createLabel("ID hộ khẩu:"));
        filterPanel.add(tfFilterId);
        filterPanel.add(createLabel("Loại phí:"));
        filterPanel.add(tfFilterType);
        JButton btnFilter = createButton("Lọc");
        filterPanel.add(btnFilter);
        add(filterPanel, BorderLayout.NORTH);

        // Hai bảng với tiêu đề rõ ràng
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Bảng khoản chưa đóng
        modelUnpaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);
        tableUnpaid = createTable(modelUnpaid);
        styleTable(tableUnpaid);
        JPanel panelUnpaid = createTablePanel(" Những khoản chưa đóng", tableUnpaid);

        // Bảng khoản đã đóng
        modelPaid = new DefaultTableModel(new Object[]{"ID", "Hộ", "Loại", "Số tiền", "Hạn nộp"}, 0);
        tablePaid = createTable(modelPaid);
        styleTable(tablePaid);
        JPanel panelPaid = createTablePanel(" Những khoản đã đóng", tablePaid);

        center.add(panelUnpaid);
        center.add(Box.createVerticalStrut(15));
        center.add(panelPaid);

        add(center, BorderLayout.CENTER);

        // Các nút thao tác
        JPanel btnPanel = new JPanel(new BorderLayout(10, 10));

        JPanel actionButtons = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        JButton btnAddUnpaid = createButton("Thêm phí chưa thanh toán");
        JButton btnMarkPaid = createButton("Đánh dấu đã thanh toán");
        JButton btnOverdue = createButton(" Phí quá hạn");
        actionButtons.add(btnOverdue);
        actionButtons.add(btnAddUnpaid);
        actionButtons.add(btnMarkPaid);

        JPanel backButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        JButton btnBack = createButton("Quay lại");
        backButtonPanel.add(btnBack);

        btnPanel.add(actionButtons, BorderLayout.WEST);
        btnPanel.add(backButtonPanel, BorderLayout.EAST);

        add(btnPanel, BorderLayout.SOUTH);

        // Sự kiện
        btnFilter.addActionListener(e -> applyFilter());
        btnAddUnpaid.addActionListener(e -> addUnpaidFee());
        btnMarkPaid.addActionListener(e -> markAsPaid());
        btnOverdue.addActionListener(e -> {
            parentFrame.setContentPane(new OverdueFeePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    private JPanel createTablePanel(String title, JTable table) {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                title,
                TitledBorder.LEFT,
                TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14),
                new Color(0x333333)
        ));
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        return panel;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        table.setFont(new Font("Arial", Font.PLAIN, 12));
    }

    private void applyFilter() {
        Integer id = null;
        String type = null;

        try {
            if (!tfFilterId.getText().trim().isEmpty())
                id = Integer.parseInt(tfFilterId.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID hộ khẩu phải là số nguyên.", "Lỗi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (!tfFilterType.getText().trim().isEmpty())
            type = tfFilterType.getText().trim();

        List<Fee> unpaid = controller.filterFees(id, type, "Chưa thanh toán");
        List<Fee> paid = controller.filterFees(id, type, "Đã thanh toán");
        fillTable(modelUnpaid, unpaid);
        fillTable(modelPaid, paid);
    }

    private void loadData() {
        fillTable(modelUnpaid, controller.getFeesByStatus("Chưa thanh toán"));
        fillTable(modelPaid, controller.getFeesByStatus("Đã thanh toán"));
    }

    private void fillTable(DefaultTableModel model, List<Fee> list) {
        model.setRowCount(0);
        for (Fee f : list) {
            model.addRow(new Object[]{f.getId(), f.getHouseholdId(), f.getType(), f.getAmount(), f.getDueDate()});
        }
    }

private void addUnpaidFee() {
    // Tạo dialog tùy chỉnh với kích thước lớn hơn
    JDialog dialog = new JDialog(parentFrame, "Thêm phí chưa thanh toán", true);
    dialog.setSize(500, 450); // Tăng kích thước
    dialog.setLocationRelativeTo(parentFrame);
    dialog.setResizable(false);
    
    // Panel chính
    JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
    mainPanel.setBackground(Color.WHITE);
    
    // Header
    JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
    headerPanel.setBackground(Color.WHITE);
    JLabel iconLabel = new JLabel("");
    iconLabel.setFont(new Font("Arial", Font.PLAIN, 24));
    JLabel titleLabel = new JLabel("Thêm khoản phí mới");
    titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
    titleLabel.setForeground(new Color(0x2C3E50));
    headerPanel.add(iconLabel);
    headerPanel.add(titleLabel);
    
    // Form panel sử dụng GridBagLayout
    JPanel formPanel = new JPanel(new GridBagLayout());
    formPanel.setBackground(Color.WHITE);
    formPanel.setBorder(BorderFactory.createTitledBorder(
        BorderFactory.createLineBorder(new Color(0xE0E0E0), 1),
        "Thông tin phí",
        TitledBorder.LEFT,
        TitledBorder.TOP,
        new Font("Arial", Font.BOLD, 12),
        new Color(0x5A6C7D)
    ));
    
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.insets = new Insets(12, 15, 12, 15); // Tăng khoảng cách
    gbc.anchor = GridBagConstraints.WEST;
    
    // Tạo các text field với kích thước cố định
    JTextField tfHouseholdId = createStyledTextField();
    tfHouseholdId.setPreferredSize(new java.awt.Dimension(200, 35));
    
    JTextField tfType = createStyledTextField();
    tfType.setPreferredSize(new java.awt.Dimension(200, 35));
    
    JTextField tfAmount = createStyledTextField();
    tfAmount.setPreferredSize(new java.awt.Dimension(200, 35));
    
    JTextField tfDueDate = createStyledTextField();
    tfDueDate.setPreferredSize(new java.awt.Dimension(200, 35));
    tfDueDate.setText("yyyy-MM-dd");
    tfDueDate.setForeground(Color.GRAY);
    
    // Placeholder cho trường ngày
    tfDueDate.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent evt) {
            if (tfDueDate.getText().equals("yyyy-MM-dd")) {
                tfDueDate.setText("");
                tfDueDate.setForeground(Color.BLACK);
            }
        }
        public void focusLost(FocusEvent evt) {
            if (tfDueDate.getText().isEmpty()) {
                tfDueDate.setText("yyyy-MM-dd");
                tfDueDate.setForeground(Color.GRAY);
            }
        }
    });
    
    // Tạo labels với kích thước cố định
    JLabel lblHouseholdId = createStyledLabel("ID hộ khẩu:");
    lblHouseholdId.setPreferredSize(new java.awt.Dimension(120, 25));
    
    JLabel lblType = createStyledLabel("Loại phí:");
    lblType.setPreferredSize(new java.awt.Dimension(120, 25));
    
    JLabel lblAmount = createStyledLabel("Số tiền (VNĐ):");
    lblAmount.setPreferredSize(new java.awt.Dimension(120, 25));
    
    JLabel lblDueDate = createStyledLabel("Hạn nộp:");
    lblDueDate.setPreferredSize(new java.awt.Dimension(120, 25));
    
    // Thêm các component vào form với layout rõ ràng
    gbc.gridx = 0; gbc.gridy = 0; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(lblHouseholdId, gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    formPanel.add(tfHouseholdId, gbc);
    
    gbc.gridx = 0; gbc.gridy = 1; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(lblType, gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    formPanel.add(tfType, gbc);
    
    gbc.gridx = 0; gbc.gridy = 2; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(lblAmount, gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    formPanel.add(tfAmount, gbc);
    
    gbc.gridx = 0; gbc.gridy = 3; gbc.fill = GridBagConstraints.NONE; gbc.weightx = 0;
    formPanel.add(lblDueDate, gbc);
    gbc.gridx = 1; gbc.fill = GridBagConstraints.HORIZONTAL; gbc.weightx = 1.0;
    formPanel.add(tfDueDate, gbc);
    
    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
    buttonPanel.setBackground(Color.WHITE);
    
    JButton btnCancel = createStyledButton("Hủy", new Color(0x95A5A6), Color.WHITE);
    JButton btnOK = createStyledButton("Thêm phí", new Color(0x3498DB), Color.WHITE);
    
    btnCancel.addActionListener(e -> dialog.dispose());
    
    btnOK.addActionListener(e -> {
        try {
            String householdIdText = tfHouseholdId.getText().trim();
            String typeText = tfType.getText().trim();
            String amountText = tfAmount.getText().trim();
            String dueDateText = tfDueDate.getText().trim();
            
            if (householdIdText.isEmpty() || typeText.isEmpty() || 
                amountText.isEmpty() || dueDateText.equals("yyyy-MM-dd") || dueDateText.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Vui lòng điền đầy đủ thông tin!", "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            int householdId = Integer.parseInt(householdIdText);
            String type = typeText;
            double amount = Double.parseDouble(amountText);
            LocalDate dueDate = LocalDate.parse(dueDateText);
            
            Fee newFee = new Fee();
            newFee.setHouseholdId(householdId);
            newFee.setType(type);
            newFee.setAmount(amount);
            newFee.setDueDate(dueDate);
            newFee.setStatus("Chưa thanh toán");
            
            if (controller.addFee(newFee)) {
                JOptionPane.showMessageDialog(dialog, "Thêm phí thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                dialog.dispose();
                loadData();
            } else {
                JOptionPane.showMessageDialog(dialog, "Thêm phí thất bại. Vui lòng thử lại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialog, "ID hộ khẩu và số tiền phải là số hợp lệ!", "Lỗi định dạng", JOptionPane.WARNING_MESSAGE);
        } catch (java.time.format.DateTimeParseException ex) {
            JOptionPane.showMessageDialog(dialog, "Định dạng ngày không hợp lệ! Vui lòng nhập theo format yyyy-MM-dd", "Lỗi ngày tháng", JOptionPane.WARNING_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(dialog, "Dữ liệu nhập không hợp lệ!", "Lỗi", JOptionPane.WARNING_MESSAGE);
        }
    });
    
    buttonPanel.add(btnCancel);
    buttonPanel.add(btnOK);
    
    // Thêm vào dialog
    mainPanel.add(headerPanel, BorderLayout.NORTH);
    mainPanel.add(formPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    
    dialog.add(mainPanel);
    dialog.setVisible(true);
}

// =================== HELPER METHODS ===================
private JTextField createStyledTextField() {
    JTextField field = new JTextField();
    field.setFont(new Font("Arial", Font.PLAIN, 13));
    field.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(new Color(0xD0D0D0), 1),
        BorderFactory.createEmptyBorder(8, 10, 8, 10)
    ));
    field.setBackground(Color.WHITE);
    
    // Focus effect
    field.addFocusListener(new FocusAdapter() {
        public void focusGained(FocusEvent evt) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0x3498DB), 2),
                BorderFactory.createEmptyBorder(7, 9, 7, 9)
            ));
        }
        public void focusLost(FocusEvent evt) {
            field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0xD0D0D0), 1),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
            ));
        }
    });
    
    return field;
}

private JLabel createStyledLabel(String text) {
    JLabel label = new JLabel(text);
    label.setFont(new Font("Arial", Font.BOLD, 13));
    label.setForeground(new Color(0x2C3E50));
    return label;
}

private JButton createStyledButton(String text, Color bgColor, Color textColor) {
    JButton button = new JButton(text);
    button.setFont(new Font("Arial", Font.BOLD, 12));
    button.setBackground(bgColor);
    button.setForeground(textColor);
    button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    button.setFocusPainted(false);
    button.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
    
    // Hover effect
    button.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent evt) {
            button.setBackground(bgColor.darker());
        }
        public void mouseExited(MouseEvent evt) {
            button.setBackground(bgColor);
        }
    });
    
    return button;
}

    private void markAsPaid() {
        int selected = tableUnpaid.getSelectedRow();
        if (selected != -1) {
            int id = (int) modelUnpaid.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận đánh dấu đã thanh toán?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.updateFeeStatus(id, "Đã thanh toán")) {
                    JOptionPane.showMessageDialog(this, "Đã cập nhật.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Thao tác thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }

    private void goBack() {
        if (parentFrame != null) {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
}