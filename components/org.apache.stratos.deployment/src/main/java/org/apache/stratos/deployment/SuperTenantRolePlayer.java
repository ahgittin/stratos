/*
*  Copyright (c) 2005-2011, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
*  WSO2 Inc. licenses this file to you under the Apache License,
*  Version 2.0 (the "License"); you may not use this file except
*  in compliance with the License.
*  You may obtain a copy of the License at
*
*    http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing,
* software distributed under the License is distributed on an
* "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
* KIND, either express or implied.  See the License for the
* specific language governing permissions and limitations
* under the License.
*/

package org.apache.stratos.deployment;

import org.apache.axiom.soap.RolePlayer;
import org.wso2.carbon.base.MultitenantConstants;
import org.wso2.carbon.context.CarbonContext;

import java.util.List;
import java.util.ArrayList;

public class SuperTenantRolePlayer implements RolePlayer{

    private List<String> roles;

    public SuperTenantRolePlayer() {
        this.roles = new ArrayList<String>();
        this.roles.add("supertenant");
    }

    public List getRoles() {
        return this.roles;
    }

    public boolean isUltimateDestination() {
        return (CarbonContext.getCurrentContext().getTenantId() ==
                MultitenantConstants.SUPER_TENANT_ID);
    }
}
