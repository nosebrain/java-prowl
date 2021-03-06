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

import java.net.URL;

/**
 * 
 * @author nosebrain
 */
public class Notification {
	
	private static final NotificationPriority DEFAULT_PRIORITY = NotificationPriority.NORMAL;
	
	/**
	 * the max length of the {@link #getApplication()} property
	 */
	public static final int APPLICATION_MAX_LENGTH = 256;
	
	/**
	 * the max length of the {@link #getEvent()} property
	 */
	public static final int EVENT_MAX_LENGTH = 1024;

	/**
	 * the max length of the {@link #getUrl()} property
	 */
	public static final int URL_MAX_LENGTH = 512;

	/**
	 * the max length if the {@link #getDescription()} property
	 */
	public static final int DESCRIPTION_MAX_LENGTH = 10000;
	
	/**
	 * 
	 * @author nosebrain
	 */
	public static class NotificationBuilder {
		
		private NotificationPriority priority = DEFAULT_PRIORITY;
		
		private String event;
		private String description;
		
		private String url;
		
		private String application;
		
		/**
		 * @param application the application to set
		 * @return the builder
		 */
		public NotificationBuilder application(final String application) {
			this.application = application;
			return this;
		}
		
		/**
		 * @param event the event to set
		 * @return the builder
		 */
		public NotificationBuilder event(final String event) {
			this.event = event;
			return this;
		}
		
		/**
		 * @param description the description to set
		 * @return the builder
		 */
		public NotificationBuilder description(final String description) {
			this.description = description;
			return this;
		}
		
		/**
		 * @param priority the priority to set
		 * @return the builder
		 */
		public NotificationBuilder priority(final NotificationPriority priority) {
			this.priority = priority;
			return this;
		}
		
		/**
		 * @param url the url to set
		 * @return the builder
		 */
		public NotificationBuilder url(final String url) {
			this.url = url;
			return this;
		}
		
		/**
		 * @return the notification
		 */
		public Notification build() {
			return new Notification(this);
		}
	}
	
	private NotificationPriority priority = DEFAULT_PRIORITY;
	
	private String event;
	private String description;
	
	/**
	 * XXX: using a string instead of an {@link URL} to support
	 * special schemas for app launching
	 */
	private String url;
	
	private String application;
	
	/**
	 * default constructor
	 */
	public Notification() {
		// noop
	}
	
	private Notification(final NotificationBuilder builder) {
		this.priority = builder.priority;
		this.url = builder.url;
		this.application = builder.application;
		this.event = builder.event;
		this.description = builder.description;
	}

	/**
	 * @return the priority
	 */
	public NotificationPriority getPriority() {
		return this.priority;
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
		return this.event;
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
		return this.description;
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
	public String getUrl() {
		return this.url;
	}

	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return this.application;
	}

	/**
	 * @param application the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}
	
}
