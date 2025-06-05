package ktpm.condo.model.service.household_service;

import java.util.List;

import ktpm.condo.model.entity.Citizen;

public interface ICitizenService {
    List<Citizen> getByHousehold(int householdId);
    boolean add(Citizen c);
    boolean update(Citizen c);
    boolean delete(int id);
}
