package com.helfarre.BankApi.Controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.helfarre.BankApi.Entities.infocredit;
import com.helfarre.BankApi.Services.BanquerService;
import com.helfarre.BankApi.Services.infocreditImpl;

@RestController
@RequestMapping("/infos")
public class inforepoController {

	@Autowired
	private infocreditImpl infocredit;
	
	
	@GetMapping("/{id}")
	public infocredit getbyid(@PathVariable Long id) {
		return infocredit.findById(id);
	}
	
	
	@DeleteMapping("/delete/{id}")
	public void deletebyid(@PathVariable Long id) {
		infocredit.deleteById(id);
	}

	@PostMapping("/addinfos")
	public ResponseEntity<?> deletebyid(@RequestBody infocredit infoscredit) {
		Date date=new Date();
		infoscredit.setDatedemande(date);
		infocredit.addinfo(infoscredit);
		return new ResponseEntity<infocredit> (infoscredit,HttpStatus.OK);
	}
	
	
}
