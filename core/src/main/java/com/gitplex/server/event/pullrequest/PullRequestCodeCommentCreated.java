package com.gitplex.server.event.pullrequest;

import java.util.Date;

import com.gitplex.server.event.MarkdownAware;
import com.gitplex.server.model.User;
import com.gitplex.server.model.CodeComment;
import com.gitplex.server.util.editable.annotation.Editable;

@Editable(name="commented code")
public class PullRequestCodeCommentCreated extends PullRequestCodeCommentEvent implements MarkdownAware {

	public PullRequestCodeCommentCreated(CodeComment comment) {
		super(comment);
	}

	@Override
	public String getMarkdown() {
		return getComment().getContent();
	}

	@Override
	public User getUser() {
		return getComment().getUser();
	}

	@Override
	public Date getDate() {
		return getComment().getDate();
	}

}
