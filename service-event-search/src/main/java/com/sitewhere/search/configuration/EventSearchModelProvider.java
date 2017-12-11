/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.search.configuration;

import com.sitewhere.configuration.model.ConfigurationModelProvider;
import com.sitewhere.configuration.old.ISearchProvidersParser;
import com.sitewhere.configuration.old.ITenantConfigurationParser;
import com.sitewhere.rest.model.configuration.AttributeNode;
import com.sitewhere.rest.model.configuration.ElementNode;
import com.sitewhere.spi.microservice.configuration.model.AttributeType;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

/**
 * Configuration model provider for event search microservice.
 * 
 * @author Derek
 */
public class EventSearchModelProvider extends ConfigurationModelProvider {

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getDefaultXmlNamespace()
     */
    @Override
    public String getDefaultXmlNamespace() {
	return "http://sitewhere.io/schema/sitewhere/microservice/event-search";
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getRootRole()
     */
    @Override
    public IConfigurationRoleProvider getRootRole() {
	return EventSearchRoles.EventSearch;
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeElements()
     */
    @Override
    public void initializeElements() {
	addElement(createSearchProviders());
	addElement(createSolrSearchProvider());
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeRoles()
     */
    @Override
    public void initializeRoles() {
	for (EventSearchRoles role : EventSearchRoles.values()) {
	    getRolesById().put(role.getRole().getKey().getId(), role.getRole());
	}
    }

    /**
     * Create the container for asset management configuration.
     * 
     * @return
     */
    protected ElementNode createSearchProviders() {
	ElementNode.Builder builder = new ElementNode.Builder("Search Providers",
		ITenantConfigurationParser.Elements.SearchProviders.getLocalName(), "search",
		EventSearchRoleKeys.SearchProviders);
	builder.description("Configure search providers.");
	return builder.build();
    }

    /**
     * Create element configuration for Solr search provider.
     * 
     * @return
     */
    protected ElementNode createSolrSearchProvider() {
	ElementNode.Builder builder = new ElementNode.Builder("Solr Search Provider",
		ISearchProvidersParser.Elements.SolrSearchProvider.getLocalName(), "search",
		EventSearchRoleKeys.SearchProvider);

	builder.description("Provider that delegates search tasks to a linked Solr instance.");
	builder.attribute((new AttributeNode.Builder("Id", "id", AttributeType.String)
		.description("Unique id for search provider.").defaultValue("solr").makeIndex().build()));
	builder.attribute((new AttributeNode.Builder("Name", "name", AttributeType.String)
		.description("Name shown for search provider.").defaultValue(" Apache Solr").build()));

	return builder.build();
    }
}