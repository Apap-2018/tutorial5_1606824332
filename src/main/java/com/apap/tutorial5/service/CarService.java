package com.apap.tutorial5.service;
import java.util.List;
import java.util.Optional;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;

public interface CarService {
	void addCar(CarModel car);
	
	List<CarModel> findAllByPriceAsc();
	
	void deleteById(long id);
	
	void update(CarModel car);
	
	Optional<CarModel> getCarDetail(long Id);
	
	List<CarModel> findByDealerOrderByPriceAsc(DealerModel dealer);
	
	void deleteCar(CarModel car);
}
