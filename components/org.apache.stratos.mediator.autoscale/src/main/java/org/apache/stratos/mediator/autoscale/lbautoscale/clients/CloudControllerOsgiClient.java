/**
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at

 *  http://www.apache.org/licenses/LICENSE-2.0

 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package org.apache.stratos.mediator.autoscale.lbautoscale.clients;

import org.apache.stratos.mediator.autoscale.lbautoscale.util.AutoscalerTaskDSHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.stratos.cloud.controller.interfaces.CloudControllerService;

/**
 * This is the client class this calls Autoscaler service.
 */
public class CloudControllerOsgiClient extends CloudControllerClient {

    private CloudControllerService cloudControllerService;

    private static final Log log = LogFactory.getLog(CloudControllerOsgiClient.class);

    // public CloudControllerStubClient(String epr) throws AxisFault {
    //
    // try {
    //
    // stub = new CloudControllerServiceStub(epr);
    // stub._getServiceClient().getOptions().setTimeOutInMilliSeconds(90000);
    //
    // } catch (AxisFault axisFault) {
    // String msg =
    // "Failed to initiate AutoscalerService client. " + axisFault.getMessage();
    // log.error(msg, axisFault);
    // throw new AxisFault(msg, axisFault);
    // }
    // }

    // public boolean init(boolean isSpi) throws Exception {
    //
    // return stub.initAutoscaler(isSpi);
    // }

    public String startInstance(String domainName, String subDomainName) throws Exception {

        return cloudControllerService.startInstance(domainName, subDomainName);
    }

    public boolean terminateInstance(String domainName, String subDomainName) throws Exception {

        return cloudControllerService.terminateInstance(domainName, subDomainName);
    }

    public boolean
        terminateLastlySpawnedInstance(String domainName, String subDomainName) throws Exception {

        return cloudControllerService.terminateLastlySpawnedInstance(domainName, subDomainName);
    }

    public int getPendingInstanceCount(String domainName, String subDomainName) throws Exception {

        return cloudControllerService.getPendingInstanceCount(domainName, subDomainName);
    }

    @Override
    public void init() {

        cloudControllerService = AutoscalerTaskDSHolder.getInstance().getCloudControllerService();
    }

}
