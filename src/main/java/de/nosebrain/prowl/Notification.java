package de.nosebrain.prowl;

import java.net.URL;

/**
 * 
 * @author nosebrain
 */
public class Notification {
	
	private NotificationPriority priority = NotificationPriority.NORMAL;
	
	private String event;
	private String description;
	
	// TODO: use string? e.g. app url handlers
	private URL url;
	
	private String application;

	/**
	 * @return the priority
	 */
	public NotificationPriority getPriority() {
		return priority;
	}

	/**
	 * @param priority the priority to set
	 */
	public void setPriority(NotificationPriority priority) {
		this.priority = priority;
	}

	/**
	 * @return the event
	 */
	public String getEvent() {
		return event;
	}

	/**
	 * @param event the event to set
	 */
	public void setEvent(String event) {
		this.event = event;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the url
	 */
	public URL getUrl() {
		return url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(URL url) {
		this.url = url;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	
}
