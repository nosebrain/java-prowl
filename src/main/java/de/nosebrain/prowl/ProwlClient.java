package de.nosebrain.prowl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import de.nosebrain.prowl.response.ProwlInfo;

/**
 * http://www.prowlapp.com/api.php
 * 
 * @author nosebrain
 */
public class ProwlClient {
	
	/**
	 * TODO: document me
	 */
	public static final URL PROWL_API_SSL;
	
	/**
	 * TODO: document me
	 */
	public static final URL PROWL_API;
	
	static {
		try {
			PROWL_API = new URL("http://api.prowlapp.com/publicapi/");
			PROWL_API_SSL = new URL("https://api.prowlapp.com/publicapi/");
		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
	private final URL apiUrl;
	
	/**
	 * apiUrl {@link #PROWL_API_SSL}
	 */
	public ProwlClient() {
		this(PROWL_API_SSL);
	}
	
	/**
	 * @param apiUrl the api url
	 */
	public ProwlClient(final URL apiUrl) {
		this.apiUrl = apiUrl;
	}
	
	/**
	 * @param notification the notification to send
	 * @param apiKey the api keys the notification to send to
	 * @return the info returned by the service
	 * @throws ProwlException TODO: document me
	 */
	public ProwlInfo<Void> sendNotification(final Notification notification, final String apiKey) throws ProwlException {
		return this.sendNotification(notification, Collections.singletonList(apiKey));
	}

	/**
	 * @param notification the notification to send
	 * @param apiKeys the api keys the notification to send to
	 * @return the info returned by the service
	 * @throws ProwlException TODO: document me
	 */
	public ProwlInfo<Void> sendNotification(final Notification notification, final List<String> apiKeys) throws ProwlException {
		
		
		return new ProwlInfo<Void>();
	}
	
	/**
	 * 
	 * @param apiKey
	 * @return
	 */
	public boolean verify(final String apiKey) {
		return false;
	}
}
