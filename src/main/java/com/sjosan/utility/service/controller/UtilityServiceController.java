package com.sjosan.utility.service.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sjosan.utility.service.business.UtilityServiceBusiness;
import com.sjosan.utility.service.entity.Content;
import com.sjosan.utility.service.entity.Likes;
import com.sjosan.utility.service.entity.ResponsePayload;

@RestController
public class UtilityServiceController {

	@Autowired
	UtilityServiceBusiness utilityServiceBusiness;

	public void setUtilityServiceBusiness(UtilityServiceBusiness utilityServiceBusiness) {
		this.utilityServiceBusiness = utilityServiceBusiness;
	}
	
	
	@RequestMapping(value = "/getCategory", method = RequestMethod.GET, produces = "application/json")
	public ResponsePayload getCategory() {

		ResponsePayload payload = utilityServiceBusiness.getCategory();

		return payload;
	}
	

	@RequestMapping(value = "/getContent/{categoryID}", method = RequestMethod.GET, produces = "application/json")
	public ResponsePayload getContent(@PathVariable("categoryID") int categoryID) {

		ResponsePayload payload = utilityServiceBusiness.getContent(categoryID);

		return payload;
	}
	
	@RequestMapping(value = "/saveData", method = RequestMethod.GET, produces = "application/json")
	public ResponsePayload saveData() {

		ResponsePayload payload = utilityServiceBusiness.saveData();

		return payload;
	}
	
	@RequestMapping(value = "/addContent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponsePayload addContent(@RequestBody Content content) {

		ResponsePayload payload = utilityServiceBusiness.addContent(content);

		return payload;
	}
	
	@RequestMapping(value = "/approveContent", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponsePayload approveContent(@RequestBody Content content) {

		ResponsePayload payload = utilityServiceBusiness.approveContent(content);

		return payload;
	}
	
	@RequestMapping(value = "/getContentToApprove", method = RequestMethod.GET, consumes = "application/json", produces = "application/json")
	public ResponsePayload getContentToApprove() {

		ResponsePayload payload = utilityServiceBusiness.getContentToApprove();

		return payload;
	}
	
	
	@RequestMapping(value = "/like", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponsePayload like(@RequestBody Likes like) {

		ResponsePayload payload = utilityServiceBusiness.like(like);

		return payload;
	}
	
	@RequestMapping(value = "/unlike", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
	public ResponsePayload unlike(@RequestBody Likes like) {

		ResponsePayload payload = utilityServiceBusiness.unlike(like);

		return payload;
	}
	
	@RequestMapping(value = "/getMyContent/{contentID}", method = RequestMethod.GET, produces = "application/json")
	public ResponsePayload getMyContent(@PathVariable("contentID") String contentID) {

		ResponsePayload payload = utilityServiceBusiness.getMyContent(contentID);

		return payload;
	}
	
}
