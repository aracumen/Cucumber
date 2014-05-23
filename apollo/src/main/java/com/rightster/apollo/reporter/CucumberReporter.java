package com.rightster.apollo.reporter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.masterthought.cucumber.ReportBuilder;

import org.apache.log4j.Logger;

import com.rightster.apollo.utils.PropertyUtils;
import com.rightster.apollo.utils.StackTraceUtils;

/**
 * Class to generate cucumber test run results report.
 * 
 * @author Pradeepta Swain
 */
public class CucumberReporter
{
    private static File          reportOutputDirectory;
    private static List<String>  jsonReportFiles;
    private static String        jsonFilesPath;
    private static ReportBuilder reportBuilder;

    private static String        buildNumber;
    private static String        buildProject;

    private static Logger        logger = Logger.getLogger(CucumberReporter.class.getName());

    public static void main(String[] args)
    {
        jsonReportFiles = new ArrayList<String>();

        try
        {
            reportOutputDirectory = new File(PropertyUtils.getProperty("apollo.reports"));
        } catch (Exception ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }

        try
        {
            jsonFilesPath = PropertyUtils.getProperty("apollo.reports.json");
        } catch (Exception ex)
        {
            logger.debug(StackTraceUtils.stackTraceToString(ex));
        }

        File folder = new File(jsonFilesPath);
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++)
        {
            if (listOfFiles[i].isFile())
            {
                jsonReportFiles.add(jsonFilesPath + File.separator + listOfFiles[i].getName());
            }
        }

        try
        {
            reportBuilder = new ReportBuilder(jsonReportFiles, reportOutputDirectory, "", buildNumber, buildProject,
                    false, false, true, false, false, "", false);
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        try
        {
            reportBuilder.generateReports();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
