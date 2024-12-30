package com.alfaris.ipsh.subscription.service;

import soap.subscription.ipsh.alfaris.com.AddSubscriptionRequest;
import soap.subscription.ipsh.alfaris.com.AddSubscriptionResponse;
import soap.subscription.ipsh.alfaris.com.UpdateSubscriptionRequest;
import soap.subscription.ipsh.alfaris.com.UpdateSubscriptionResponse;

public interface SubXml {
	 AddSubscriptionResponse add (AddSubscriptionRequest request);
	 UpdateSubscriptionResponse update(UpdateSubscriptionRequest request);
}
