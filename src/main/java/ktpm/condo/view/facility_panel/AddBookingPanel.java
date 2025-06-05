package ktpm.condo.view.facility_panel;

import java.awt.GridLayout;
import java.time.LocalDate;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ktpm.condo.controller.FacilityController;
import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.view.BasePanel;

public class AddBookingPanel extends BasePanel {
    private final JTextField tfName = createTextField(20);
    private final JTextField tfHouseholdId = createTextField(10);
    private final JTextField tfDate = createTextField(15);

    private final FacilityController controller;

    public AddBookingPanel(FacilityController controller) {
        this.controller = controller;
        setLayout(new GridLayout(0, 1));

        add(createLabel("Tên tiện ích:"));
        add(tfName);
        add(createLabel("ID hộ khẩu:"));
        add(tfHouseholdId);
        add(createLabel("Ngày sử dụng (yyyy-MM-dd):"));
        add(tfDate);
    }

    /**
     * Lưu dữ liệu lượt đặt tiện ích.
     * 
     * @return true nếu thành công, false nếu lỗi dữ liệu hoặc lưu thất bại
     */
    public boolean saveBooking() {
        try {
            FacilityBooking fb = new FacilityBooking();
            fb.setFacilityName(tfName.getText());
            fb.setHouseholdId(Integer.parseInt(tfHouseholdId.getText()));
            fb.setUsageDate(LocalDate.parse(tfDate.getText()));

            boolean success = controller.addBooking(fb);
            if (!success) {
                JOptionPane.showMessageDialog(this, "Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
            return success;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Dữ liệu không hợp lệ.", "Lỗi nhập liệu", JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
}
