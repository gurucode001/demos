package com.demo.cucumber.cucumberdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SnakeController {
	
	private boolean fed;
 
	@Autowired
	public SnakeController() {
		super();
	}
	
	public void feed()
	{
		fed = true;
	}
 
	public String poke() {
		if(this.fed == true)
			return "snek snek ^_^";
		else
			return "hiss";
	}
}