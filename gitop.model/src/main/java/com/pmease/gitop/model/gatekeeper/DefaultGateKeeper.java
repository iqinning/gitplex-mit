package com.pmease.gitop.model.gatekeeper;

import com.pmease.commons.editable.annotation.Editable;
import com.pmease.gitop.model.PullRequest;
import com.pmease.gitop.model.gatekeeper.checkresult.Accepted;
import com.pmease.gitop.model.gatekeeper.checkresult.CheckResult;

@SuppressWarnings("serial")
@Editable
public class DefaultGateKeeper extends AbstractGateKeeper {

	@Override
	public Object trim(Object context) {
		return null;
	}

	@Override
	public CheckResult doCheck(PullRequest request) {
		return new Accepted("Not defined.");
	}

}