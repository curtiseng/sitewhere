/*
 * Copyright (c) SiteWhere, LLC. All rights reserved. http://www.sitewhere.com
 *
 * The software in this package is published under the terms of the CPAL v1.0
 * license, a copy of which has been included with this distribution in the
 * LICENSE.txt file.
 */
package com.sitewhere.batch.configuration;

import com.sitewhere.configuration.model.ConfigurationModelProvider;
import com.sitewhere.configuration.old.IDeviceCommunicationParser;
import com.sitewhere.configuration.parser.IBatchOperationsParser;
import com.sitewhere.rest.model.configuration.AttributeNode;
import com.sitewhere.rest.model.configuration.ElementNode;
import com.sitewhere.spi.microservice.configuration.model.AttributeType;
import com.sitewhere.spi.microservice.configuration.model.IConfigurationRoleProvider;

/**
 * Configuration model provider for batch operations microservice.
 * 
 * @author Derek
 */
public class BatchOperationsModelProvider extends ConfigurationModelProvider {

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getDefaultXmlNamespace()
     */
    @Override
    public String getDefaultXmlNamespace() {
	return "http://sitewhere.io/schema/sitewhere/microservice/batch-operations";
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#getRootRole()
     */
    @Override
    public IConfigurationRoleProvider getRootRole() {
	return BatchOperationsRoles.BatchOperations;
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeElements()
     */
    @Override
    public void initializeElements() {
	addElement(createBatchOperationsElement());
	addElement(createBatchOperationManagerElement());
    }

    /*
     * @see com.sitewhere.spi.microservice.configuration.model.
     * IConfigurationModelProvider#initializeRoles()
     */
    @Override
    public void initializeRoles() {
	for (BatchOperationsRoles role : BatchOperationsRoles.values()) {
	    getRolesById().put(role.getRole().getKey().getId(), role.getRole());
	}
    }

    /**
     * Create element configuration for batch operations.
     * 
     * @return
     */
    protected ElementNode createBatchOperationsElement() {
	ElementNode.Builder builder = new ElementNode.Builder("Batch Operation Management",
		IDeviceCommunicationParser.Elements.BatchOperations.getLocalName(), "server",
		BatchOperationsRoleKeys.BatchOperations);

	builder.description("Manages how batch operations are processed. Batch operations are "
		+ "actions that are executed asynchronously for many devices with the ability to monitor "
		+ "progress at both the batch and element level.");
	return builder.build();
    }

    /**
     * Create element configuration for batch operation manager.
     * 
     * @return
     */
    protected ElementNode createBatchOperationManagerElement() {
	ElementNode.Builder builder = new ElementNode.Builder("Batch Operation Manager",
		IBatchOperationsParser.Elements.DefaultBatchOperationManager.getLocalName(), "server",
		BatchOperationsRoleKeys.BatchOperationManager);

	builder.description("Manages how batch operations are processed.");
	builder.attribute((new AttributeNode.Builder("Throttle delay (ms)", "throttleDelayMs", AttributeType.Integer)
		.description("Number of milliseconds to wait between processing elements in a "
			+ "batch operation. This throttles the output to prevent overloading the system.")
		.defaultValue("0").build()));
	return builder.build();
    }
}