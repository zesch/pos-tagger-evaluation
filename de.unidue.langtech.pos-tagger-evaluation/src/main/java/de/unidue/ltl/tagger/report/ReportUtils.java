package de.unidue.ltl.tagger.report;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.dkpro.lab.engine.TaskContext;
import org.dkpro.lab.storage.StorageService;
import org.dkpro.lab.task.TaskContextMetadata;

import de.unidue.ltl.tagger.Keys;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class ReportUtils
    implements Keys
{
    public static Double getNormalizedTime(StorageService storageService, String id)
        throws Exception
    {
        File normTimeFile = storageService.locateKey(id, FILE_NORMALIZED_TOKEN_TIME);
        Properties prop = new Properties();

        FileInputStream fis = new FileInputStream(normTimeFile);
        prop.load(fis);
        fis.close();

        String normTimeString = prop.getProperty(PROP_NORMALIZED_MIL_TOK_PER_SEC);
        Double normTime = Double.valueOf(normTimeString);
        return normTime;
    }

    public static String getTaggerModel(StorageService storageService, String id)
        throws Exception
    {
        File taggerFile = storageService.locateKey(id, FILE_TAGGER);
        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(taggerFile);
        prop.load(fis);
        fis.close();

        String taggerName = prop.getProperty(PROP_TAGGER_NAME);
        String taggerModelName = prop.getProperty(PROP_TAGGER_MODEL_NAME);

        return taggerName + (taggerModelName.isEmpty() ? "" : "-" + taggerModelName);
    }

    public static String germanizeFormat(Double value)
    {
        return value.toString().replaceAll("\\.", ",");
    }

    public static Double getAccuracy(StorageService storageService, String contextId,
            boolean loadCoarse)
        throws Exception
    {
        File accFile = null;
        if (loadCoarse) {
            accFile = storageService.locateKey(contextId, FILE_ACC_COARSE);
        }
        else {
            accFile = storageService.locateKey(contextId, FILE_ACC_FINE);
        }

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(accFile);
        prop.load(fis);
        fis.close();

        Double acc = null;
        if (loadCoarse) {
            acc = Double.valueOf(prop.getProperty(PROP_ACC_COARSE));
        }
        else {
            acc = Double.valueOf(prop.getProperty(PROP_ACC_FINE));
        }

        return acc;
    }

    public static String getCorpusName(StorageService storageService, String subcontextId)
        throws Exception
    {
        File cf = storageService.locateKey(subcontextId, FILE_CORPUS);

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(cf);
        prop.load(fis);
        fis.close();

        String corpus = prop.getProperty(PROP_CORPUS_NAME);
        return corpus;
    }

    public static String getCorpusDomainName(StorageService storageService, String id)
        throws Exception
    {
        File cf = storageService.locateKey(id, FILE_CORPUS);

        Properties prop = new Properties();
        FileInputStream fis = new FileInputStream(cf);
        prop.load(fis);
        fis.close();

        String corpusDomain = prop.getProperty(PROP_CORPUS_DOMAIN);
        return corpusDomain;
    }

    public static List<String> getCorporaNames(TaskContext context, TaskContextMetadata[] subtasks)
        throws Exception
    {
        Set<String> corporaNamesSet = new HashSet<>();
        StorageService storageService = context.getStorageService();
        for (TaskContextMetadata subcontext : subtasks) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {

                String corpus = ReportUtils.getCorpusName(storageService, subcontext.getId());
                corporaNamesSet.add(corpus);
            }
        }

        List<String> corporaNamesSorted = new ArrayList<String>(corporaNamesSet);
        Collections.sort(corporaNamesSorted);
        return corporaNamesSorted;
    }

    public static List<String[]> getCorporaNamesAndDomains(TaskContext context,
            TaskContextMetadata[] subtasks)
        throws Exception
    {
        Set<String[]> corporaNamesSet = new HashSet<>();
        StorageService storageService = context.getStorageService();
        for (TaskContextMetadata subcontext : subtasks) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {
                String corpus = ReportUtils.getCorpusName(storageService, subcontext.getId());
                String domain = ReportUtils.getCorpusDomainName(storageService, subcontext.getId());
                corporaNamesSet.add(new String[] { corpus, domain });
            }
        }

        List<String[]> corporaNamesSorted = new ArrayList<String[]>(corporaNamesSet);
        Collections.sort(corporaNamesSorted, new Comparator<String[]>()
        {

            @Override
            public int compare(String[] o1, String[] o2)
            {
                return o1[0].compareTo(o2[0]);
            }
        });
        return corporaNamesSorted;
    }

}
