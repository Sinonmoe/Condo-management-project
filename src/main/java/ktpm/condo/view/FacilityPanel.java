package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.FacilityController;
import ktpm.condo.model.entity.FacilityBooking;

/**
 * Giao diện Swing để quản lý lịch sử sử dụng tiện ích.
 */
public class FacilityPanel extends JPanel {
    private final FacilityController controller = new FacilityController();
    private JTable table;
    private DefaultTableModel tableModel;
    private JFrame parentFrame;

    /**
     * Constructor có tham số JFrame parent (để hỗ trợ chuyển panel khi cần).
     *
     * @param parentFrame JFrame chính chứa panel này
     */
    public FacilityPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initialize();
    }

    /**
     * Constructor không tham số (nếu không cần parentFrame).
     */
    public FacilityPanel() {
        initialize();
    }

    /**
     * Khởi tạo UI và sự kiện.
     */
    private void initialize() {
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(new Object[]{"ID", "Tên tiện ích", "ID hộ khẩu", "Ngày sử dụng"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JButton btnAdd = new JButton("Thêm tiện ích");
        JButton btnDelete = new JButton("Xoá tiện ích");
        JButton btnRefresh = new JButton("Làm mới");

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnRefresh);
        add(btnPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addBooking());
        btnDelete.addActionListener(e -> deleteBooking());
        btnRefresh.addActionListener(e -> loadData());

        loadData();
    }

    /**
     * Tải lại danh sách tiện ích từ controller và hiển thị lên bảng.
     */
    private void loadData() {
        tableModel.setRowCount(0);
        List<FacilityBooking> list = controller.getAllBookings();
        for (FacilityBooking fb : list) {
            tableModel.addRow(new Object[]{
                    fb.getId(),
                    fb.getFacilityName(),
                    fb.getHouseholdId(),
                    fb.getUsageDate()
            });
        }
    }

    /**
     * Hiển thị hộp thoại nhập liệu thêm lượt đặt tiện ích mới và xử lý lưu.
     */
    private void addBooking() {
        JTextField tfName = new JTextField();
        JTextField tfHouseholdId = new JTextField();
        JTextField tfDate = new JTextField("yyyy-MM-dd");

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Tên tiện ích:"));
        panel.add(tfName);
        panel.add(new JLabel("ID hộ khẩu:"));
        panel.add(tfHouseholdId);
        panel.add(new JLabel("Ngày sử dụng (yyyy-MM-dd):"));
        panel.add(tfDate);

        int result = JOptionPane.showConfirmDialog(this, panel, "Thêm tiện ích", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                FacilityBooking fb = new FacilityBooking();
                fb.setFacilityName(tfName.getText());
                fb.setHouseholdId(Integer.parseInt(tfHouseholdId.getText()));
                fb.setUsageDate(LocalDate.parse(tfDate.getText()));

                if (controller.addBooking(fb)) {
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
     * Xoá lượt đặt tiện ích được chọn nếu người dùng xác nhận.
     */
    private void deleteBooking() {
        int selected = table.getSelectedRow();
        if (selected != -1) {
            int id = (int) tableModel.getValueAt(selected, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xoá tiện ích này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteBooking(id)) {
                    JOptionPane.showMessageDialog(this, "Đã xoá.");
                    loadData();
                } else {
                    JOptionPane.showMessageDialog(this, "Không thể xoá.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiện ích để xoá.");
        }
    }
}
