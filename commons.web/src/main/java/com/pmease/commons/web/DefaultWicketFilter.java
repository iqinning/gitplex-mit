package com.pmease.commons.web;

import org.apache.wicket.protocol.http.IWebApplicationFactory;
import org.apache.wicket.protocol.http.WebApplication;
import org.apache.wicket.protocol.http.WicketFilter;

import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class DefaultWicketFilter extends WicketFilter {

	private final AbstractWicketConfig wicketConfig;
	
	@Inject
	public DefaultWicketFilter(AbstractWicketConfig wicketConfig) {
		this.wicketConfig = wicketConfig;
		setFilterPath("");
	}
	
	@Override
	protected IWebApplicationFactory getApplicationFactory() {
		return new IWebApplicationFactory() {

			public WebApplication createApplication(WicketFilter filter) {
				return wicketConfig;
			}

			public void destroy(WicketFilter filter) {
				
			}
		};
	}

}