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

/**
 * 
 * @author nosebrain
 */
public enum NotificationPriority {

	/**
	 * very low priority
	 */
	VERY_LOW(-2),

	/**
	 * moderate priority
	 */
	MODERATE(-1),

	/**
	 * normal priority
	 */
	NORMAL(0),

	/**
	 * high priority
	 */
	HIGH(1),

	/**
	 * emergency priority
	 */
	EMERGENCY(2);

	private final int value;

	private NotificationPriority(final int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return this.value;
	}
}
