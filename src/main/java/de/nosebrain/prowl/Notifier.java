package de.nosebrain.prowl;

import java.util.Arrays;

/**
 * 
 * @author nosebrain
 */
public class Notifier {

	/**
	 * main method to access {@link ProwlClient} via command line
	 * @param args
	 * @throws Exception
	 */
	public static void main(final String[] args) throws Exception {
		if (args.length < 3) {
			System.out.println("usage: application message apikey(s)");
			System.exit(1);
		}
		final String application = args[0];
		final String message = args[1];
		final ProwlClient client = new ProwlClient();
		final Notification notification = new Notification();
		notification.setApplication(application);
		notification.setDescription(message);
		client.sendNotification(notification, Arrays.asList(Arrays.copyOfRange(args, 2, args.length)));
	}
}
