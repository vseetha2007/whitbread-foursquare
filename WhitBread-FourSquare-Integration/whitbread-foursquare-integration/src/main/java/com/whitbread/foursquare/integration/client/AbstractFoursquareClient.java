package com.whitbread.foursquare.integration.client;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;

import com.whitbread.foursquare.integration.domain.RequestModelObject;
import com.whitbread.foursquare.integration.util.Operation;

/**
 * Abstract client for HTTP calls
 * @author mihaianghel
 */
public abstract class AbstractFoursquareClient {
	
	@Value("${4sq.endpoint}")
	protected String endpoint;

	@Value("${4sq.api.version}")
	protected String apiVersion;
	
	@Value("${4sq.clientid}")
	protected String clientId;

	@Value("${4sq.clientsecret}")
	protected String clientSecret;
	
	/**
	 * Initialise HTTP client
	 */
	public abstract void init();

	/**
	 * Execute HTTP calls
	 * @param model object encapsulating user input
	 * @param operation - /explore in this case. Available for extension
	 * @return String representation of the response from Foursquare service
	 */
	public abstract String execute(RequestModelObject model, Operation operation);
	
	/**
	 * Destroy HTTP client
	 * @throws IOException
	 */
	public abstract void destroy() throws IOException;

}
