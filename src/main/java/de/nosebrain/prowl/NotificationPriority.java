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
	
	private NotificationPriority(int value) {
		this.value = value;
	}

	/**
	 * @return the value
	 */
	public int getValue() {
		return value;
	}
}
