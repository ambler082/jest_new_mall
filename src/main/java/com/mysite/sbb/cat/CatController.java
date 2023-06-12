package com.mysite.sbb.cat;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CatController {
	@GetMapping("/cat")
	public String cat_home() {
		return "cat";
	}
	
	@GetMapping("/cat_detail")
	public String cat_detail() {
		return "cat_detail";
	}
}
