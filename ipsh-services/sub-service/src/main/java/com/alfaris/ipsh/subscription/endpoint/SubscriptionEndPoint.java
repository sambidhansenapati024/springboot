package com.alfaris.ipsh.subscription.endpoint;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.alfaris.ipsh.subscription.service.SubXmlImpl;

import soap.subscription.ipsh.alfaris.com.AddSubscriptionRequest;
import soap.subscription.ipsh.alfaris.com.AddSubscriptionResponse;



@Endpoint
public class SubscriptionEndPoint {

	private static final String NAMESPACE_URI = "http://com.alfaris.ipsh.subscription.soap";

	@Autowired
	private SubXmlImpl subXmlService;


	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "AddSubscriptionRequest")
	@ResponsePayload
	public AddSubscriptionResponse addSub(@RequestPayload AddSubscriptionRequest request) {
		return subXmlService.add(request);
		
	}
	
//	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "getEmployeeByIdRequest")
//	@ResponsePayload
//	public GetEmployeeResponse getEmployee(@RequestPayload GetEmployeeByIdRequest request) {
//		GetEmployeeResponse response = new GetEmployeeResponse();
//		EmployeeInfo employeeInfo = new EmployeeInfo();
//		BeanUtils.copyProperties(employeeService.getEmployeeById(request.getEmployeeId()), employeeInfo);
//		response.setEmployeeInfo(employeeInfo);
//		return response;
//	}
//
//	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "updateEmployeeRequest")
//	@ResponsePayload
//	public UpdateEmployeeResponse updateEmployee(@RequestPayload UpdateEmployeeRequest request) {
//		Employee employee = new Employee();
//		BeanUtils.copyProperties(request.getEmployeeInfo(), employee);
//		employeeService.updateEmployee(employee);
//		ServiceStatus serviceStatus = new ServiceStatus();
//		serviceStatus.setStatus("SUCCESS");
//		serviceStatus.setMessage("Content Updated Successfully");
//		UpdateEmployeeResponse response = new UpdateEmployeeResponse();
//		response.setServiceStatus(serviceStatus);
//		return response;
//	}
//
//	@PayloadRoot(namespace = NAMESPACE_URI, localPart = "deleteEmployeeRequest")
//	@ResponsePayload
//	public DeleteEmployeeResponse deleteEmployee(@RequestPayload DeleteEmployeeRequest request) {
//		employeeService.deleteEmployee(request.getEmployeeId());
//		ServiceStatus serviceStatus = new ServiceStatus();
//
//		serviceStatus.setStatus("SUCCESS");
//		serviceStatus.setMessage("Content Deleted Successfully");
//		DeleteEmployeeResponse response = new DeleteEmployeeResponse();
//		response.setServiceStatus(serviceStatus);
//		return response;
//	}

}