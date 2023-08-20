package com.amine.citiesapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.amine.citiesapi.entities.City;
import com.amine.citiesapi.service.CityService;
import com.amine.citiesapi.utils.GlobalValues;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/public")
@CrossOrigin(origins = "http://localhost:4200")
public class CityPublicRestController {
		
	@Autowired
	CityService cityService;
	
	@GetMapping("cities")
	public ResponseEntity<List<City>> findAllCities(@RequestParam(value = "page", defaultValue = "0", required = false) int page){
		return new ResponseEntity<>(cityService.findAllCities(page, GlobalValues.paginationPageSize), HttpStatus.OK);
	}
	
	@GetMapping("cities/{id}")
	public ResponseEntity<City> findCityById(@PathVariable int id) {
		return new ResponseEntity<>(cityService.findCityById(id), HttpStatus.OK);
	}
	
	@PostMapping("cities")
	public ResponseEntity<City> saveCity(@Valid @RequestBody City city) {
		return new ResponseEntity<>(cityService.saveCity(city), HttpStatus.CREATED);
	}
	
	@PutMapping("cities/{id}")
	public ResponseEntity<City> updateCityById(@PathVariable int id, @Valid @RequestBody City city) {
		return new ResponseEntity<>(cityService.updateCityById(id, city), HttpStatus.OK);
	}
	
	@DeleteMapping("cities/{id}")
	public ResponseEntity<Boolean> deleteCityById(@PathVariable int id) {
		cityService.deleteCityById(id);
		return new ResponseEntity<>(true, HttpStatus.OK);
	}
	
	@GetMapping("cities/iso3/{iso3}")
	public ResponseEntity<List<City>> findCitiesByIso3(@PathVariable String iso3){
		return new ResponseEntity<>(cityService.findCitiesByIso3(iso3), HttpStatus.OK);
	}
	
	@GetMapping("cities/pagination")
	public ResponseEntity<Map<String, Long>> getPaginationInformation(){
		return new ResponseEntity<>(cityService.getPaginationInformation(), HttpStatus.OK);
	}

}
