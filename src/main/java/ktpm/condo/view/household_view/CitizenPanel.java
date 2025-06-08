package ktpm.condo.view.household_view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.Date;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
import javax.swing.SwingConstants;
import ktpm.condo.controller.household_controller.CitizenController;
import ktpm.condo.model.entity.Citizen;
import ktpm.condo.view.BasePanel;

/**
 * Giao diện Swing để quản lý nhân khẩu thuộc một hộ khẩu cụ thể.
 */
public class CitizenPanel extends BasePanel {
    private final CitizenController service = new CitizenController();
    private final int householdId;
    private final HouseholdPanel parentHouseholdPanel;

    private final JTable table;
    private final DefaultTableModel tableModel;

    /**
     * Khởi tạo CitizenPanel với ID hộ khẩu tương ứng.
     *
     * @param householdId ID của hộ khẩu cần quản lý nhân khẩu
     * @param parentHouseholdPanel tham chiếu để gọi loadData() làm mới hộ khẩu
     */
    public CitizenPanel(int householdId, HouseholdPanel parentHouseholdPanel) {
        this.householdId = householdId;
        this.parentHouseholdPanel = parentHouseholdPanel;

        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Họ tên", "Ngày sinh", "Giới tính", "Nghề nghiệp", "Quan hệ"}, 0);
        table = createTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = createButton("Thêm nhân khẩu");
        JButton btnDelete = createButton("Xoá nhân khẩu");
        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addCitizen());
        btnDelete.addActionListener(e -> deleteCitizen());

        loadData();
    }

    /**
     * Tải lại danh sách nhân khẩu từ cơ sở dữ liệu và hiển thị lên bảng.
     */
    private void loadData() {
        tableModel.setRowCount(0);
        List<Citizen> list = service.getByHousehold(householdId);
        for (Citizen c : list) {
            tableModel.addRow(new Object[]{
                c.getId(),
                c.getName(),
                c.getDateOfBirth(),
                c.getGender(),
                c.getJob(),
                c.getRelationshipToHead()
            });
        }
    }

    /**
     * Tạo label với font và màu sắc đẹp
     */
    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.BOLD, 12));
        label.setForeground(new Color(60, 60, 60));
        label.setHorizontalAlignment(SwingConstants.LEFT);
        return label;
    }

    /**
     * Tạo text field với border và padding đẹp
     */
    private JTextField createStyledTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 30));
        textField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));
        textField.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return textField;
    }

    /**
     * Tạo combo box với style đẹp
     */
    private JComboBox<String> createStyledComboBox(String[] items) {
        JComboBox<String> comboBox = new JComboBox<>(items);
        comboBox.setPreferredSize(new Dimension(200, 30));
        comboBox.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboBox.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        return comboBox;
    }

    /**
     * Tạo date spinner với style đẹp
     */
    private JSpinner createStyledDateSpinner() {
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy"));
        dateSpinner.setPreferredSize(new Dimension(200, 30));
        dateSpinner.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        dateSpinner.setFont(new Font("SansSerif", Font.PLAIN, 12));
        return dateSpinner;
    }

    /**
     * Hiển thị hộp thoại thêm nhân khẩu mới với giao diện được cải thiện.
     */
    private void addCitizen() {
        // Tạo các components với style đẹp
        JTextField tfName = createStyledTextField();
        JSpinner dateSpinner = createStyledDateSpinner();
        String[] genders = {"Nam", "Nữ"};
        JComboBox<String> cbGender = createStyledComboBox(genders);
        JTextField tfJob = createStyledTextField();
        JTextField tfRelation = createStyledTextField();

        // Tạo panel chính với GridBagLayout để có control tốt hơn
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        mainPanel.setBackground(Color.WHITE);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 0, 8, 15); // top, left, bottom, right

        // Tiêu đề
        JLabel titleLabel = new JLabel("Thông tin nhân khẩu mới");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(new Color(51, 122, 183));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.insets = new Insets(8, 0, 8, 15);

        // Họ tên
        gbc.gridx = 0;
        gbc.gridy = 1;
        mainPanel.add(createStyledLabel("Họ và tên:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(tfName, gbc);

        // Ngày sinh
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(createStyledLabel("Ngày sinh:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(dateSpinner, gbc);

        // Giới tính
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(createStyledLabel("Giới tính:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(cbGender, gbc);

        // Nghề nghiệp
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(createStyledLabel("Nghề nghiệp:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(tfJob, gbc);

        // Quan hệ với chủ hộ
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.NONE;
        mainPanel.add(createStyledLabel("Quan hệ với chủ hộ:"), gbc);
        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        mainPanel.add(tfRelation, gbc);

        // Tạo panel wrapper để có thể control kích thước
        JPanel wrapperPanel = new JPanel(new BorderLayout());
        wrapperPanel.add(mainPanel, BorderLayout.CENTER);
        wrapperPanel.setPreferredSize(new Dimension(450, 320));

        // Tùy chỉnh các button của dialog
        String[] options = {"Thêm", "Hủy"};
        
        int result = JOptionPane.showOptionDialog(
            this,
            wrapperPanel,
            "Thêm nhân khẩu mới",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            options,
            options[0]
        );

        if (result == JOptionPane.YES_OPTION) {
            try {
                String name = tfName.getText().trim();
                String dob = new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
                String gender = (String) cbGender.getSelectedItem();
                String job = tfJob.getText().trim();
                String relation = tfRelation.getText().trim();

                // Validation
                if (name.isEmpty()) {
                    showWarningMessage("Vui lòng nhập họ tên!");
                    return;
                }
                if (job.isEmpty()) {
                    showWarningMessage("Vui lòng nhập nghề nghiệp!");
                    return;
                }
                if (relation.isEmpty()) {
                    showWarningMessage("Vui lòng nhập quan hệ với chủ hộ!");
                    return;
                }

                if (service.addCitizen(householdId, name, dob, gender, job, relation)) {
                    showSuccessMessage("Thêm nhân khẩu thành công!");
                    loadData();
                    if (parentHouseholdPanel != null) {
                        parentHouseholdPanel.loadData(); // làm mới số thành viên
                    }
                } else {
                    showErrorMessage("Không thể thêm nhân khẩu. Vui lòng thử lại!");
                }
            } catch (Exception ex) {
                showErrorMessage("Dữ liệu không hợp lệ. Vui lòng kiểm tra lại thông tin!");
            }
        }
    }

    /**
     * Hiển thị thông báo thành công
     */
    private void showSuccessMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Thành công", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Hiển thị thông báo lỗi
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Hiển thị thông báo cảnh báo
     */
    private void showWarningMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Cảnh báo", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Xoá nhân khẩu được chọn trong bảng nếu xác nhận.
     */
    private void deleteCitizen() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            int id = (int) tableModel.getValueAt(selected, 0);
            String name = (String) tableModel.getValueAt(selected, 1);
            
            String message = String.format("Bạn có chắc chắn muốn xóa nhân khẩu:\n\"%s\" không?", name);
            String[] options = {"Xóa", "Hủy"};
            
            int confirm = JOptionPane.showOptionDialog(
                this,
                message,
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[1]
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteCitizen(id)) {
                    showSuccessMessage("Xóa nhân khẩu thành công!");
                    loadData();
                    if (parentHouseholdPanel != null) {
                        parentHouseholdPanel.loadData(); // làm mới số thành viên trong hộ khẩu
                    }
                } else {
                    showErrorMessage("Không thể xóa nhân khẩu. Vui lòng thử lại!");
                }
            }
        } else {
            showWarningMessage("Vui lòng chọn một nhân khẩu để xóa!");
        }
    }
}