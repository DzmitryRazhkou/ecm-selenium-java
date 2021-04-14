package com.app.entities;

/**
 * POJO class of a Group.
 * @author Muana Kimba
 */
public class Group {
	
	// MEMBERS
	private String groupID;
	private String groupName;
	
	// CONSTRUCTOR
	public Group(String groupID, String groupName) {
		this.groupID = groupID;
		this.groupName = groupName;
	}
	
	// GETTERS
	public String getGroupID() {
		return groupID;
	}
	public String getGroupName() {
		return groupName;
	}
	
	@Override
	public String toString() {
		return "Group [groupID=" + groupID + ", groupName=" + groupName + "]";
	}
}