package ktpm.condo.model.service.report_service;

import java.util.Map;

/**
 * Interface định nghĩa các phương thức nghiệp vụ báo cáo thống kê.
 */
public interface IReportService {

    Map<String, Integer> getPopulationByBuildingFloor();

    Map<String, Integer> getPopulationByAgeGroup();

    Map<String, Integer> getPopulationByGender();

    Map<String, Integer> getPopulationByJob();

    Map<String, Double> getTotalFeeByMonth();

    Map<String, Integer> getFacilityUsageByType();

    Map<String, Double> getTotalFeeByServiceType();
}
