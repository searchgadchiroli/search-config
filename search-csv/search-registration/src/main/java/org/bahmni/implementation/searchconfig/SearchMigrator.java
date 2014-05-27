package org.bahmni.implementation.searchconfig;

import org.apache.log4j.Logger;
import org.bahmni.csv.CSVFile;
import org.bahmni.csv.MigrateResult;
import org.bahmni.csv.MigratorBuilder;
import org.bahmni.csv.MultiStageMigrator;
import org.bahmni.csv.exception.MigrationException;

public class SearchMigrator {

    private Logger logger = Logger.getLogger(SearchMigrator.class.getName());

    public static void main(String[] args){
        if(args.length < 2) {
            System.out.println("Incorrect command usage.");
            System.out.println(String.format("Usage %s [validate|migrate] reg-csv-file-path", SearchMigrator.class.getName()));
            System.exit(1);
        }

        String csvParentFolderPath = args[2];
        String csvFileName = args[3];
        new SearchMigrator().process(csvParentFolderPath, csvFileName);
    }


    public void process(String csvParentFolderPath, String csvFileName){
        CSVFile<SearchCSVRow> registrationCSVFile = new CSVFile<SearchCSVRow>(csvParentFolderPath, csvFileName, SearchCSVRow.class);

        MultiStageMigrator multiStageMigrator = new MultiStageMigrator<SearchCSVRow>();
        multiStageMigrator.addStage(new SearchValidatorStage());
        multiStageMigrator.migrate(registrationCSVFile, SearchCSVRow.class);
    }

}