package ktpm.condo.model.service.household_service;

import java.util.List;

import ktpm.condo.model.entity.Household;

public interface IHouseholdService {
    List<Household> getAll();
    boolean add(Household h);
    boolean delete(int id);
    Household getById(int id);
}
