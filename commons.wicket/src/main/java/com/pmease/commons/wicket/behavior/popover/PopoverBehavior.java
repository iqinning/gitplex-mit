package com.pmease.commons.wicket.behavior.popover;

import org.apache.wicket.Component;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.resource.PackageResourceReference;

import com.pmease.commons.wicket.behavior.dropdown.DropdownAlignment;
import com.pmease.commons.wicket.behavior.dropdown.DropdownAlignment.INDICATOR_MODE;
import com.pmease.commons.wicket.behavior.dropdown.DropdownBehavior;

@SuppressWarnings("serial")
public class PopoverBehavior extends DropdownBehavior {

	public PopoverBehavior(PopoverPanel popoverPanel) {
		super(popoverPanel);
		
		setAlignment(new DropdownAlignment(100, 50, 0, 50).setIndicatorMode(INDICATOR_MODE.SHOW));
	}

	@Override
	public void renderHead(Component component, IHeaderResponse response) {
		super.renderHead(component, response);
		response.render(CssHeaderItem.forReference(new PackageResourceReference(PopoverBehavior.class, "popover.css")));
	}

}