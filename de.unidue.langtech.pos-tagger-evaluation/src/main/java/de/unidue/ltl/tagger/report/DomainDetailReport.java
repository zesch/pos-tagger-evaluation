package de.unidue.ltl.tagger.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.dkpro.lab.reporting.BatchReportBase;
import org.dkpro.lab.storage.StorageService;
import org.dkpro.lab.task.TaskContextMetadata;

import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class DomainDetailReport
    extends BatchReportBase
    implements Keys
{
    @Override
    public void execute()
        throws Exception
    {
        List<String> sortedDomains = getDomains();
        List<String> sortedTaggerModel = getTaggerModels();

        for (boolean b : new boolean[] { true, false }) {
            for (String domain : sortedDomains) {
                List<Object[]> taggerResult = new ArrayList<>();
                for (String tagger : sortedTaggerModel) {
                    List<Object[]> results = getResults(domain, tagger, b);
                    taggerResult.add(new Object[] { tagger, results });
                }
                String domainReport = buildReport(taggerResult);
                writeReport(domain, domainReport, b);
            }
        }
    }

    private void writeReport(String domain, String domainReport, boolean b)
        throws Exception
    {
        StorageService storageService = getContext().getStorageService();
        File cf = storageService.locateKey(getContext().getId(), (b ? "coarse_" : "fine_")
                + "detail_" + domain + ".csv");
        FileUtils.write(cf, domainReport);
    }

    private String buildReport(List<Object[]> taggerResult)
    {
        StringBuilder sb = new StringBuilder();

        sb = buildReportHeader(sb, taggerResult);
        sb = buildReportBody(sb, taggerResult);

        return sb.toString();
    }

    private StringBuilder buildReportBody(StringBuilder sb, List<Object[]> taggerResult)
    {
        for (int i = 0; i < taggerResult.size(); i++) {
            Object[] o = taggerResult.get(i);
            String taggerName = (String) o[0];
            @SuppressWarnings("unchecked")
            List<Object[]> r = (List<Object[]>) o[1];

            sb.append(taggerName + ";");

            for (int j = 0; j < r.size(); j++) {
                Object[] objects = r.get(j);
                for (int k = 1; k < objects.length; k++) {

                    Double val = (Double) objects[k];
                    String val_string = ReportUtils.germanizeFormat(val);
                    sb.append(val_string + ";");
                }
            }
            sb.append("\n");
        }

        return sb;
    }

    private StringBuilder buildReportHeader(StringBuilder sb, List<Object[]> taggerResult)
    {
        List<String> corporaNames = getNameOfCorpora(taggerResult.get(0));
        sb.append(";");
        for (String s : corporaNames) {
            sb.append(s + ";;");
        }
        sb.append("\n");

        return sb;
    }

    private List<String> getNameOfCorpora(Object[] objects)
    {
        List<String> names = new ArrayList<>();
        @SuppressWarnings("unchecked")
        List<Object[]> o = (List<Object[]>) objects[1];
        for (Object[] x : o) {
            names.add((String) x[0]);
        }
        return names;
    }

    private List<String> getTaggerModels()
        throws Exception
    {
        Set<String> tagger = new HashSet<>();
        StorageService storageService = getContext().getStorageService();
        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {
                String taggerModel = ReportUtils.getTaggerModel(storageService, subcontext.getId());
                tagger.add(taggerModel);
            }
        }

        List<String> taggerNamesSorted = new ArrayList<String>(tagger);
        Collections.sort(taggerNamesSorted);
        return taggerNamesSorted;
    }

    private List<Object[]> getResults(String targetDomain, String targetTagger, boolean loadCoarse)
        throws Exception
    {
        List<Object[]> result = new ArrayList<>();
        StorageService storageService = getContext().getStorageService();
        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {
                String corpusDomainName = ReportUtils.getCorpusDomainName(storageService,
                        subcontext.getId());
                String taggerModel = ReportUtils.getTaggerModel(storageService, subcontext.getId());
                if (!corpusDomainName.equals(targetDomain) || !taggerModel.equals(targetTagger)) {
                    continue;
                }

                String corpusName = ReportUtils.getCorpusName(storageService, subcontext.getId());
                Double accuracy = ReportUtils.getAccuracy(storageService, subcontext.getId(),
                        loadCoarse);
                Double normalizedTime = ReportUtils.getNormalizedTime(storageService,
                        subcontext.getId());

                result.add(new Object[] { corpusName, accuracy, normalizedTime });
            }
        }

        Collections.sort(result, new Comparator<Object[]>()
        {
            @Override
            public int compare(Object[] o1, Object[] o2)
            {
                return ((String) o1[0]).compareTo((String) o2[0]);
            }
        });

        return result;
    }

    private List<String> getDomains()
        throws Exception
    {
        Set<String> corporaDomainSet = new HashSet<>();
        StorageService storageService = getContext().getStorageService();
        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {

                String domain = ReportUtils.getCorpusDomainName(storageService, subcontext.getId());
                corporaDomainSet.add(domain);
            }
        }

        List<String> corporaDomainNamesSorted = new ArrayList<String>(corporaDomainSet);
        Collections.sort(corporaDomainNamesSorted);
        return corporaDomainNamesSorted;
    }

}
