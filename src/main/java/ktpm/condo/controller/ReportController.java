package ktpm.condo.controller;

import java.util.Map;

import ktpm.condo.model.service.ReportService;

/**
 * Controller điều phối báo cáo thống kê.
 */
public class ReportController {
    private final ReportService service = new ReportService();

    public Map<String, Integer> getPopulationByBuildingFloor() {
        return service.getPopulationByBuildingFloor();
    }

    public Map<String, Integer> getPopulationByAgeGroup() {
        return service.getPopulationByAgeGroup();
    }

    public Map<String, Integer> getPopulationByGender() {
        return service.getPopulationByGender();
    }

    public Map<String, Integer> getPopulationByJob() {
        return service.getPopulationByJob();
    }

    public Map<String, Double> getTotalFeeByMonth() {
        return service.getTotalFeeByMonth();
    }

    public Map<String, Integer> getFacilityUsageByType() {
        return service.getFacilityUsageByType();
    }
}
