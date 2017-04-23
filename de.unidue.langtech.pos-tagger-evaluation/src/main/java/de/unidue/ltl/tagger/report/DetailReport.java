package de.unidue.ltl.tagger.report;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.dkpro.lab.engine.TaskContext;
import org.dkpro.lab.reporting.BatchReportBase;
import org.dkpro.lab.storage.StorageService;
import org.dkpro.lab.task.TaskContextMetadata;

import de.unidue.ltl.tagger.components.Keys;
import de.unidue.ltl.tagger.task.RunTaggerTask;

public class DetailReport
    extends BatchReportBase
    implements Keys
{
    @Override
    public void execute()
        throws Exception
    {

        for (boolean b : new boolean[] { true, false }) {

            TaskContext context = getContext();
            TaskContextMetadata[] subtasks = getSubtasks();
            List<String> sortedCorporaNames = ReportUtils.getCorporaNames(context, subtasks);
            List<List<Object[]>> results = getResults(sortedCorporaNames, b);

            String summary = makeSummary(sortedCorporaNames, results);
            writeSummary(summary, b);
        }
    }

    private void writeSummary(String summary, boolean b)
        throws Exception
    {
        StorageService storageService = getContext().getStorageService();
        File cf = storageService.locateKey(getContext().getId(), (b ? "coarse_" : "fine_")
                + "detail.csv");
        FileUtils.write(cf, summary);
    }

    private String makeSummary(List<String> corporaNames, List<List<Object[]>> results)
    {
        StringBuilder sb = new StringBuilder();

        // corpora names
        sb.append(";");
        for (int i = 0; i < corporaNames.size(); i++) {
            sb.append(corporaNames.get(i));
            sb.append(";;");
        }
        sb.append("\n");

        // accuracy / time column names
        sb.append(";");
        for (int i = 0; i < corporaNames.size(); i++) {
            sb.append("accuracy;time;");
        }
        sb.append("\n");

        List<Object[]> entry = results.get(0);

        for (int j = 0; j < entry.size(); j++) {
            for (int k = 0; k < results.size(); k++) {
                List<Object[]> list = results.get(k);
                Object[] objects = list.get(j);

                if (k == 0) {
                    sb.append(objects[0] + ";");
                }
                Double acc = (Double) objects[1];
                Double time = (Double) objects[2];

                String acc_string = ReportUtils.germanizeFormat(acc);
                String time_string = ReportUtils.germanizeFormat(time);

                sb.append(acc_string + ";" + time_string + ";");
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    private List<List<Object[]>> getResults(List<String> corporaNames, boolean useCoarse)
        throws Exception
    {
        List<List<Object[]>> results = new ArrayList<>();

        for (String corpus : corporaNames) {
            List<Object[]> r = getResultsForCorpus(corpus, useCoarse);
            Collections.sort(r, new Comparator<Object[]>()
            {

                @Override
                public int compare(Object[] o1, Object[] o2)
                {
                    String n1 = (String) o1[0];
                    String n2 = (String) o2[0];
                    return n1.compareTo(n2);
                }
            });
            results.add(r);
        }
        return results;
    }

    private List<Object[]> getResultsForCorpus(String targetCorpus, boolean loadCoarse)
        throws Exception
    {
        List<Object[]> results = new ArrayList<Object[]>();

        StorageService storageService = getContext().getStorageService();
        for (TaskContextMetadata subcontext : getSubtasks()) {
            if (subcontext.getType().contains(RunTaggerTask.class.getSimpleName())) {

                String corpus = ReportUtils.getCorpusName(storageService, subcontext.getId());

                if (!corpus.equals(targetCorpus)) {
                    continue;
                }

                String taggerModel = ReportUtils.getTaggerModel(storageService, subcontext.getId());
                Double acc = ReportUtils
                        .getAccuracy(storageService, subcontext.getId(), loadCoarse);
                Double normTime = ReportUtils.getNormalizedTime(storageService, subcontext.getId());

                results.add(new Object[] { taggerModel, acc, normTime });
            }
        }

        return results;
    }

}
