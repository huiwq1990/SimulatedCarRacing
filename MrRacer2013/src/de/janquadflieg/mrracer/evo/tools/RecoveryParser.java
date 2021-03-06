/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package de.janquadflieg.mrracer.evo.tools;

import java.util.*;
import java.text.*;

import de.janquadflieg.mrracer.behaviour.*;
import de.janquadflieg.mrracer.controller.*;
import de.janquadflieg.mrracer.evo.Individual2011;

/**
 *
 * @author quad
 */
public class RecoveryParser 
implements IndividualParser{
    public Individual2011 parse(String line, String track, java.util.HashMap<String, Integer> header) {
        Individual2011 result = new Individual2011();
        result.properties = new Properties();

        Properties params = result.properties;

        DecimalFormat nf = new DecimalFormat();
        DecimalFormatSymbols symbols = nf.getDecimalFormatSymbols();
        symbols.setDecimalSeparator(',');
        symbols.setGroupingSeparator('.');
        nf.setDecimalFormatSymbols(symbols);

        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        // number
        String token = tokenizer.nextToken();
        result.indNR = Integer.parseInt(token);
        result.indID = result.indNR + 1;
        // generation no
        tokenizer.nextToken();
        // fitness value
        String sfitness = tokenizer.nextToken();
        try {
            result.fitness.put(track, nf.parse(sfitness).doubleValue());

        } catch (ParseException e) {
            e.printStackTrace(System.out);
        }
        // mutation strength
        tokenizer.nextToken();

        try {
            // parameters for the planning module
            //token = tokenizer.nextToken();
            //params.setProperty(MrRacer2011.PLAN + Plan2011.TARGET_SPEEDS + GeneralisedLogisticFunction.GROWTH_RATE_B,
            //        String.valueOf(Math.pow(10, nf.parse(token).doubleValue())));

            // offtrack forward
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.F_ANGLE,
                    String.valueOf(nf.parse(token).doubleValue() * 90.0));
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.F_MIN_ACC,
                    String.valueOf(nf.parse(token).doubleValue()));
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.F_MAX_ACC,
                    String.valueOf(nf.parse(token).doubleValue()));

            // offtrack backward
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.B_ANGLE,
                    String.valueOf(nf.parse(token).doubleValue() * -90.0));
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.B_MIN_ACC,
                    String.valueOf(nf.parse(token).doubleValue()));
            token = tokenizer.nextToken();
            params.setProperty(MrRacer2012.RECOVERY + DefensiveFallbackBehaviour.OFF_TRACK + OffTrackRecoveryBehaviour.B_MAX_ACC,
                    String.valueOf(nf.parse(token).doubleValue()));

        } catch (Exception e) {
            e.printStackTrace(System.out);
        }

        return result;
    }

}
