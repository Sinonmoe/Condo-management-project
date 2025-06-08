package ktpm.condo.view.household_view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.household_controller.HouseholdController;
import ktpm.condo.model.entity.Household;
import ktpm.condo.view.BasePanel;
import ktpm.condo.view.DashboardPanel;

/**
 * Giao diện Swing để quản lý danh sách hộ khẩu trong hệ thống.
 */
public class HouseholdPanel extends BasePanel {
    private final HouseholdController controller = new HouseholdController();
    private JTable table;
    private DefaultTableModel tableModel;
    private final JFrame parentFrame;

    /**
     * Khởi tạo HouseholdPanel kèm tham chiếu JFrame chính.
     *
     * @param parentFrame cửa sổ chính để điều hướng
     */
    public HouseholdPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new BorderLayout());

        tableModel = new DefaultTableModel(
            new Object[]{"ID", "Căn hộ", "Mã hộ", "Số thành viên"}, 0);
        table = createTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton btnAdd = createButton("Thêm hộ");
        JButton btnDelete = createButton("Xoá hộ");
        JButton btnViewCitizens = createButton("Xem nhân khẩu");
        JButton btnBack = createButton("Quay lại");

        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnViewCitizens);
        buttonPanel.add(btnBack);
        add(buttonPanel, BorderLayout.SOUTH);

        btnAdd.addActionListener(e -> addHousehold());
        btnDelete.addActionListener(e -> deleteSelected());
        btnViewCitizens.addActionListener(e -> viewCitizens());
        btnBack.addActionListener(e -> goBack());

        loadData();
    }

    /**
     * Quay lại giao diện chính.
     */
    private void goBack() {
        parentFrame.setTitle("Hệ thống quản lý Chung cư");
        parentFrame.setContentPane(new DashboardPanel(parentFrame));
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    /**
     * Tải dữ liệu hộ khẩu từ controller và hiển thị.
     */
    public void loadData() {
        tableModel.setRowCount(0);
        List<Household> households = controller.getAllHouseholds();
        for (Household h : households) {
            tableModel.addRow(new Object[]{
                h.getId(),
                h.getApartmentNumber(),
                h.getHouseholdCode(),
                h.getNumberOfMembers()
            });
        }
    }

    /**
     * Hiển thị dialog thêm hộ khẩu mới với giao diện đẹp.
     */
    private void addHousehold() {
        AddHouseholdDialog dialog = new AddHouseholdDialog(parentFrame);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            String apartment = dialog.getApartmentText();
            String code = dialog.getHouseholdCodeText();
            
            if (controller.addHousehold(apartment, code)) {
                // Hiển thị thông báo thành công với style đẹp
                String successMessage = String.format(
                    "✅ Đã thêm hộ khẩu thành công!\n\n" +
                    "📍 Căn hộ: %s\n" +
                    "🏷️ Mã hộ: %s\n\n" +
                    "Hộ khẩu đã được thêm vào hệ thống.",
                    apartment, code
                );
                
                JOptionPane.showMessageDialog(this, 
                    successMessage, 
                    "Thành công", 
                    JOptionPane.INFORMATION_MESSAGE
                );
                loadData();
            } else {
                // Hiển thị thông báo lỗi với style đẹp
                String errorMessage = 
                    "❌ Thêm hộ khẩu thất bại!\n\n" +
                    "Có thể do:\n" +
                    "• Căn hộ đã tồn tại trong hệ thống\n" +
                    "• Mã hộ khẩu đã được sử dụng\n" +
                    "• Lỗi kết nối cơ sở dữ liệu\n\n" +
                    "Vui lòng kiểm tra lại thông tin và thử lại.";
                
                JOptionPane.showMessageDialog(this, 
                    errorMessage, 
                    "Lỗi", 
                    JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    /**
     * Xoá hộ khẩu được chọn nếu xác nhận từ người dùng.
     */
    private void deleteSelected() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int id = (int) tableModel.getValueAt(selectedRow, 0);
            String apartment = (String) tableModel.getValueAt(selectedRow, 1);
            String householdCode = (String) tableModel.getValueAt(selectedRow, 2);
            int memberCount = (int) tableModel.getValueAt(selectedRow, 3);
            
            // Tạo thông báo xác nhận với thông tin chi tiết
            String confirmMessage = String.format(
                "⚠️ Bạn có chắc chắn muốn xoá hộ khẩu này?\n\n" +
                "📍 Căn hộ: %s\n" +
                "🏷️ Mã hộ: %s\n" +
                "👥 Số thành viên: %d\n\n" +
                "⚠️ Lưu ý: Việc xoá sẽ không thể hoàn tác!",
                apartment, householdCode, memberCount
            );
            
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                confirmMessage, 
                "Xác nhận xoá hộ khẩu", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                if (controller.deleteHousehold(id)) {
                    String successMessage = String.format(
                        "✅ Đã xoá hộ khẩu thành công!\n\n" +
                        "Căn hộ %s (Mã: %s) đã được xoá khỏi hệ thống.",
                        apartment, householdCode
                    );
                    
                    JOptionPane.showMessageDialog(this, 
                        successMessage, 
                        "Xoá thành công", 
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    loadData();
                } else {
                    String errorMessage = 
                        "❌ Không thể xoá hộ khẩu!\n\n" +
                        "Có thể do:\n" +
                        "• Hộ khẩu đang có ràng buộc dữ liệu\n" +
                        "• Lỗi kết nối cơ sở dữ liệu\n" +
                        "• Hộ khẩu không tồn tại\n\n" +
                        "Vui lòng kiểm tra lại và thử lại.";
                    
                    JOptionPane.showMessageDialog(this, 
                        errorMessage, 
                        "Lỗi", 
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Vui lòng chọn một hộ khẩu để xoá!\n\n" +
                "Nhấp vào một dòng trong bảng để chọn hộ khẩu cần xoá.",
                "Chưa chọn hộ khẩu", 
                JOptionPane.WARNING_MESSAGE
            );
        }
    }

    /**
     * Hiển thị danh sách nhân khẩu thuộc hộ khẩu được chọn.
     */
    private void viewCitizens() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            int householdId = (int) tableModel.getValueAt(selectedRow, 0);
            String apartment = (String) tableModel.getValueAt(selectedRow, 1);
            String householdCode = (String) tableModel.getValueAt(selectedRow, 2);
            
            JDialog dialog = new JDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this), 
                String.format("Nhân khẩu - Căn hộ %s (%s)", apartment, householdCode), 
                true
            );
            dialog.setSize(600, 400);
            dialog.setLocationRelativeTo(null);
            dialog.setContentPane(new CitizenPanel(householdId, this));
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                "⚠️ Vui lòng chọn một hộ khẩu để xem nhân khẩu!\n\n" +
                "Nhấp vào một dòng trong bảng để chọn hộ khẩu cần xem.",
                "Chưa chọn hộ khẩu", 
                JOptionPane.WARNING_MESSAGE
            );
        }
    }
}