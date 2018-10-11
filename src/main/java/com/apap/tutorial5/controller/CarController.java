package com.apap.tutorial5.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {
	@Autowired
	private CarService carService;
	
	@Autowired
	private DealerService dealerService;
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		CarModel car = new CarModel();
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		car.setDealer(dealer);
		
		model.addAttribute("car", car);
		model.addAttribute("title", "Add Car");
		return "addCar";
	}
	
	@RequestMapping(value="/car/add-dynamic", params={"addRow"})
	public String addRow(final DealerModel dealer, final BindingResult bindingResult) {
	    dealer.getListCar().add(new CarModel());
	    return "insert-dynamic";
	}

	@RequestMapping(value="/car/add-dynamic", params={"removeRow"})
	public String removeRow(
	        final DealerModel dealer, final BindingResult bindingResult, 
	        final HttpServletRequest req) {
	    final Integer carId = Integer.valueOf(req.getParameter("removeRow"));
	    dealer.getListCar().remove(carId.intValue());
	    return "insert-dynamic";
	}
	
	@RequestMapping(value = "/car/add", method = RequestMethod.POST)
	private String addCarSubmit(@ModelAttribute CarModel car, Model model) {
		carService.addCar(car);
		model.addAttribute("title", "Berhasil! Add Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete/{carId}")
	private String deleteCar(@PathVariable(value = "carId") Long carId, Model model) {
		System.out.println("masuk");
		carService.deleteById(carId);
		model.addAttribute("title", "Berhasil! Delete Car");
		return "delete";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.GET)
	private String updateForm(@PathVariable(value = "carId") Long carId, Model model) {
		CarModel car = carService.getCarDetail(carId).get();
		System.out.println(car.getId());
		model.addAttribute("car", car);
		model.addAttribute("title", "Update Car");
		return "updateCar";
	}
	
	@RequestMapping(value = "/car/update/{carId}", method = RequestMethod.POST)
	private String updateCar(@PathVariable(value = "carId") Long carId, @ModelAttribute CarModel car, Model model) {
		car.setId(carId);
		carService.update(car);
		model.addAttribute("title", "Berhasil! Update Car");
		return "add";
	}
	
	@RequestMapping(value = "/car/delete", method = RequestMethod.POST)
	private String delete(@ModelAttribute DealerModel dealer, Model model) {
		for (CarModel car : dealer.getListCar()) {
			carService.deleteCar(car);
			model.addAttribute("title", "Berhasil! Delete Car");
		}
		return "delete";
	}
}
