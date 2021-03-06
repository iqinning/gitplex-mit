package com.gitplex.server.web.page.project.pullrequest.requestdetail.overview.activity;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.GenericPanel;
import org.apache.wicket.model.AbstractReadOnlyModel;
import org.apache.wicket.model.IModel;

import com.gitplex.server.model.User;
import com.gitplex.server.model.PullRequest;
import com.gitplex.server.model.PullRequestReference;
import com.gitplex.server.web.component.avatar.AvatarLink;
import com.gitplex.server.web.component.link.UserLink;
import com.gitplex.server.web.component.link.ViewStateAwarePageLink;
import com.gitplex.server.web.component.requeststatus.RequestStatusPanel;
import com.gitplex.server.web.page.project.pullrequest.requestdetail.overview.RequestOverviewPage;
import com.gitplex.server.web.util.DateUtils;

@SuppressWarnings("serial")
class ReferencedPanel extends GenericPanel<PullRequestReference> {

	public ReferencedPanel(String id, IModel<PullRequestReference> model) {
		super(id, model);
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		PullRequestReference reference = getModelObject();
		
		User userForDisplay = User.getForDisplay(reference.getUser(), reference.getUserName());
		add(new AvatarLink("avatar", userForDisplay));
		add(new UserLink("name", userForDisplay));
		add(new Label("age", DateUtils.formatAge(reference.getDate())));
		
		ViewStateAwarePageLink<Void> link = new ViewStateAwarePageLink<Void>("link", 
				RequestOverviewPage.class, RequestOverviewPage.paramsOf(reference.getReferencedBy()));
		link.add(new Label("number", "#" + reference.getReferencedBy().getNumber()));
		link.add(new Label("title", reference.getReferencedBy().getTitle()));
		add(link);
		
		add(new RequestStatusPanel("status", new AbstractReadOnlyModel<PullRequest>() {

			@Override
			public PullRequest getObject() {
				return ReferencedPanel.this.getModelObject().getReferencedBy();
			}
			
		}));
	}

}
