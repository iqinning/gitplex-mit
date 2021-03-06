package com.gitplex.server.web.behavior.inputassist;

import java.util.List;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.RepeatingView;

import com.gitplex.codeassist.InputCompletion;
import com.gitplex.codeassist.InputStatus;
import com.gitplex.jsymbol.Range;
import com.gitplex.server.web.behavior.infinitescroll.InfiniteScrollBehavior;

@SuppressWarnings("serial")
class AssistPanel extends Panel {

	private static final int PAGE_SIZE = 25;
	
	private final Component input;
	
	private final InputStatus inputStatus;
	
	private final List<String> hints;

	private final List<InputCompletion> suggestions;
	
	public AssistPanel(String id, Component input, InputStatus inputStatus, List<InputCompletion> suggestions, 
			List<String> hints) {
		super(id);
		this.input = input;
		this.inputStatus = inputStatus;
		this.hints = hints;
		this.suggestions = suggestions;
	}

	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		WebMarkupContainer suggestionsContainer = new WebMarkupContainer("suggestions");
		add(suggestionsContainer);
		RepeatingView suggestionsView = new RepeatingView("suggestions");
		suggestionsContainer.add(suggestionsView);
		int count = 0;
		for (InputCompletion suggestion: suggestions) {
			if (++count <= PAGE_SIZE)
				suggestionsView.add(newSuggestionItem(suggestionsView.newChildId(), suggestion));
		}
		
		suggestionsContainer.add(new InfiniteScrollBehavior(PAGE_SIZE) {

			@Override
			protected void appendPage(AjaxRequestTarget target, int page) {
				for (int i=(page-1)*PAGE_SIZE; i<page*PAGE_SIZE; i++) {
					if (i < suggestions.size()) {
						InputCompletion suggestion = suggestions.get(i);
						Component suggestionItem = newSuggestionItem(suggestionsView.newChildId(), suggestion);
						suggestionsView.add(suggestionItem);
						String script = String.format("$('#%s .suggestions>table>tbody').append('<tr id=\"%s\"></tr>');", 
								AssistPanel.this.getMarkupId(), suggestionItem.getMarkupId());
						target.prependJavaScript(script);
						target.add(suggestionItem);
						script = String.format("$('#%s').click(function() {$('#%s').data('update')($(this));});", 
								suggestionItem.getMarkupId(), input.getMarkupId());
						target.appendJavaScript(script);
					} else {
						break;
					}
				}
			}
			
		});

		RepeatingView hintsView = new RepeatingView("hints");
		add(hintsView);
		for (String hint: hints) 
			hintsView.add(new Label(hintsView.newChildId(), hint).setEscapeModelStrings(false));
		
		setOutputMarkupId(true);
	}
	
	private Component newSuggestionItem(String itemId, InputCompletion suggestion) {
		WebMarkupContainer item = new WebMarkupContainer(itemId);
		WebMarkupContainer link = new WebMarkupContainer("link");
		Range matchRange = suggestion.getMatchRange();
		String label = suggestion.getLabel();
		if (matchRange != null) {
			String prefix = StringEscapeUtils.escapeHtml4(label.substring(0, matchRange.getFrom()));
			String suffix = StringEscapeUtils.escapeHtml4(label.substring(matchRange.getTo()));
			String matched = StringEscapeUtils.escapeHtml4(label.substring(matchRange.getFrom(), matchRange.getTo()));
			link.add(new Label("label", prefix + "<b>" + matched + "</b>" + suffix).setEscapeModelStrings(false));
		} else {
			link.add(new Label("label", label));
		}
		item.add(link);
		if (suggestion.getDescription() != null)
			item.add(new Label("description", suggestion.getDescription()));
		else
			item.add(new Label("description").setVisible(false));
		item.add(AttributeAppender.append("data-content", suggestion.complete(inputStatus).getContent()));
		item.add(AttributeAppender.append("data-caret", suggestion.getCaret()));
		item.setOutputMarkupId(true);
		return item;
	}

}
