package com.ccd.models;

/**
 * 
 * Model to carry Item Information;
 */
public class ActionItem {
	private String actionItem;

	public String getActionItem() {
		return actionItem;
	}

	public void setActionItem(String actionItem) {
		this.actionItem = actionItem;
	}

	@Override
	public String toString() {
		return "ActionItem [actionItem=" + actionItem + "]";
	}

}
