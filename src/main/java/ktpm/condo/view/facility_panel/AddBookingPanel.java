package ktpm.condo.view.facility_panel;

import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import ktpm.condo.controller.FacilityController;
import ktpm.condo.model.entity.Facility;
import ktpm.condo.model.entity.FacilityBooking;
import ktpm.condo.view.BasePanel;

public class AddBookingPanel extends BasePanel {
    private final JComboBox<Facility> cbFacility;
    private final JTextField tfHouseholdId = createTextField(10);
    private final JTextField tfDate = createTextField(15);

    private final FacilityController controller;

    public AddBookingPanel(FacilityController controller) {
        this.controller = controller;
        setLayout(new GridLayout(0, 1));

        List<Facility> facilities = controller.getAllFacilities();
        cbFacility = new JComboBox<>(facilities.toArray(new Facility[0]));

        add(createLabel("Tên tiện ích:"));
        add(cbFacility);
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
            Facility selectedFacility = (Facility) cbFacility.getSelectedItem();
            if (selectedFacility == null) {
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một tiện ích.", "Lỗi", JOptionPane.WARNING_MESSAGE);
                return false;
            }

            FacilityBooking fb = new FacilityBooking();
            fb.setFacilityId(selectedFacility.getId());  // Dùng facilityId
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
