package de.nosebrain.prowl;

import java.net.URL;

import de.nosebrain.prowl.response.ProwlInfo;
import de.nosebrain.prowl.response.Token;

/**
 * 
 * @author nosebrain
 */
public class ProviderProwlClient extends ProwlClient {
	
	private final String providerKey;
	
	/**
	 * apiUrl {@link #PROWL_API_SSL}
	 * @param providerKey one api key
	 */
	public ProviderProwlClient(final String providerKey) {
		this.providerKey = providerKey;
	}
	
	/**
	 * @param apiUrl the api url
	 * @param providerKey the api key
	 */
	public ProviderProwlClient(final URL apiUrl, final String providerKey) {
		super(apiUrl);
		this.providerKey = providerKey;
	}
	
	/**
	 * 
	 * @return TODO
	 * @throws ProwlException TODO
	 */
	public ProwlInfo<Token> retrieveToken() throws ProwlException {
		
		return new ProwlInfo<Token>();
	}
	
	/**
	 * 
	 * @param token
	 * @return
	 * @throws ProwlException
	 */
	public ProwlInfo<String> retrieveApiKey(final String token) throws ProwlException {
		
		
		return new ProwlInfo<String>();
	}
}
