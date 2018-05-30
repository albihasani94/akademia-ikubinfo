package al.ikubinfo.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import al.ikubinfo.jpa.model.City;
import al.ikubinfo.jpa.service.CityService;

public class App {
	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) {

		emf = Persistence.createEntityManagerFactory("al.ikubinfo.jpa");
		em = emf.createEntityManager();
		EntityTransaction tx = em.getTransaction();

		CityService cityService = new CityService(em);

		/**
		 * Get and print all the cities
		 */

		List<City> cities = cityService.getAllCities();

		for (City city : cities) {
			System.out.println(city);
		}

		/**
		 * Add a new city
		 */
		tx.begin();
		cityService.createCity("Tepelene", "AL", "Vlore", 10000);
		tx.commit();

		/**
		 * Get the new entered city
		 */
		City city = cityService.getCityWithName("Tepelene");
		System.out.println(city);

		/**
		 * Add another city
		 */
		tx.begin();
		cityService.createCity("Korce", "AL", "Korce", 50000);
		tx.commit();

		// Update the newly entered city
		tx.begin();
		cityService.updateCityWithBulkUpdate("Korce", "Fier");
		tx.commit();

		/**
		 * Get the updated city
		 */
		City updatedCity = cityService.getCityWithName("Fier");
		System.out.println(updatedCity);

		/**
		 * Remove the new entered city
		 */
		tx.begin();
		cityService.removeCityByNameWithBulkDelete("Fier");
		tx.commit();

		/**
		 * Get all the cities again
		 */
		cityService.getAllCities().forEach(System.out::println);

		/**
		 * Release the resources
		 */
		em.close();
		emf.close();

	}
}
