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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import de.nosebrain.prowl.xml.ErrorType;
import de.nosebrain.prowl.xml.ProwlXML;
import de.nosebrain.prowl.xml.RetrieveType;
import de.nosebrain.prowl.xml.SuccessType;

/**
 * 
 * @author nosebrain
 */
public class ProwlClientTest {
	
	private static final ProwlClient CLIENT = new ProwlClient();

	@Test
	public void testValidate() {
		final Notification notification = new Notification();
		notification.setPriority(null);
		
		try {
			ProwlClient.validate(notification);
			fail("validation passed");
		} catch (IllegalArgumentException e) {
			// TODO
		}
		
		// TODO:
	}
	
	private static ProwlXML parseXML(final String filename) throws IOException {
		final InputStream instream = ProwlClientTest.class.getClassLoader().getResourceAsStream(filename);
		return CLIENT.parseXML(instream);
	}
	
	@Test
	public void testParseError1() throws IOException {
		/*
		 * simple error
		 */
		final ProwlXML parseXML = parseXML("error1.xml");
		
		final ErrorType errorType = parseXML.getError();
		assertEquals(new Integer(401), errorType.getCode());
		assertEquals("API key is required", errorType.getValue());
		
		/*
		 * retrieve token success
		 */
		final ProwlXML parseXML2 = parseXML("retrieveTokenSuccess.xml");
		
		final SuccessType success = parseXML2.getSuccess();
		assertEquals(new Integer(200), success.getCode());
		assertEquals(new Integer(12), success.getRemaining());
		assertEquals(new Integer(1293861600), success.getResetdate());
		
		final RetrieveType retrieve = parseXML2.getRetrieve();
		final String string40 = "apx0pa2e36pl51445m86p148sp5ixx8l8h5ubcnl";
		assertEquals(string40, retrieve.getToken());
		assertEquals("http://bitbucket.org/apiRequest", retrieve.getUrl());
		
		/*
		 * retrieve apikey success
		 */
		final ProwlXML parseXML3 = parseXML("retrieveApikeySuccess.xml");
		assertEquals(string40, parseXML3.getRetrieve().getApikey());
		
		final ProwlXML parseXML4 = parseXML("success.xml");
		final SuccessType success4 = parseXML4.getSuccess();
		assertEquals(new Integer(200), success4.getCode());
		assertEquals(new Integer(997), success4.getRemaining());
		assertEquals(new Integer(1313433851), success4.getResetdate());
	}
}
