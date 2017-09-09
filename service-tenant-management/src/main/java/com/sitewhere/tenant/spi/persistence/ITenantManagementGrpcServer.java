package com.sitewhere.tenant.spi.persistence;

import com.sitewhere.spi.server.lifecycle.ILifecycleComponent;

/**
 * Binds to a port and listens for tenant management GRPC requests.
 * 
 * @author Derek
 */
public interface ITenantManagementGrpcServer extends ILifecycleComponent {
}