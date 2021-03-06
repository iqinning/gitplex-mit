package com.gitplex.server.web.assets.js.codemirror;

import java.util.List;

import org.apache.wicket.markup.head.HeaderItem;
import org.apache.wicket.markup.head.OnDomReadyHeaderItem;
import org.apache.wicket.request.cycle.RequestCycle;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

import com.gitplex.server.util.StringUtils;

import de.agilecoders.wicket.webjars.request.resource.WebjarsJavaScriptResourceReference;

public class ModeUrlResourceReference extends JavaScriptResourceReference {

	private static final long serialVersionUID = 1L;

	public ModeUrlResourceReference() {
		super(ModeUrlResourceReference.class, "blank.js");
	}

	/*
	 * Since we have to rely on RequestCycle to get correct mode url, we can not bundle this resource, 
	 * otherwise the getDependencies() method will be called to cause issues
	 */
	@Override
	public boolean canBeRegistered() {
		return false;
	}

	@Override
	public List<HeaderItem> getDependencies() {
		List<HeaderItem> dependencies = super.getDependencies();
		WebjarsJavaScriptResourceReference metaReference = new WebjarsJavaScriptResourceReference("codemirror/current/mode/meta.js");
		String modeBase = StringUtils.substringBeforeLast(RequestCycle.get().urlFor(metaReference, new PageParameters()).toString(), "/");
		dependencies.add(OnDomReadyHeaderItem.forScript("CodeMirror.modeURL = '" + modeBase + "/%N/%N.js';"));		
		return dependencies;
	}

}
