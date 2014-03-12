package com.pmease.gitop.model;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

@Embeddable
public class CloseInfo {

	public enum Status {INTEGRATED, DISCARDED};
	
	private Status closeStatus;

	@ManyToOne
	private User closedBy;
	
	private Date closeDate = new Date();
	
	private String comment;
	
	public @Nullable User getClosedBy() {
		return closedBy;
	}
	
	public void setClosedBy(@Nullable User closedBy) {
		this.closedBy = closedBy;
	}

	public Status getCloseStatus() {
		return closeStatus;
	}

	public void setCloseStatus(Status closeStatus) {
		this.closeStatus = closeStatus;
	}
	
	public Date getCloseDate() {
		return closeDate;
	}

	public void setCloseDate(Date closeDate) {
		this.closeDate = closeDate;
	}
	
	public @Nullable String getComment() {
		return comment;
	}

	public void setComment(@Nullable String comment) {
		this.comment = comment;
	}

}