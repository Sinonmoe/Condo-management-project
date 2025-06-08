package ktpm.condo.view;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

/**
 * Lớp cơ sở cho các panel trong giao diện, cung cấp các phương thức tiện ích tạo
 * component chuẩn và đồng bộ phong cách.
 */
public class BasePanel extends JPanel {

    // ✅ Style constants - Updated to match the design
    protected final Color PRIMARY_COLOR = new Color(93, 109, 156);      // Purple-blue color from button
    protected final Color BACKGROUND_COLOR = new Color(248, 249, 250);  // Light gray background
    protected final Color TITLE_COLOR = new Color(52, 58, 64);          // Dark gray for titles
    protected final Color INPUT_BORDER_COLOR = new Color(206, 212, 218); // Light border for inputs
    protected final Color TEXT_COLOR = new Color(73, 80, 87);           // Medium gray for text
    
    protected final Font FONT_REGULAR = new Font("Segoe UI", Font.PLAIN, 14);
    protected final Font FONT_BOLD = new Font("Segoe UI", Font.BOLD, 16);
    protected final Font FONT_TITLE = new Font("Segoe UI", Font.BOLD, 18);

    public BasePanel() {
        setBackground(Color.WHITE);
    }

    /**
     * Tạo JLabel với font thường và màu chữ chuẩn.
     */
    protected JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_REGULAR);
        label.setForeground(TEXT_COLOR);
        return label;
    }

    /**
     * Tạo JLabel tiêu đề nổi bật.
     */
    protected JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(FONT_TITLE);
        label.setForeground(TITLE_COLOR);
        label.setBorder(new EmptyBorder(10, 0, 20, 0));
        return label;
    }

    /**
     * Tạo JButton với màu nền, font đậm và style như trong thiết kế.
     */
    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(FONT_BOLD);
        button.setBackground(PRIMARY_COLOR);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        
        // Tự động tính toán kích thước button dựa trên text
        FontMetrics fm = button.getFontMetrics(FONT_BOLD);
        int textWidth = fm.stringWidth(text);
        int minWidth = Math.max(120, textWidth + 50); // Tối thiểu 120px, thêm 50px padding
        button.setPreferredSize(new Dimension(minWidth, 45));
        button.setMinimumSize(new Dimension(minWidth, 45));
        
        // Add rounded corners effect
        button.setOpaque(true);
        button.setBorderPainted(false);
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(PRIMARY_COLOR);
            }
        });
        
        return button;
    }

    /**
     * Tạo JTextField với font chuẩn, border và padding như trong thiết kế.
     */
    protected JTextField createTextField(int columns) {
        JTextField textField = new JTextField(columns);
        textField.setFont(FONT_REGULAR);
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));
        textField.setPreferredSize(new Dimension(textField.getPreferredSize().width, 40));
        return textField;
    }

    /**
     * Tạo JComboBox với style giống như trong thiết kế.
     */
    protected JComboBox<String> createComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setFont(FONT_REGULAR);
        comboBox.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INPUT_BORDER_COLOR, 1),
            BorderFactory.createEmptyBorder(2, 8, 2, 8)
        ));
        comboBox.setPreferredSize(new Dimension(comboBox.getPreferredSize().width, 40));
        comboBox.setBackground(Color.WHITE);
        return comboBox;
    }

    /**
     * Tạo JTable với font chuẩn cho bảng và header.
     */
    protected JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        table.setFont(FONT_REGULAR);
        table.setRowHeight(35);
        table.getTableHeader().setFont(FONT_BOLD);
        table.getTableHeader().setBackground(BACKGROUND_COLOR);
        table.getTableHeader().setForeground(TITLE_COLOR);
        table.setGridColor(INPUT_BORDER_COLOR);
        table.setSelectionBackground(new Color(PRIMARY_COLOR.getRed(), PRIMARY_COLOR.getGreen(), PRIMARY_COLOR.getBlue(), 50));
        return table;
    }

    /**
     * Tạo panel với form layout như trong thiết kế.
     */
    protected JPanel createFormPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(new EmptyBorder(20, 30, 20, 30));
        return panel;
    }

    /**
     * Thêm component vào form với GridBagConstraints.
     */
    protected void addFormComponent(JPanel parent, Component component, int gridx, int gridy, int gridwidth, int anchor, Insets insets) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.anchor = anchor;
        gbc.insets = insets;
        if (component instanceof JTextField || component instanceof JComboBox) {
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
        }
        parent.add(component, gbc);
    }

    /**
     * Tạo separator line như trong thiết kế.
     */
    protected JSeparator createSeparator() {
        JSeparator separator = new JSeparator();
        separator.setForeground(INPUT_BORDER_COLOR);
        return separator;
    }
}