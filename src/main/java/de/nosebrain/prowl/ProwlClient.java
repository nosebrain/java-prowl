/**
 *
 *  Java Prowl Client
 *
 *  Copyright (C) 2011 - 2012 Daniel Zoller
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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import de.nosebrain.prowl.xml.ProwlXML;

/**
 * <a href="http://www.prowlapp.com/api.php">Prowl API</a>
 * 
 * @author nosebrain
 */
public class ProwlClient {
	/**
	 * the prowl api url (ssl)
	 */
	public static final String PROWL_API_SSL = "https://api.prowlapp.com/publicapi/";

	/**
	 * the prowl api url
	 */
	public static final String PROWL_API = "http://api.prowlapp.com/publicapi/";

	private static final String JAXB_PACKAGE_DECLARATION = "de.nosebrain.prowl.xml";

	private static final String VERIFY_PATH = "verify";
	private static final String SEND_NOTIFICATION_PATH = "add";

	/**
	 * implodes a collection to a string
	 * @param collection the collection
	 * @param delimiter the delim to separate each collection item
	 * @return the imploded string
	 */
	protected static String implodeCollection(final Collection<?> collection, String delimiter) {
		if (delimiter == null) {
			delimiter = "";
		}

		final StringBuilder builder = new StringBuilder();

		for (final Object object : collection) {
			builder.append(object);
			builder.append(delimiter);
		}

		final String result = builder.toString();
		return result.substring(0, result.length() - delimiter.length());
	}

	/**
	 * validates the provided notification
	 * @param notification the notification to validate
	 */
	protected static void validate(final Notification notification) {
		final StringBuilder builder = new StringBuilder();
		final String application = notification.getApplication();
		if (application == null) {
			builder.append("application is null\n");
		} else if (application.length() > Notification.APPLICATION_MAX_LENGTH) {
			builder.append("application is too long (max: " + Notification.APPLICATION_MAX_LENGTH + ")\n");
		}

		// check length
		final String url = notification.getUrl();
		if (url != null && url.length() > Notification.URL_MAX_LENGTH) {
			builder.append("url is too long (max:" + Notification.URL_MAX_LENGTH + ")\n");
		}

		final String event = notification.getEvent();
		final String description = notification.getDescription();
		if (event == null || event.isEmpty()) {
			if (description == null || description.isEmpty()) {
				// invalid event or description must be provided
				builder.append("either event or description must be specified\n");
			} else if (description.length() > Notification.DESCRIPTION_MAX_LENGTH) {
				builder.append("description is too long (max: " + Notification.DESCRIPTION_MAX_LENGTH + ")\n");
			}
		} else if (event.length() > Notification.EVENT_MAX_LENGTH) {
			builder.append("event is too long (max: " + Notification.EVENT_MAX_LENGTH + ")\n");
		}

		final String errorsString = builder.toString();
		if (!errorsString.isEmpty()) {
			throw new IllegalArgumentException(errorsString);
		}
	}

	/**
	 * the api uri
	 */
	protected final URI apiUrl;

	/**
	 * the client to use
	 */
	protected HttpClient client = new DefaultHttpClient();

	/**
	 * apiUrl {@link #PROWL_API_SSL}
	 */
	public ProwlClient() {
		this(PROWL_API_SSL);
	}

	/**
	 * @param apiUrl the api url
	 */
	public ProwlClient(final String apiUrl) {
		try {
			this.apiUrl = new URI(apiUrl);
		} catch (final URISyntaxException e) {
			throw new IllegalArgumentException(e);
		}
	}

	/**
	 * @param notification the notification to send
	 * @param apiKey the api keys the notification to send to
	 * @throws IOException if any IO error occurred
	 */
	public void sendNotification(final Notification notification, final String apiKey) throws IOException {
		this.sendNotification(notification, Collections.singletonList(apiKey));
	}

	/**
	 * @param notification the notification to send
	 * @param apiKeys the api keys the notification to send to
	 * @throws IOException if any IO error occurred
	 */
	public void sendNotification(final Notification notification, final List<String> apiKeys) throws IOException {
		/*
		 * validate apikeys
		 */
		boolean validApiKeys = apiKeys != null && apiKeys.size() > 0;
		for (final String string : apiKeys) {
			if (string == null || string.trim().length() != 40) {
				validApiKeys = false;
				break;
			}
		}
		if (!validApiKeys) {
			throw new IllegalStateException("At least one api key is invalid. Please check.");
		}

		/*
		 * validate notification (length, required values)
		 */
		validate(notification);

		final List<NameValuePair> params = this.createDefaultParams();
		params.add(new BasicNameValuePair("apikey", implodeCollection(apiKeys, ",")));
		params.add(new BasicNameValuePair("application", notification.getApplication()));

		if (notification.getEvent() != null) {
			params.add(new BasicNameValuePair("event", notification.getEvent()));
		}

		if (notification.getDescription() != null) {
			params.add(new BasicNameValuePair("description", notification.getDescription()));
		}

		/*
		 * optional values
		 */
		if (notification.getUrl() != null) {
			params.add(new BasicNameValuePair("url", notification.getUrl().toString()));
		}

		if (notification.getPriority() != null) {
			params.add(new BasicNameValuePair("priority", String.valueOf(notification.getPriority().getValue())));
		}

		final HttpPost sendNotification = new HttpPost(this.apiUrl.toString() + SEND_NOTIFICATION_PATH);
		sendNotification.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));
		final HttpResponse response = this.client.execute(sendNotification);
		this.releaseConnection(response); // TODO: parse success
	}

	/**
	 * @param apiKey the api key to verify
	 * @return <code>true</code> iff the api key is valid
	 * @throws IOException if any IO error occurred
	 */
	public boolean verify(final String apiKey) throws IOException {
		try {
			final List<NameValuePair> params = this.createDefaultParams();
			params.add(new BasicNameValuePair("apikey", apiKey));
			final URI uri = URIUtils.createURI(this.apiUrl.getScheme(), this.apiUrl.getHost(), this.apiUrl.getPort(), this.apiUrl.getPath() + VERIFY_PATH,  URLEncodedUtils.format(params, "UTF-8"), null);
			final HttpGet verify = new HttpGet(uri);

			final HttpResponse response = this.client.execute(verify);
			final StatusLine statusLine = response.getStatusLine();

			this.releaseConnection(response);
			return statusLine.getStatusCode() == 200;
		} catch (final URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}

	private void releaseConnection(final HttpResponse response) throws IOException {
		final HttpEntity entity = response.getEntity();
		EntityUtils.consume(entity);
	}

	protected ProwlXML parseResponse(final HttpResponse response) throws IOException {
		final HttpEntity entity = response.getEntity();
		if (entity != null) {
			try {
				final InputStream instream = entity.getContent();
				return this.parseXML(instream);
			} finally {
				EntityUtils.consume(entity);
			}
		}
		return null;
	}

	protected ProwlXML parseXML(final InputStream instream) throws IOException {
		try {
			final Reader reader = new InputStreamReader(instream);
			final JAXBContext context = JAXBContext.newInstance(JAXB_PACKAGE_DECLARATION, this.getClass().getClassLoader());
			final Unmarshaller unmarshaller = context.createUnmarshaller();
			@SuppressWarnings("unchecked")
			final JAXBElement<ProwlXML> unmarshal = (JAXBElement<ProwlXML>) unmarshaller.unmarshal(reader);
			return unmarshal.getValue();
		} catch (final JAXBException e) {
			throw new IllegalStateException(e);
		}
	}

	/**
	 * @return the default params
	 */
	protected List<NameValuePair> createDefaultParams() {
		return new LinkedList<NameValuePair>();
	}

	/**
	 * @param client the client to set
	 */
	public void setClient(final HttpClient client) {
		this.client = client;
	}
}
