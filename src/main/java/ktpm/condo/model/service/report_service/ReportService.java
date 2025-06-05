package ktpm.condo.model.service.report_service;

import java.util.Map;

import ktpm.condo.model.dao.ReportDAO;

/**
 * Service xử lý nghiệp vụ thống kê báo cáo.
 */
public class ReportService implements IReportService {
    private final ReportDAO dao = new ReportDAO();

    @Override
    public Map<String, Integer> getPopulationByBuildingFloor() {
        return dao.getPopulationByBuildingFloor();
    }

    @Override
    public Map<String, Integer> getPopulationByAgeGroup() {
        return dao.getPopulationByAgeGroup();
    }

    @Override
    public Map<String, Integer> getPopulationByGender() {
        return dao.getPopulationByGender();
    }

    @Override
    public Map<String, Integer> getPopulationByJob() {
        return dao.getPopulationByJob();
    }

    @Override
    public Map<String, Double> getTotalFeeByMonth() {
        return dao.getTotalFeeByMonth();
    }

    @Override
    public Map<String, Integer> getFacilityUsageByType() {
        return dao.getFacilityUsageByType();
    }

    @Override
    public Map<String, Double> getTotalFeeByServiceType() {
        return dao.getTotalFeeByServiceType();
    }
}
