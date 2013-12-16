package com.pmease.gitop.core.gatekeeper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.pmease.commons.editable.annotation.Editable;
import com.pmease.commons.editable.annotation.OmitNames;
import com.pmease.gitop.model.PullRequest;
import com.pmease.gitop.model.gatekeeper.AndGateKeeper;
import com.pmease.gitop.model.gatekeeper.CommonGateKeeper;
import com.pmease.gitop.model.gatekeeper.IfThenGateKeeper;
import com.pmease.gitop.model.gatekeeper.checkresult.CheckResult;

@SuppressWarnings("serial")
@Editable(order=100, icon="icon-lock", description="By default, users with write permission of "
		+ "the project can write to all directories of all branches. Use this gate keeper to "
		+ "restrict write access of certain branches to certain users.")
@OmitNames
public class BranchProtection extends CommonGateKeeper {
	
	private List<Entry> entries = new ArrayList<Entry>();
	
	public BranchProtection() {
		Entry entry = new Entry();
		entries.add(entry);
	}
	
	@Editable(name="Protected Branches", order=100)
	@Size(min=1)
	@Valid
	@NotNull
	public List<Entry> getEntries() {
		return entries;
	}

	public void setEntries(List<Entry> entries) {
		this.entries = entries;
	}

	@Editable
	public static class Entry implements Serializable {
		private String branches;
		
		private Long teamId;

		@Editable(order=100)
		@NotEmpty
		public String getBranches() {
			return branches;
		}

		public void setBranches(String branches) {
			this.branches = branches;
		}

		@Editable(name="Team Can Write", order=200)
		@NotNull
		public Long getTeamId() {
			return teamId;
		}

		public void setTeamId(Long teamId) {
			this.teamId = teamId;
		}

	}

	@Override
	public CheckResult doCheck(PullRequest request) {
		AndGateKeeper andGateKeeper = new AndGateKeeper();
		for (Entry entry: entries) {
			IfThenGateKeeper ifThenGateKeeper = new IfThenGateKeeper();
			IfSubmittedToSpecifiedBranches ifGate = new IfSubmittedToSpecifiedBranches();
			ifGate.setBranchIds(entry.getBranches());
			ifThenGateKeeper.setIfGate(ifGate);
			
			IfApprovedBySpecifiedTeam thenGate = new IfApprovedBySpecifiedTeam();
			thenGate.setTeamId(entry.getTeamId());
			ifThenGateKeeper.setThenGate(thenGate);
			
			andGateKeeper.getGateKeepers().add(ifThenGateKeeper);
		}
		return andGateKeeper.doCheck(request);
	}

}