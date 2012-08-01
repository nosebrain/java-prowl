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

import java.util.Arrays;

/**
 * 
 * @author nosebrain
 */
public class Notifier {

	/**
	 * TODO:
	 * - add provider key param
	 * - add priority
	 * 
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
