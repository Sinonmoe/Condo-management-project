package ktpm.condo.model.service;

import java.util.Map;

import ktpm.condo.model.dao.ReportDAO;

/**
 * Service xử lý nghiệp vụ thống kê báo cáo.
 */
public class ReportService {
    private final ReportDAO dao = new ReportDAO();

    public Map<String, Integer> getPopulationByBuildingFloor() {
        return dao.getPopulationByBuildingFloor();
    }

    public Map<String, Integer> getPopulationByAgeGroup() {
        return dao.getPopulationByAgeGroup();
    }

    public Map<String, Integer> getPopulationByGender() {
        return dao.getPopulationByGender();
    }

    public Map<String, Integer> getPopulationByJob() {
        return dao.getPopulationByJob();
    }

    public Map<String, Double> getTotalFeeByMonth() {
        return dao.getTotalFeeByMonth();
    }

    public Map<String, Integer> getFacilityUsageByType() {
        return dao.getFacilityUsageByType();
    }
}
