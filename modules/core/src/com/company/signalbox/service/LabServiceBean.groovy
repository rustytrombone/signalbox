package com.company.signalbox.service

import io.github.adrianulbona.hmm.Model
import io.github.adrianulbona.hmm.solver.MostProbableStateSequenceFinder
import org.slf4j.Logger
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service(LabService.NAME)
public class LabServiceBean implements LabService {
    private Logger log = LoggerFactory.getLogger(getClass())

    @Override
    void testMarkov() {
        Model<WikipediaViterbi.MedicalState, WikipediaViterbi.Symptom> model = WikipediaViterbi.INSTANCE.model;
        List<WikipediaViterbi.Symptom> symptoms = [WikipediaViterbi.Symptom.NORMAL, WikipediaViterbi.Symptom.COLD, WikipediaViterbi.Symptom.DIZZY].toList();
        List<WikipediaViterbi.MedicalState> evolution = new MostProbableStateSequenceFinder<>(model).basedOn(symptoms);

        evolution.each {
            log.info(it.toString())
        }

    }
}