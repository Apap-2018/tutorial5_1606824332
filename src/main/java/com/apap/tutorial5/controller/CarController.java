package com.apap.tutorial5.controller;

import java.awt.List;
import java.util.ArrayList;

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
	
//	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
//	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
//		CarModel car = new CarModel();
//		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
//		car.setDealer(dealer);
//		
//		model.addAttribute("car", car);
//		model.addAttribute("title", "Add Car");
//		return "addCar";
//	}
	
	@RequestMapping(value = "/car/add/{dealerId}", method = RequestMethod.GET)
	private String add(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		ArrayList<CarModel> cars = new ArrayList<CarModel>();
		cars.add(new CarModel());
		dealer.setListCar(cars);
		
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Add Car");
		return "addCar-dynamic";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"addRow"})
	public String addRow(@ModelAttribute DealerModel dealer, final BindingResult bindingResult, Model model) {
		if (dealer.getListCar()==null) {
			dealer.setListCar(new ArrayList<CarModel>());
		}
		dealer.getListCar().add(new CarModel());
		for (int i=0;i<dealer.getListCar().size();i++) {
			System.out.println(dealer.getListCar().get(i));
		}
		model.addAttribute("dealer", dealer);
	    return "addCar-dynamic";
	}

	@RequestMapping(value="/car/add/{dealerId}", params={"removeRow"})
	public String removeRow(@PathVariable(value = "dealerId") Long dealerId,
	        @ModelAttribute DealerModel dealer, final BindingResult bindingResult, 
	        final HttpServletRequest req, Model model) {
	    final Integer carId = Integer.valueOf(req.getParameter("removeRow"));
	    System.out.println(carId);
	    dealer.getListCar().remove(carId.intValue());
	    model.addAttribute("dealer", dealer);
	    return "addCar-dynamic";
	}
	
	@RequestMapping(value="/car/add/{dealerId}", params={"save"})
	public String saveRow(@PathVariable(value = "dealerId") Long dealerId,
			@ModelAttribute DealerModel dealer, final BindingResult bindingResult) {
		for (CarModel car : dealer.getListCar()) {
			System.out.println(dealer.getId());
			car.setDealer(dealer);
			carService.addCar(car);
		}
		return "add";
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
