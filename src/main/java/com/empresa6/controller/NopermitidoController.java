package com.empresa6.controller;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping(path="/api/v1")
@RequiredArgsConstructor
public class NopermitidoController {
	 @PostMapping (value="demo")
	 public String welcome() {
		 return "welcome from secure endpoint";
	 }

}