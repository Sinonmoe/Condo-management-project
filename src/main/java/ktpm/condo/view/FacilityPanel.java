package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.model.service.FacilityBookingService;

public class FacilityPanel extends JPanel {
    private final FacilityBookingService service = new FacilityBookingService();
    private final DefaultTableModel model;
    private final JTable table;

    public FacilityPanel(JFrame parentFrame) {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(new Object[]{"ID", "Tên tiện ích", "ID hộ khẩu", "Thời gian sử dụng"}, 0);
        table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel filterPanel = new JPanel();
        String[] options = {"Tên tiện ích", "ID hộ", "Ngày (yyyy-mm-dd)", "Tháng (1-12)"};
        JComboBox<String> cbFilter = new JComboBox<>(options);
        JTextField tfKeyword = new JTextField(10);
        JButton btnFilter = new JButton("Lọc");

        filterPanel.add(new JLabel("Lọc theo:"));
        filterPanel.add(cbFilter);
        filterPanel.add(tfKeyword);
        filterPanel.add(btnFilter);
        add(filterPanel, BorderLayout.NORTH);

        btnFilter.addActionListener(e -> {
            String[] types = {"name", "household", "date", "month"};
            String type = types[cbFilter.getSelectedIndex()];
            String keyword = tfKeyword.getText().trim();
            List<FacilityBooking> result = service.filter(type, keyword);
            fillTable(result);
        });

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));

        JButton btnAdd = new JButton("Thêm tiện ích");
        btnAdd.addActionListener(e -> {
            JTextField tfName = new JTextField(10);
            JTextField tfHousehold = new JTextField(5);
            JTextField tfTime = new JTextField(16); // yyyy-MM-dd HH:mm

            JPanel inputPanel = new JPanel(new GridLayout(3, 2));
            inputPanel.add(new JLabel("Tên tiện ích:"));
            inputPanel.add(tfName);
            inputPanel.add(new JLabel("ID hộ khẩu:"));
            inputPanel.add(tfHousehold);
            inputPanel.add(new JLabel("Thời gian (yyyy-MM-dd HH:mm):"));
            inputPanel.add(tfTime);

            int result = JOptionPane.showConfirmDialog(this, inputPanel, "Thêm lượt đặt tiện ích", JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                try {
                    String name = tfName.getText().trim();
                    int householdId = Integer.parseInt(tfHousehold.getText().trim());
                    LocalDateTime time = LocalDateTime.parse(tfTime.getText().trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                    FacilityBooking booking = new FacilityBooking(name, householdId, time);
                    if (service.addBooking(booking)) {
                        JOptionPane.showMessageDialog(this, "Thêm thành công.");
                        fillTable(service.getAll());
                    } else {
                        JOptionPane.showMessageDialog(this, "Thêm thất bại.");
                    }
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.");
                }
            }
        });

        JButton btnDelete = new JButton("Xóa tiện ích");
        btnDelete.addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một dòng để xóa.");
                return;
            }

            int bookingId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Xác nhận xóa lượt đặt tiện ích này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.deleteBooking(bookingId)) {
                    model.removeRow(selectedRow);
                    JOptionPane.showMessageDialog(this, "Đã xóa thành công.");
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thất bại.");
                }
            }
        });

        JButton btnBack = new JButton("Quay lại Dashboard");
        btnBack.addActionListener(e2 -> {
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        btnPanel.add(btnAdd);
        btnPanel.add(btnDelete);
        btnPanel.add(btnBack);
        add(btnPanel, BorderLayout.SOUTH);

        fillTable(service.getAll());
    }

    private void fillTable(List<FacilityBooking> list) {
        model.setRowCount(0);
        for (FacilityBooking f : list) {
            model.addRow(new Object[]{
                    f.getId(),
                    f.getFacilityName(),
                    f.getHouseholdId(),
                    f.getBookingTime().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))
            });
        }
    }
}
