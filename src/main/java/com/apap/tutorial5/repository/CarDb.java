package com.apap.tutorial5.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tutorial5.model.CarModel;
import com.apap.tutorial5.model.DealerModel;

@Repository
public interface CarDb extends JpaRepository<CarModel, Long>{
	CarModel findByType(String type);
	
	List<CarModel> findAllByOrderByPriceAsc();
	
	void deleteById(long Id);
	
	List<CarModel> findByDealerOrderByPriceAsc(DealerModel dealer);
}
