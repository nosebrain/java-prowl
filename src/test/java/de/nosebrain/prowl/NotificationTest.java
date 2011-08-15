package de.nosebrain.prowl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

import de.nosebrain.prowl.Notification.NotificationBuilder;

/**
 * 
 * @author nosebrain
 */
public class NotificationTest {

	/**
	 * tests {@link NotificationBuilder}
	 */
	@Test
	public void testBuilder() {
		final NotificationBuilder builder = new NotificationBuilder();
		
		final String application = "Test";
		final String description = "What is going on";
		final Notification notification = builder.application(application).description(description).build();
		
		assertEquals(application, notification.getApplication());
		assertEquals(NotificationPriority.NORMAL, notification.getPriority());
		assertEquals(description, notification.getDescription());
		assertNull(notification.getUrl());
		assertNull(notification.getEvent());
		
		final NotificationBuilder builder2 = new NotificationBuilder();
		
		final String event2 = "NotificationTest";
		final String application2 = "Java Prowl Client";
		final String description2 = "This is a description";
		final String url2 = "myapp://asdf24532fdfsdfsdf";
		final NotificationPriority priority2 = NotificationPriority.VERY_LOW;
		final Notification notification2 = builder2.event(event2).application(application2).description(description2).priority(priority2).url(url2).build();
		
		assertEquals(application2, notification2.getApplication());
		assertEquals(priority2, notification2.getPriority());
		assertEquals(description2, notification2.getDescription());
		assertEquals(url2, notification2.getUrl());
		assertEquals(event2, notification2.getEvent());
	}
}
