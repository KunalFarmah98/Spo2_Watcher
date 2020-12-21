package com.apps.kunalfarmah.Spo2Watcher.activity;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.apps.kunalfarmah.Spo2Watcher.model.Info;
import com.apps.kunalfarmah.Spo2Watcher.R;
import com.apps.kunalfarmah.Spo2Watcher.adapter.InfoAdapter;

import java.util.ArrayList;


public class InfoActivity extends AppCompatActivity {

    ListView lv;
    InfoAdapter mAdapter;
    TextView ttl;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info);

        String title = getIntent().getStringExtra("title");


        lv = findViewById(R.id.info);
        ttl = findViewById(R.id.title);
        ttl.setText(title);

        ArrayList<Info> data=new ArrayList<>();

        switch (title){
            case "Tachypnea": data = getTachypneaInfo();
            break;
            case "Bradypnea": data = getBradypneaInfo();
            break;
            case "Hyperoxia": data = getHyperoxiaInfo();
        }


        mAdapter = new InfoAdapter(this,data);

        lv.setAdapter(mAdapter);

    }

    ArrayList<Info> getTachypneaInfo(){
        ArrayList<Info> data = new ArrayList<>();

        data.add(new Info("","Tachypnea is defined as an elevated respiratory rate, or more simply, breathing that is more rapid than normal. A normal respiratory rate can vary depending on age and activity but is usually between 12 and 20 breaths per minute for a resting adult. In contrast to the term hyperpnea which refers to rapid deep breathing, tachypnea refers to rapid, shallow breathing. Let's look at the potential causes of tachypnea, " +
                "as well as medical conditions in which it may occur."));

        data.add(new Info("Conditions That May Result in Tachypnea:\n",
                "A wide range of medical conditions can result in tachypnea. By categories these may include:\n" +
                        "\n" +
                        "\n1. Lung related:\n\nLung diseases that result in a low level of oxygen or an elevated level of carbon dioxide in the body may include chronic obstructive pulmonary disease (COPD), asthma, pneumonia, pulmonary fibrosis, a pneumothorax (collapsed lung) or a pulmonary embolism, among others. The rapid breathing rate is the body's way of trying to increase oxygen or lower carbon dioxide levels in the blood. \n" +
                        "\n2. Heart-related:\n\nConditions such as heart failure, anemia, or a low thyroid can result in cardiovascular changes which in turn cause tachypnea.\n" +
                        "\n3. Hyperventilation:\n\nThis may occur due to pain, anxiety, or other conditions.\n" +
                        "\n4. Metabolic acidosis:\n\nWhen the acid level is too high in the blood, breathing rate increases to blow off carbon dioxide. Some causes of this include diabetic ketoacidosis, lactic acidosis, and hepatic encephalopathy.\n" +
                        "\n5. Central nervous system-related:\n\nTachypnea may be directly caused by brain abnormalities such as brain tumors.\n"
        ));

        data.add(new Info("Medications:\n"
                ,"Drugs such as aspirin, stimulants, and marijuana can cause a rapid shallow breathing rate."));

        data.add(new Info("Symptoms:\n",
                "Tachypnea may be accompanied by the sensation of shortness of breath and an inability to get enough air (dyspnea), " +
                        "blue-tinged fingers and lips (cyanosis) and sucking in of the chest muscles with breathing (retracting). "));

        data.add(new Info("Diagnosis:\n",
                "The diagnosis of tachypnea will vary depending on a person's age, other medical problems, current medications, and other symptoms, but may include:\n" +
                        "\n" +
                        "\n1. Oximetry:\n\nA \"clip\" may be placed on your finger to estimate the amount of oxygen in your blood.\n" +
                        "\n2. Arterial blood gases (ABGs):\n\nBlood gases can give a more accurate estimate of your oxygen level, carbon dioxide content of your blood and blood pH.\n" +
                        "\n3. Chest x-ray:\n\nA chest x-ray can quickly determine some causes of tachypnea, such as a collapsed lung.\n" +
                        "\n4. Chest CT:\n\nA chest CT may be done to look for lung diseases or tumors.\n" +
                        "\n5. Pulmonary function tests:\n\nPulmonary function tests are very helpful when looking for conditions such as COPD and asthma.\n" +
                        "\n6. Glucose:\n\nA blood sugar is often done to rule out (or confirm) diabetic ketoacidosis.\n" +
                        "\n7. Electrolytes:\n\nSodium and potassium levels are helpful in evaluating some of the causes of tachypnea.\n" +
                        "\n8. Hemoglobin:\n\nA complete blood count and hemoglobin may be done to look for evidence of anemia as well as infections.\n" +
                        "\n9. EKG:\n\nAn EKG can look for evidence of a heart attack or abnormal heart rhythms.\n" +
                        "\n10. VQ scan:\n\nA VQ scan is often done if there is a possibility of a pulmonary embolus.\n" +
                        "\n11. Brain MRI:\n\nIf no obvious causes of tachypnea are found, a brain MRI may be helpful in ruling out brain abnormalities (such as tumors) as a cause.\n" +
                        "\n12. Toxicology screen:\n\nThere are many drugs, both prescription, over-the-counter, and illegal that can cause tachypnea. A toxicology screen is often done in the emergency settings if the cause of tachypnea is unknown.\n"
        ));

        data.add(new Info("Treatments:\n",
                "The treatment of tachypnea depends primarily on determining and correcting the underlying cause.\n" +
                        "Consult your doctor_logo for a better understanding"));
        return data;


    }

    ArrayList<Info> getBradypneaInfo(){
        ArrayList<Info>data=new ArrayList<>() ;

        data.add(new Info("","Bradypnea is an abnormally slow breathing rate.\n" +
                "\n" +
                "The normal breathing rate for an adult is typically between 12 and 20 breaths per minute. A respiration rate below 12 or over 25 breaths " +
                "per minute while resting may signal an underlying health problem."));

        data.add(new Info("Age ranges\n",
                "Age 0–1 year  ->   < 30 breaths per minute\n" +
                "Age 1–3 years  ->   < 25 breaths per minute\n" +
                "Age 3–12 years  ->   < 20 breaths per minute\n" +
                "Age 12–50 years  ->   < 12 breaths per minute\n" +
                "Age 50 and up  ->   <    13 breaths per minute"));


        data.add(new Info("Symptoms\n" ,
                "1. Dizziness\n" +
                "2. Near-fainting or fainting\n" +
                "3. Fatigue\n" +
                "4. Weakness\n" +
                "5. Chest pains\n" +
                "6. Shortness of breath\n" +
                "7. Memory impairment or confusion\n" +
                "8. Tiring easily during any physical activity"));

        data.add(new Info("Causes\n",
                "1. Degeneration of heart tissue because of aging\n" +
                "2. Damage to tissues in the heart from heart attack or heart disease\n" +
                "3. High blood pressure or hypertension\n" +
                "4. Congenital heart defect which is disorder present at birth\n" +
                "5. Heart tissue infection also known as myocarditis - Complication of heart surgery\n" +
                "6. Hypothyroidism or underactive thyroid gland\n" +
                "7. Imbalance of electrolytes which are mineral related substances needed for conducting electrical impulses\n" +
                "8. Inflammatory disease, such as lupus or rheumatic fever\n"));

        data.add(new Info("Treatment\n",
                "If urgent treatment is needed, supplemental oxygen is given to the individual. " +
                        "Treatments can range from surgery to correct dangerous intracranial pressure, " +
                        "to stays in rehabilitation facilities for addiction problems.\n" +
                        "Contact your doctor_logo for a better understanding"));

        return data;

    }

    ArrayList<Info> getHyperoxiaInfo(){
        ArrayList<Info>data = new ArrayList<>();

        data.add(new Info("",
                "Hyperoxia occurs when cells, tissues and organs are exposed to an excess supply of oxygen (O2) or higher than normal partial pressure of oxygen.\n" +
                "\n" +
                "It can be caused by breathing air or oxygen at pressures greater than normal atmospheric pressure. " +
                "This kind of hyperoxia can lead to oxygen toxicity, caused from the harmful effects of breathing molecular oxygen at elevated partial pressures.\n"
                ));

        data.add(new Info("Signs and symptoms\n",
                "Associated with hyperoxia is an increased level of reactive oxygen species (ROS), " +
                        "which are chemically reactive molecules containing oxygen. " +
                        "These oxygen containing molecules can damage lipids, proteins, " +
                        "and nucleic acids, and react with surrounding biological tissues. " +
                        "\n" + "\n" +
                "The symptoms produced from breathing high concentrations of oxygen for extended periods have been studied " +
                        "in a variety of animals." +
                        "The majority of these studies reported the occurrence of irritation, " +
                        "congestion and edema of the lungs, and even death following prolonged exposures."));

        data.add(new Info("Cause\n",
                "Oxygen supplied at greater than atmospheric pressure has been known to damage plants, animals, and aerobic bacteria such as Escherichia coli." +
                " Some studies have shown that even breathing oxygen at 21% has some damaging effects."
                +"\n\nAn additional cause of hyperoxia is related to underwater diving with breathing apparatus. \n\nUnderwater divers breath a mixture of gasses which must include oxygen, and the partial pressure of any given gas mixture will increase with depth. A mixture known as nitrox is used to reduce the risk of decompression sickness by substituting oxygen for part of the nitrogen content. Breathing nitrox can lead to " +
                        "hyperoxia due to the high partial pressure of oxygen if used too deep or for too long."));

        data.add(new Info("Treatment\n",
                "Oxygen supplementation is used to treat tissue hypoxia and to relieve arterial hypoxemia. High concentrations of oxygen are often given to patients with chronic obstructive pulmonary disease (COPD) or acute lung injury (ALI). Supplementing oxygen is known to cause tissue damage, with toxicity increasing with the increase of oxygen concentrations and exposure pressures. " +
                        "Unfortunately, the supplementation of oxygen is necessary if an individual is not able to obtain sufficient oxygen through respiration and perfusion. To decrease the chances of hyperoxia, the therapist should use the lowest concentration of oxygen required by an individual. " +
                "At this time, there are no known alternatives to oxygen supplementation."));


        return data;



    }

}

