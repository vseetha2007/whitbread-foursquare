package com.whitbread.foursquare.integration.service;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.google.gson.JsonObject;
import com.whitbread.foursquare.integration.client.AbstractFoursquareClient;
import com.whitbread.foursquare.integration.domain.AbstractModel;
import com.whitbread.foursquare.integration.domain.Meta;
import com.whitbread.foursquare.integration.domain.RequestModelObject;
import com.whitbread.foursquare.integration.dto.JSONDeserializer;
import com.whitbread.foursquare.integration.util.Operation;

/**
 * Service implementation that makes the call to the HTTP client and evaluates the response
 */
@Component
public class FoursquareServiceImpl implements FoursquareService {

	@Autowired
	private AbstractFoursquareClient foursquareHttpClient;
	
	@Autowired
	private JSONDeserializer<AbstractModel> jsonDeserializer;

	protected Logger LOG = LoggerFactory.getLogger(FoursquareServiceImpl.class);
	
	private ConcurrentHashMap<RequestModelObject, String> CACHE;
	
	@PostConstruct
	public void init() {
		LOG.info("Intializing cache...");
		CACHE = new ConcurrentHashMap<>();
	}
	
	//invalidate the cache
	//set to every minute for testing purposes
	@Scheduled(cron = "0 * * * * *")
	public void flush() {
		LOG.info("Invalidating cache");
		CACHE.clear();
	}
	
	@Override
	public Collection<AbstractModel> execute(String location, Integer radius, Integer limit, Operation operation) {

		final RequestModelObject model = new RequestModelObject();
		model.setLocation(location);
		model.setLimit(limit);
		model.setRadius(radius);
		try {
			final String fourSquareResponseAsString = getResponseFromCache(model);
			if (StringUtils.isNotBlank(fourSquareResponseAsString)) {
				final JsonObject fourSquareResponseAsJson = jsonDeserializer.deserialise(fourSquareResponseAsString);
				final Meta meta = (Meta) jsonDeserializer.unmarshallMeta(fourSquareResponseAsJson);
				if (meta.getStatusCode() == HttpStatus.SC_OK) {
					LOG.info("Successful response from Foursquare API");
					return jsonDeserializer.unmarshallResponse(fourSquareResponseAsJson);
				} else {
					LOG.error(
							String.format("Request to Foursquare API had incorrect parameters. StatusCode = %s Error = %s",
									meta.getStatusCode(), meta.getError()));
				}
			} else {
				LOG.error("Request to Foursquare API was unsuccessful");
			}
			return Collections.emptyList();
		} catch (Exception e) {
			LOG.error("Unexpected error ocurred during processing", e);
			return Collections.emptyList();
		}
	}
	
	private String getResponseFromCache(RequestModelObject key) {
		if (!CACHE.containsKey(key)) {
			synchronized (CACHE) {
				if (!CACHE.containsKey(key)) {
					LOG.info("Object not found in cache. Making request to Foursquare Service");
					final String response = foursquareHttpClient.execute(key, Operation.EXPLORE);
					if (StringUtils.isNotBlank(response)) {
						CACHE.put(key, response);
					} else {
						return response;
					}
				}
			}
		}
		return CACHE.get(key);
	}
}
