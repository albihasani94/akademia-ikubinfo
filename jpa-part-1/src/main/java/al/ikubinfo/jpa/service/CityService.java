package al.ikubinfo.jpa.service;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import al.ikubinfo.jpa.model.City;

public class CityService {

	private EntityManager em;

	public CityService(EntityManager em) {
		this.em = em;
	}

	public List<City> getAllCities() {
		TypedQuery<City> query = em.createQuery("Select c from City c", City.class);
		return query.getResultList();
	}

	public void createCity(String name, String countryCode, String district, int population) {
		City city = new City();
		city.setName(name);
		city.setCountryCode(countryCode);
		city.setDistrict(district);
		city.setPopulation(population);

		em.persist(city);
	}

	public City getCityWithName(String name) {
		TypedQuery<City> query = em.createQuery("Select c from City c where c.name = :name", City.class);
		query.setParameter("name", name);
		return query.getSingleResult();
	}

	/*public void removeCityByNameWithEntityManager(String name) {
		City city = this.getCityWithName(name);
		em.remove(city);
	}*/

	public void removeCityByNameWithBulkDelete(String name) {
		Query query = em.createQuery("Delete From City c where c.name = :name");
		query.setParameter("name", name);
		query.executeUpdate();
	}

	/*public void updateCityWithMerge() {
		City cityToBeUpdated = em.find(City.class, 3793);

		cityToBeUpdated.setCountryCode("AL");
		cityToBeUpdated.setDistrict("Korca");

		cityToBeUpdated = em.merge(cityToBeUpdated);
		System.out.println(cityToBeUpdated);
	}*/

	public void updateCityWithBulkUpdate(String oldName, String newName) {
		Query query = em.createQuery("Update City c Set c.name = :newName where c.name = :oldName");
		query.setParameter("newName", newName);
		query.setParameter("oldName", oldName);

		query.executeUpdate();
	}

}
