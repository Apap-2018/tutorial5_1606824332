package com.apap.tutorial5.controller;

import com.apap.tutorial5.model.*;
import com.apap.tutorial5.service.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class DealerController {
	@Autowired
	private DealerService dealerService;
	
	@Autowired
	private CarService carService;
	
	@RequestMapping("/")
	private String home(Model model) {
		model.addAttribute("title", "Home");
		return "home";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.GET)
	private String add(Model model) {
		model.addAttribute("dealer", new DealerModel());
		model.addAttribute("title", "Add Dealer");
		return "addDealer";
	}
	
	@RequestMapping(value = "/dealer/add", method = RequestMethod.POST)
	private String addDealerSubmit(@ModelAttribute DealerModel dealer, Model model) {
		dealerService.addDealer(dealer);
		model.addAttribute("title", "Berhasil! Add Car");
		return "add";
	}
	
	@RequestMapping(value = "/dealer/view")
	private String viewDealer(@RequestParam(value="dealerId") Long id, Model model) {
		Optional<DealerModel> dealerOpt = dealerService.getDealerDetailById(id);
		DealerModel dealer = new DealerModel();
		if (dealerOpt.isPresent()) {
			dealer = dealerOpt.get();
		}else {
			List<DealerModel> allDealer = dealerService.viewAll();
			model.addAttribute("allDealer", allDealer);
			return "noEntity";
		}
		List<CarModel> carsInDealer = carService.findByDealerOrderByPriceAsc(dealer);
		if (dealer.equals(null)){
			List<DealerModel> allDealer = dealerService.viewAll();
			model.addAttribute("allDealer", allDealer);
		}
		dealer.setListCar(carsInDealer);
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "View Dealer");
		return "view-dealer";
	}
	
	@RequestMapping(value = "/dealer/delete/{dealerId}")
	private String deleteCar(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		System.out.println("masuk");
		dealerService.deleteDealer(dealerId);
		model.addAttribute("title", "Berhasil! Delete Dealer");
		return "delete";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.GET)
	private String updateForm(@PathVariable(value = "dealerId") Long dealerId, Model model) {
		DealerModel dealer = dealerService.getDealerDetailById(dealerId).get();
		System.out.println(dealer.getId());
		model.addAttribute("dealer", dealer);
		model.addAttribute("title", "Update Dealer");
		return "updatedealer";
	}
	
	@RequestMapping(value = "/dealer/update/{dealerId}", method = RequestMethod.POST)
	private String updatedealer(@PathVariable(value = "dealerId") Long dealerId, @ModelAttribute DealerModel dealer, Model model) {
		dealer.setId(dealerId);
		dealerService.update(dealer);
		model.addAttribute("title", "Berhasil! Menambahkan Dealer");
		return "add";
	}
	
	@RequestMapping("/view-all")
	private String viewAll(Model model) {
		List<DealerModel> allDealer = dealerService.viewAll();
		model.addAttribute("allDealer", allDealer);
		model.addAttribute("title", "View All Car");
		return "view-all";
	}
}
