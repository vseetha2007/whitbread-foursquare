package com.whitbread.foursquare.integration.controller;

import java.util.Collection;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.whitbread.foursquare.integration.domain.AbstractModel;
import com.whitbread.foursquare.integration.domain.RequestModelObject;
import com.whitbread.foursquare.integration.service.FoursquareService;
import com.whitbread.foursquare.integration.util.Operation;

@Controller
public class ExploreController {
    
	@Value("#{'${gui.limit.values}'.split(',')}")
    private List<String> limitValues;

    @Value("#{'${gui.radius.values}'.split(',')}")
    private List<String> radiusValues;


    @Value("${gui.limit.default}")
    private Integer limitDefault;

    @Value("${gui.radius.default}")
    private Integer radiusDefault;

    
    @Autowired
    private FoursquareService fourSquareService;
	
    @RequestMapping(value = "/explore", method = RequestMethod.GET)
    public String explore(Model model){
    	RequestModelObject requestModelObject = new RequestModelObject();
    	requestModelObject.setLimit(limitDefault);
    	requestModelObject.setRadius(radiusDefault);
    	model.addAttribute("explore",requestModelObject);
    	model.addAttribute("limitValues", limitValues);
    	model.addAttribute("radiusValues", radiusValues);

    	return "explore";
	}
    
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public String explore(@ModelAttribute("explore") @Valid RequestModelObject requestModelObject, BindingResult result,Model model){
    	if (result.hasErrors()) {
    		System.out.println("Validation Errors occurred --->");
            return "explore";
        }
    	Collection<AbstractModel> searchList=fourSquareService.execute(requestModelObject.getLocation(), requestModelObject.getRadius(), requestModelObject.getLimit(), Operation.EXPLORE);
    	
    	//String response=fourSquareService.explore(exploreRequest);
    	System.out.println("Response Returned ===> "+searchList);
    	model.addAttribute("response",searchList);
    	model.addAttribute("searchList",searchList);

    	return "success";
	}

}
