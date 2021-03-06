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

package org.apache.stratos.mediator.autoscale.lbautoscale.callables;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.stratos.lb.common.conf.LoadBalancerConfiguration.ServiceConfiguration;
import org.apache.stratos.mediator.autoscale.lbautoscale.clients.CloudControllerClient;
import org.apache.stratos.mediator.autoscale.lbautoscale.context.AppDomainContext;
import org.apache.stratos.mediator.autoscale.lbautoscale.util.AutoscaleUtil;
import org.apache.stratos.mediator.autoscale.lbautoscale.util.AutoscalerTaskDSHolder;

import java.util.concurrent.Callable;

/** Performing sanity checks for each service domain, sub domain combination **/
public class AppNodeSanityCheckCallable implements Callable<Boolean> {

    private static final Log log = LogFactory.getLog(AppNodeSanityCheckCallable.class);
    private String domain;
    private String subDomain;
    private CloudControllerClient client;
    private AppDomainContext appDomainContext;
    private ServiceConfiguration serviceConfig;
    
    public AppNodeSanityCheckCallable(String domain, String subDomain, CloudControllerClient client, AppDomainContext appCtxt){
        this.domain = domain;
        this.subDomain = subDomain;
        this.client = client;
        this.appDomainContext = appCtxt;
        serviceConfig =
                AutoscalerTaskDSHolder.getInstance().getWholeLoadBalancerConfig().getServiceConfig(this.domain,
                    this.subDomain);
    }
    
    @Override
    public Boolean call() throws Exception {

        if (appDomainContext != null) {
            int currentInstances = 0;
            // we're considering both running and pending instance count
            currentInstances = appDomainContext.getInstances();

            int requiredInstances = serviceConfig.getMinAppInstances();

            // we try to maintain the minimum number of instances required
            if (currentInstances < requiredInstances) {
                log.debug("App domain Sanity check failed for " +
                    AutoscaleUtil.domainSubDomainString(domain, subDomain) +
                        " . Current instances: " +
                        currentInstances +
                        ". Required instances: " +
                        requiredInstances);

                int diff = requiredInstances - currentInstances;

                // Launch diff number of App instances
                log.debug("Launching " +
                    diff +
                    " App instances for " +AutoscaleUtil.domainSubDomainString(domain, subDomain));

                // FIXME: should we need to consider serviceConfig.getInstancesPerScaleUp()?
                AutoscaleUtil.runInstances(client, appDomainContext, this.domain, this.subDomain, diff);
            }
        }

        return true;
    }

}
