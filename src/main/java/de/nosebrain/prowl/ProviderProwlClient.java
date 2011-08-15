/**
 *
 *  Java Prowl Client
 *
 *  Copyright (C) 2011 Daniel Zoller
 *                            http://nosebrain.de/
 *
 *  This program is free software; you can redistribute it and/or
 *  modify it under the terms of the GNU Lesser General Public License
 *  as published by the Free Software Foundation; either version 2
 *  of the License, or (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU Lesser General Public License for more details.
 *
 *  You should have received a copy of the GNU Lesser General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package de.nosebrain.prowl;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;

import de.nosebrain.prowl.response.Token;
import de.nosebrain.prowl.xml.ProwlXML;
import de.nosebrain.prowl.xml.RetrieveType;

/**
 * 
 * @author nosebrain
 */
public class ProviderProwlClient extends ProwlClient {
	
	private static final String RETRIEVE_TOKEN_PATH = "retrieve/token";
	private static final String RETRIEVE_APIKEY_PATH = "retrieve/apikey";
	
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
	public ProviderProwlClient(final String apiUrl, final String providerKey) {
		super(apiUrl);
		this.providerKey = providerKey;
	}
	
	/**
	 * @see <a href="http://www.prowlapp.com/api.php#retrieve_token">API docu</a>
	 * @return the generated token
	 * @throws IOException if any IO error occurred
	 */
	public Token retrieveToken() throws IOException {
		try {
			final List<NameValuePair> params = this.createDefaultParams();
			final URI uri = URIUtils.createURI(this.apiUrl.getScheme(), this.apiUrl.getHost(), this.apiUrl.getPort(), this.apiUrl.getPath() + RETRIEVE_TOKEN_PATH,  URLEncodedUtils.format(params, "UTF-8"), null);
			final HttpGet retrieveToken = new HttpGet(uri);
			final HttpResponse response = this.client.execute(retrieveToken);
			
			final ProwlXML responseXML = this.parseResponse(response);
			final RetrieveType retrieve = responseXML.getRetrieve();
			if (retrieve != null) {
				final Token token = new Token();
				token.setToken(retrieve.getToken());
				token.setUrl(new URL(retrieve.getUrl()));
				return token;
			}
			
			// TODO:
			throw new ProwlException();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @see <a href="http://www.prowlapp.com/api.php#retrieve_apikey">API docu</a>
	 * 
	 * @param token the token to get the api key
	 * @return the api key
	 * @throws IOException if any IO error occurred
	 */
	public String retrieveApiKey(final String token) throws IOException {
		try {
			final List<NameValuePair> params = this.createDefaultParams();
			params.add(new BasicNameValuePair("token", token));
			final URI uri = URIUtils.createURI(this.apiUrl.getScheme(), this.apiUrl.getHost(), this.apiUrl.getPort(), this.apiUrl.getPath() + RETRIEVE_TOKEN_PATH,  URLEncodedUtils.format(params, "UTF-8"), null);
			final HttpGet retrieveToken = new HttpGet(uri);
			final HttpResponse response = this.client.execute(retrieveToken);
			
			// TODO: check for error
			final ProwlXML prowlXML = this.parseResponse(response);
			final RetrieveType retrieve = prowlXML.getRetrieve();
			if (retrieve != null) {
				return retrieve.getApikey();
			}
			
			throw new ProwlException();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	protected List<NameValuePair> createDefaultParams() {
		final List<NameValuePair> params = super.createDefaultParams();
		params.add(new BasicNameValuePair("providerkey", this.providerKey));
		return params;
	}
}
