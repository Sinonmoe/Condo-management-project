package ktpm.condo.view.household_view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.SpinnerDateModel;
import javax.swing.JSpinner;
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
     * Hiển thị hộp thoại thêm nhân khẩu mới và cập nhật dữ liệu nếu thành công.
     */
    private void addCitizen() {
        JTextField tfName = createTextField(15);

        // Spinner chọn ngày sinh
        SpinnerDateModel dateModel = new SpinnerDateModel();
        JSpinner dateSpinner = new JSpinner(dateModel);
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));

        // ComboBox chọn giới tính
        String[] genders = {"Nam", "Nữ"};
        JComboBox<String> cbGender = new JComboBox<>(genders);

        JTextField tfJob = createTextField(15);
        JTextField tfRelation = createTextField(15);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(createLabel("Họ tên:"));
        panel.add(tfName);
        panel.add(createLabel("Ngày sinh:"));
        panel.add(dateSpinner);
        panel.add(createLabel("Giới tính:"));
        panel.add(cbGender);
        panel.add(createLabel("Nghề nghiệp:"));
        panel.add(tfJob);
        panel.add(createLabel("Quan hệ với chủ hộ:"));
        panel.add(tfRelation);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân khẩu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                String name = tfName.getText();
                String dob = new java.text.SimpleDateFormat("yyyy-MM-dd").format(dateSpinner.getValue());
                String gender = (String) cbGender.getSelectedItem();
                String job = tfJob.getText();
                String relation = tfRelation.getText();

                if (service.addCitizen(householdId, name, dob, gender, job, relation)) {
                    JOptionPane.showMessageDialog(this, "Đã thêm.");
                    loadData();
                    if (parentHouseholdPanel != null) {
                        parentHouseholdPanel.loadData(); // làm mới số thành viên
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            }
        }
    }


    /**
     * Xoá nhân khẩu được chọn trong bảng nếu xác nhận.
     */
    private void deleteCitizen() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            int id = (int) tableModel.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá nhân khẩu này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteCitizen(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                    if (parentHouseholdPanel != null) {
                        parentHouseholdPanel.loadData(); // làm mới số thành viên trong hộ khẩu
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân khẩu để xoá.");
        }
    }
}
