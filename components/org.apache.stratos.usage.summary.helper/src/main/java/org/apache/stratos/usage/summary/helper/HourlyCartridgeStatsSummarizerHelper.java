/*
 *Licensed to the Apache Software Foundation (ASF) under one
 *or more contributor license agreements.  See the NOTICE file
 *distributed with this work for additional information
 *regarding copyright ownership.  The ASF licenses this file
 *to you under the Apache License, Version 2.0 (the
 *"License"); you may not use this file except in compliance
 *with the License.  You may obtain a copy of the License at

 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *Unless required by applicable law or agreed to in writing,
 *software distributed under the License is distributed on an
 *"AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *KIND, either express or implied.  See the License for the
 *specific language governing permissions and limitations
 *under the License.
 */

package org.apache.stratos.usage.summary.helper;

import org.apache.stratos.usage.summary.helper.util.DataAccessObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.analytics.hive.extension.AbstractHiveAnalyzer;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper class to set the timestamp of the last hourly summarization cycle in hive config.
 * This is needed to excluded the already summarized usage stats in the current summarization cycle.
 */
public class HourlyCartridgeStatsSummarizerHelper extends AbstractHiveAnalyzer {

    private static Log log = LogFactory.getLog(HourlyCartridgeStatsSummarizerHelper.class);

    public void execute() {
        log.info("Running custom analyzer for Stratos cartridge stats hourly summarization.");
        try {
            String lastHourlyTimestampStr = DataAccessObject.getInstance().getAndUpdateLastCartridgeStatsHourlyTimestamp();
            Long lastHourlyTimestamp = Timestamp.valueOf(lastHourlyTimestampStr).getTime();

            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:00");
            String currentTsStr = formatter.format(new Date().getTime());

            log.info("Running hourly cartridge stats analytics from " + lastHourlyTimestampStr + " to " + currentTsStr);
            setProperty("last_hourly_ts", lastHourlyTimestamp.toString());
        } catch (Exception e) {
            log.error("An error occurred while setting hour range for hourly cartridge stats analysis. ", e);
        }
    }
}
