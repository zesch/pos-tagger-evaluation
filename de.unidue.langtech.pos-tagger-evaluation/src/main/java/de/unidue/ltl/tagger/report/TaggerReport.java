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

import de.unidue.ltl.tagger.Keys;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class TaggerReport
    extends BatchReportBase
    implements Keys
{
    @Override
    public void execute()
        throws Exception
    {
        List<String> sortedTaggerModel = getTaggerModels();

        for (boolean b : new boolean[] { true, false }) {
            StringBuilder sb = new StringBuilder();
            sb.append("tagger;accuracy;time\n");
            
            for (String tagger : sortedTaggerModel) {
                List<Object[]> results = getResults(tagger, b);
                appendEntry(sb, tagger, results);
            }
            writeResult(sb.toString(), b);
        }
    }

    private void writeResult(String report, boolean b)
        throws Exception
    {
        StorageService storageService = getContext().getStorageService();
        File cf = storageService.locateKey(getContext().getId(), (b ? "coarse_" : "fine_")
                + "tagger.csv");
        FileUtils.write(cf, report);
    }

    private StringBuilder appendEntry(StringBuilder sb, String tagger, List<Object[]> taggerResult)
    {
        Double acc = new Double(0);
        Double time = new Double(0);

        for (Object[] e : taggerResult) {
            acc += (Double) e[1];
            time += (Double) e[2];
        }

        acc /= taggerResult.size();
        time /= taggerResult.size();

        sb.append(tagger + ";" + ReportUtils.germanizeFormat(acc) + ";"
                + ReportUtils.germanizeFormat(time) + ";");
        sb.append("\n");

        return sb;
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

    private List<Object[]> getResults(String targetTagger, boolean loadCoarse)
        throws Exception
    {
        List<Object[]> result = new ArrayList<>();
        StorageService storageService = getContext().getStorageService();
        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {
                String taggerModel = ReportUtils.getTaggerModel(storageService, subcontext.getId());
                if (!taggerModel.equals(targetTagger)) {
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

}
