package ktpm.condo.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.text.DecimalFormat;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import ktpm.condo.controller.household_controller.ReportController;

/**
 * Giao diện báo cáo thống kê với nhiều loại báo cáo.
 */
public class ReportPanel extends BasePanel {
    private final ReportController controller = new ReportController();
    private final JFrame parentFrame;

    private JTable table;
    private DefaultTableModel model;

    private final DecimalFormat moneyFormat = new DecimalFormat("#,##0.00");

    public ReportPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        initUI();
        showPopulationReport(); // Mặc định báo cáo dân cư
    }

    private void initUI() {
        setLayout(new BorderLayout());

        // Panel chứa nút báo cáo
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnPopulation = createButton("Báo cáo dân cư");
        btnPopulation.setToolTipText("Xem thống kê số cư dân theo tòa và tầng");

        JButton btnFeeByMonth = createButton("Báo cáo thu phí theo tháng");
        btnFeeByMonth.setToolTipText("Xem tổng thu phí từng tháng");

        JButton btnFeeByType = createButton("Báo cáo thu phí theo dịch vụ");
        btnFeeByType.setToolTipText("Xem tổng thu phí theo từng loại dịch vụ");

        JButton btnFacility = createButton("Báo cáo tiện ích");
        btnFacility.setToolTipText("Xem số lượt sử dụng tiện ích");

        btnPanel.add(btnPopulation);
        btnPanel.add(btnFeeByMonth);
        btnPanel.add(btnFeeByType);
        btnPanel.add(btnFacility);
        add(btnPanel, BorderLayout.NORTH);

        // Tạo bảng với model rỗng
        model = new DefaultTableModel();
        table = createTable(model);

        // Định dạng header font và căn giữa
        table.getTableHeader().setFont(this.getFont());
        ((DefaultTableCellRenderer)table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.CENTER);

        add(new JScrollPane(table), BorderLayout.CENTER);

        // Nút quay lại dashboard
        JButton btnBack = createButton("Quay lại ");
        btnBack.addActionListener(e -> {
            parentFrame.setTitle("Hệ thống quản lý Chung cư");
            parentFrame.setContentPane(new DashboardPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.add(btnBack);
        add(bottomPanel, BorderLayout.SOUTH);

        // Sự kiện chuyển đổi báo cáo
        btnPopulation.addActionListener(e -> showPopulationReport());
        btnFeeByMonth.addActionListener(e -> showFeeByMonthReport());
        btnFeeByType.addActionListener(e -> showFeeByTypeReport());
        btnFacility.addActionListener(e -> showFacilityReport());
    }

    // Phương thức chung để cập nhật dữ liệu vào bảng
    private <K, V> void updateTable(Map<K, V> data, String[] columnNames, boolean rightAlignSecondColumn, boolean formatSecondColumnAsMoney) {
        model.setColumnIdentifiers(columnNames);
        model.setRowCount(0);

        data.forEach((k, v) -> {
            Object secondValue = v;
            if (formatSecondColumnAsMoney && v instanceof Number) {
                secondValue = moneyFormat.format(((Number) v).doubleValue());
            }
            model.addRow(new Object[]{k, secondValue});
        });

        // Căn chỉnh cột
        DefaultTableCellRenderer leftRenderer = new DefaultTableCellRenderer();
        leftRenderer.setHorizontalAlignment(SwingConstants.LEFT);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);

        table.getColumnModel().getColumn(0).setCellRenderer(leftRenderer);
        if (rightAlignSecondColumn) {
            table.getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
        } else {
            table.getColumnModel().getColumn(1).setCellRenderer(leftRenderer);
        }
    }

    private void showPopulationReport() {
        Map<String, Integer> data = controller.getPopulationByBuildingFloor();
        updateTable(data, new String[]{"Tòa - Tầng", "Số cư dân"}, true, false);
    }

    private void showFeeByMonthReport() {
        Map<String, Double> data = controller.getTotalFeeByMonth();
        updateTable(data, new String[]{"Tháng", "Tổng thu phí"}, true, true);
    }

    private void showFeeByTypeReport() {
        Map<String, Double> data = controller.getTotalFeeByServiceType();
        updateTable(data, new String[]{"Loại dịch vụ", "Tổng thu"}, true, true);
    }

    private void showFacilityReport() {
        Map<String, Integer> data = controller.getFacilityUsageByType();
        updateTable(data, new String[]{"Tên tiện ích", "Số lượt sử dụng"}, true, false);
    }
}
