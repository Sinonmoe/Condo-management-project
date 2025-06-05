package ktpm.condo.view.household_view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.model.entity.Citizen;
import ktpm.condo.model.service.household_service.CitizenService;
import ktpm.condo.view.BasePanel;

/**
 * Giao diện Swing để quản lý nhân khẩu thuộc một hộ khẩu cụ thể.
 */
public class CitizenPanel extends BasePanel {
    private final CitizenService service = new CitizenService();
    private final int householdId;
    private JTable table;
    private DefaultTableModel tableModel;

    /**
     * Khởi tạo CitizenPanel với ID hộ khẩu tương ứng.
     *
     * @param householdId ID của hộ khẩu cần quản lý nhân khẩu
     */
    public CitizenPanel(int householdId) {
        this.householdId = householdId;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Họ tên", "Ngày sinh", "Giới tính", "Nghề nghiệp", "Quan hệ"}, 0);
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
        JTextField tfName = createTextField(20);
        JTextField tfDOB = createTextField(20);
        tfDOB.setText("yyyy-MM-dd");
        JTextField tfGender = createTextField(20);
        JTextField tfJob = createTextField(20);
        JTextField tfRelation = createTextField(20);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(createLabel("Họ tên:"));
        panel.add(tfName);
        panel.add(createLabel("Ngày sinh (yyyy-MM-dd):"));
        panel.add(tfDOB);
        panel.add(createLabel("Giới tính:"));
        panel.add(tfGender);
        panel.add(createLabel("Nghề nghiệp:"));
        panel.add(tfJob);
        panel.add(createLabel("Quan hệ với chủ hộ:"));
        panel.add(tfRelation);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm nhân khẩu", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                Citizen c = new Citizen();
                c.setHouseholdId(householdId);
                c.setName(tfName.getText().trim());
                c.setDateOfBirth(LocalDate.parse(tfDOB.getText().trim()));
                c.setGender(tfGender.getText().trim());
                c.setJob(tfJob.getText().trim());
                c.setRelationshipToHead(tfRelation.getText().trim());

                if (service.add(c)) {
                    JOptionPane.showMessageDialog(this, "Đã thêm.");
                    loadData();
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
                if (service.delete(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân khẩu để xoá.");
        }
    }
}
