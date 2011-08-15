package de.nosebrain.prowl.response;

import java.util.Date;

/**
 * 
 * @author nosebrain
 * @param <I> TODO: document
 */
public class ProwlInfo<I> {

	private int remainingNotifications;

	private Date resetdate;
	
	private I info;

	/**
	 * @return the remainingNotifications
	 */
	public int getRemainingNotifications() {
		return remainingNotifications;
	}

	/**
	 * @param remainingNotifications the remainingNotifications to set
	 */
	public void setRemainingNotifications(int remainingNotifications) {
		this.remainingNotifications = remainingNotifications;
	}

	/**
	 * @return the resetdate
	 */
	public Date getResetdate() {
		return resetdate;
	}

	/**
	 * @param resetdate the resetdate to set
	 */
	public void setResetdate(Date resetdate) {
		this.resetdate = resetdate;
	}

	/**
	 * @return the info
	 */
	public I getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(I info) {
		this.info = info;
	}
}
