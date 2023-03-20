// Copyright 2018 HP Development Company, L.P.
// SPDX-License-Identifier: MIT
package com.dcpl.printfromonbase;

import com.hp.workpath.api.Result;
import com.hp.workpath.api.job.JobInfo;
import com.hp.workpath.api.job.PrintJobData;
import com.hp.workpath.api.job.PrintJobState;
import com.hp.workpath.api.printer.PrintAttributes;
import com.hp.workpath.api.printer.PrintAttributesCaps;
import com.hp.workpath.api.printer.PrintAttributesReader;
import com.hp.workpath.api.printer.StatusInfo;
import com.hp.workpath.api.printer.TrayInfo;

public class Logger {

    public static final boolean _DEBUG = BuildConfig.DEBUG;

    private static final String _START = "[";
    private static final String _END = "]";
    private static final String _START_SUB = "{";
    private static final String _END_SUB = "}";
    private static final String _C = ",";
    private static final String _EQ = "=";
    private static final String _NF = (BuildConfig.DEBUG) ? "\n" : "";

    public static String build(PrintAttributesCaps caps) {
        if (caps != null) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(_START)
                    .append(_NF).append("autoFit").append(_EQ).append(caps.getAutoFitList()).append(_C)
                    .append(_NF).append("collateMode").append(_EQ).append(caps.getCollateModeList()).append(_C)
                    .append(_NF).append("colorMode").append(_EQ).append(caps.getColorModeList()).append(_C)
                    .append(_NF).append("documentFormat").append(_EQ).append(caps.getDocumentFormatList()).append(_C)
                    .append(_NF).append("duplex").append(_EQ).append(caps.getDuplexList()).append(_C)
                    .append(_NF).append("maxCopies").append(_EQ).append(caps.getMaxCopies()).append(_C)
                    .append(_NF).append("paperSize").append(_EQ).append(caps.getPaperSizeList()).append(_C)
                    .append(_NF).append("paperSource").append(_EQ).append(caps.getPaperSourceList()).append(_C)
                    .append(_NF).append("paperType").append(_EQ).append(caps.getPaperTypeList()).append(_C)
                    .append(_NF).append("stapleMode").append(_EQ).append(caps.getStapleModeList())
                    .append(_NF).append(_END);
            return logBuilder.toString();
        }
        return null;
    }

    public static String build(PrintAttributes attributes) {
        if (attributes != null) {
            PrintAttributesReader reader = new PrintAttributesReader(attributes);
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(_START)
                    .append(_NF).append("autoFit").append(_EQ).append(reader.getAutoFit()).append(_C)
                    .append(_NF).append("collateMode").append(_EQ).append(reader.getCollateMode()).append(_C)
                    .append(_NF).append("colorMode").append(_EQ).append(reader.getColorMode()).append(_C)
                    .append(_NF).append("copies").append(_EQ).append(reader.getCopies()).append(_C)
                    .append(_NF).append("documentFormat").append(_EQ).append(reader.getDocumentFormat()).append(_C)
                    .append(_NF).append("duplex").append(_EQ).append(reader.getPlex()).append(_C)
                    .append(_NF).append("jobName").append(_EQ).append(reader.getJobName()).append(_C)
                    .append(_NF).append("paperSize").append(_EQ).append(reader.getPaperSize()).append(_C)
                    .append(_NF).append("paperSource").append(_EQ).append(reader.getPaperSource()).append(_C)
                    .append(_NF).append("paperType").append(_EQ).append(reader.getPaperType()).append(_C)
                    .append(_NF).append("stapleMode").append(_EQ).append(reader.getStapleMode())
                    .append(_NF).append(_END);
            return logBuilder.toString();
        }
        return null;
    }

    public static String build(StatusInfo statusInfo) {
        if (statusInfo != null) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(_START)
                    .append(_NF).append("status").append(_EQ).append(statusInfo.getStatus()).append(_C)
                    .append(_NF).append("statusReasons").append(_EQ).append(statusInfo.getStatusReasons())
                    .append(_NF).append(_END);
            return logBuilder.toString();
        }
        return null;
    }

    public static String build(TrayInfo trayInfo) {
        if (trayInfo != null) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(_START);
            if (trayInfo.getStatus() == TrayInfo.Status.AVAILABLE) {
                logBuilder.append("paperSource").append(_EQ).append(trayInfo.getPaperSource()).append(_C)
                        .append(_NF).append("status").append(_EQ).append(trayInfo.getStatus()).append(_C)
                        .append(_NF).append("level").append(_EQ).append(trayInfo.getLevel()).append(_C)
                        .append(_NF).append("cap").append(_EQ).append(trayInfo.getCapacity()).append(_C)
                        .append(_NF).append("paperSize").append(_EQ).append(trayInfo.getPaperSize()).append(_C)
                        .append(_NF).append("paperType").append(_EQ).append(trayInfo.getPaperType()).append(_END);
            } else {
                logBuilder.append("paperSource").append(_EQ).append(trayInfo.getPaperSource()).append(_C)
                        .append("status").append(_EQ).append(trayInfo.getStatus()).append(_END);
            }
            return logBuilder.toString();
        }
        return null;
    }

    public static String build(JobInfo jobInfo) {
        if (jobInfo != null) {
            StringBuilder logBuilder = new StringBuilder();
            logBuilder.append(_START)
                    .append(_NF).append("jobId").append(_EQ).append(jobInfo.getJobId()).append(_C)
                    .append(_NF).append("jobName").append(_EQ).append(jobInfo.getJobName()).append(_C)
                    .append(_NF).append("jobType").append(_EQ).append(jobInfo.getJobType()).append(_C)
                    .append(_NF).append("owner").append(_EQ).append(jobInfo.getOwner()).append(_C)
                    .append(_NF).append("startTime").append(_EQ).append(jobInfo.getStartTime()).append(_C)
                    .append(_NF).append("completeTime").append(_EQ).append(jobInfo.getCompleteTime()).append(_C);
            if (jobInfo.getJobType() == JobInfo.JobType.PRINT) {
                PrintJobData printJobData = jobInfo.getJobData();
                if (printJobData != null) {
                    logBuilder.append(_NF).append("jobData").append(_EQ).append(_START_SUB);
                    PrintJobState printJobState = printJobData.getJobState();
                    if (printJobState != null) {
                        logBuilder.append("jobState").append(_EQ).append(_START_SUB)
                                .append("state").append(_EQ).append(printJobState.getState()).append(_END_SUB);
                    }
                    logBuilder.append(_C)
                            .append("sheetsPrinted").append(_EQ).append(printJobData.getSheetsPrinted()).append(_C)
                            .append("impressionsPrinted").append(_EQ).append(printJobData.getImpressionsPrinted()).append(_C)
                            .append("copies").append(_EQ).append(printJobData.getCopies()).append(_C)
                            .append("duplex").append(_EQ).append(printJobData.getDuplex()).append(_C)
                            .append("source").append(_EQ).append(printJobData.getSource()).append(_END_SUB);
                }
            }
            logBuilder.append(_NF).append(_END);
            return logBuilder.toString();
        }
        return null;
    }

    public static String build(Result result) {
        return new StringBuilder()
                .append(_START)
                .append(_NF).append("Code:").append(result.getCode()).append(_C)
                .append(_NF).append("ErrorCode:").append(result.getErrorCode()).append(_C)
                .append(_NF).append("Cause:").append(result.getCause())
                .append(_NF).append(_END)
                .toString();
    }
}
