package com.AppointmentMonitoring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.AppointmentMonitoring.model.District;
import com.AppointmentMonitoring.repository.DistrictRepository;
import com.AppointmentMonitoring.service.DistrictService;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;



@Controller
public class districtController {
	@Autowired
	DistrictRepository districtRepository;
	@Autowired
	DistrictService districtService;
	RedirectAttributes attributes;
	
	 @GetMapping("/districtlist")
	    public String viewDistrictlist(Model model) {
		 List<District> listDistricts = districtRepository.findAll();
	        model.addAttribute("listDistricts", listDistricts);
	        return "districts";
	    }
	
	 @GetMapping("/ViewDistrictAddForm")
	    public String ViewDistrictAdd(Model model) {
	    	model.addAttribute("district", new District());
	        return "districtadd";
	    }
	 
	 @PostMapping("/AddDistrict")
	    public String AddDistrict(@Valid District districtmaster, BindingResult result, Model model) {	
		 if (result.hasErrors()) {
	            return "districtadd";
	        }
		 districtRepository.save(districtmaster);
	         
	        return "redirect:districtlist";
	    }
	 
	 @GetMapping("/districtView/{districtId}")
	 public String districtViewMethod(@PathVariable Long districtId, Model model) {
		 Optional<District> district = districtService.getDistrict(districtId);
		 model.addAttribute("district", district);
		 System.out.println("vvvvvv  "+district.get().getDistrictNameEng()+ district.get().getDistrictNameMal()+ district.get().getDistrictCode());
		 return "districtview";
	 }
	
	 @GetMapping("/districtEdit/{districtId}")
	 public String districtEditMethod(@PathVariable Long districtId, Model model) {
		 Optional<District> district = districtService.getDistrict(districtId);
		 model.addAttribute("district", district);
		 return "districtedit";
	 }
	
	// @RequestMapping(method=RequestMethod.PUT, value="/districtUpdate/{districtId}")
	 @PutMapping("/districtUpdate")
	 public String districtUpdateMethod( @Valid District district, BindingResult result) {
		long districtId= district.getDistrictId();
		if (result.hasErrors()) {
            return "districtedit";
        }
		districtService.update(district, districtId);
		 return "redirect:districtlist";
	 }
	 
	 @GetMapping("/districtDeleteForm/{districtId}")
	 public String districtDeleteFormMethod(@PathVariable Long districtId,Model model) {
		 Optional<District> district = districtService.getDistrict(districtId);
		 model.addAttribute("district", district);
		 return "districtDeleteView";
	 }
	 
	 @GetMapping("/DeleteDistrict/{districtId}")
	 public String districtDeleteMethod(@PathVariable Long districtId) { 
		districtService.delete(districtId);
		 return "redirect:../districtlist";
	 }
	
}
