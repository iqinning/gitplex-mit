package com.gitplex.server.rest.jersey;

import org.glassfish.jersey.server.ResourceConfig;

public interface JerseyConfigurator {
	void configure(ResourceConfig resourceConfig);
}
