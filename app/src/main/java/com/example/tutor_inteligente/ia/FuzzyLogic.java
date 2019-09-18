package com.example.tutor_inteligente.ia;


import android.content.Context;
import android.util.Log;

import androidx.fragment.app.FragmentManager;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunction;
import net.sourceforge.jFuzzyLogic.membership.MembershipFunctionPieceWiseLinear;
import net.sourceforge.jFuzzyLogic.membership.Value;
import net.sourceforge.jFuzzyLogic.plot.JFuzzyChart;
import net.sourceforge.jFuzzyLogic.rule.LinguisticTerm;
import net.sourceforge.jFuzzyLogic.rule.Rule;
import net.sourceforge.jFuzzyLogic.rule.RuleBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class FuzzyLogic {

    private FIS fis;
    private String fileName = "FuzzyRules";

    public FuzzyLogic(Context context) {
        try {
            fis = FIS.load(context.getAssets().open(fileName + ".fcl"), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setInuputVariables(List<String> name, List<Integer> vars) {
        if (vars.size() == name.size()) {
            for (int i = 0; i < vars.size(); i++) {
                fis.setVariable(name.get(i), vars.get(i));
            }
        }
    }

    public void evaluate() {
        fis.evaluate();
    }

    public List<Double> getOutVariables() {
        List<Double> var = new ArrayList<>();
        var.add(fis.getVariable("numeroNivel").getValue());
        var.add(fis.getVariable("retroalimentacion").getValue());
        return var;
    }

    public Variable getOutVariable(String name) {
        return fis.getVariable(name);
    }

    public List getRules() {
        List list = new ArrayList();
        for (Rule r : fis.getFunctionBlock(fileName).getFuzzyRuleBlock("No1").getRules()) {
            list.add(r);
        }
        return list;
    }

    public void getGraphs(FragmentManager supportFragmentManager) {
        List<Variable> fuzzyVars = getVars(fis.getFunctionBlock(fileName).getVariables());
        //List<LinguisticTerm> keyList = Collections.list(Collections.enumeration(prueba2.keySet()));         
        List<LinguisticTerm> valueLinguisticTerm = Collections.list(Collections.enumeration(fuzzyVars.get(0).getLinguisticTerms().values()));

        //Obtencion de categorias
        List<List<String>> vars = new ArrayList<>();
        for (int k = 0; k < valueLinguisticTerm.size(); k++) {
            vars.add(new ArrayList<>());
            vars.get(k).add(valueLinguisticTerm.get(k).getTermName());
        }

        //Obtencion de valores por categoria
        for (int j = 0; j < valueLinguisticTerm.size(); j++) {
            MembershipFunction valuesMember = valueLinguisticTerm.get(j).getMembershipFunction();
            for (int i = 0; i < valuesMember.getParametersLength(); i++) {
                vars.get(j).add(String.valueOf(valuesMember.getParameter(i)));
            }
        }
        InstructionDialogFragment dialog = new InstructionDialogFragment();
        dialog.show(supportFragmentManager, "");
        while (!dialog.status) {

        }
        dialog.setGraphs(vars);
    }

    private List<Variable> getVars(HashMap<String, Variable> vars) {
        String clave;
        List<Variable> varList = new ArrayList();

        Iterator<String> productos = vars.keySet().iterator();
        while (productos.hasNext()) {
            clave = productos.next();
            varList.add(vars.get(clave));
        }
        return varList;
    }

}