package com.apps.kunalfarmah.Spo2Watcher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class RemedyActivity extends AppCompatActivity {

    ListView lv;
    RemedyAdapter madapter;
    TextView ttl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedy);

        lv = findViewById(R.id.list);

        String title = getIntent().getStringExtra("title");

        ttl = findViewById(R.id.title);
        ttl.setText(title);


        switch (title) {
            case "Hypertension":

                madapter = new RemedyAdapter(this, hypertension());
                break;

            case "Low Blood Presure":

                madapter = new RemedyAdapter(this, hypotension());
                break;

            case "High Heart Rate":
                madapter = new RemedyAdapter(this, high_hr());
                break;

            case "Low Heart Rate":
                madapter = new RemedyAdapter(this,lowHeartRAte());
                break;

            case "Oxygen Saturation":
                madapter = new RemedyAdapter(this, oxygenSaturation());
                break;

            case "High Oxygen Levels":

                Intent Info = new Intent(this, InfoActivity.class);
                Info.putExtra("title", "Hyperoxia");
                finish();
                startActivity(Info);
                break;


            case "Bradypnea":
                Intent Info1 = new Intent(this, InfoActivity.class);
                Info1.putExtra("title", "Bradypnea");
                finish();
                startActivity(Info1);
                break;


            case "Tachypnea":
                Intent Info2 = new Intent(this, InfoActivity.class);
                Info2.putExtra("title", "Tachypnea");
                finish();
                startActivity(Info2);
                break;
        }


        lv.setAdapter(madapter);


    }


    ArrayList<Remedy> hypertension() {
        ArrayList<Remedy> data = new ArrayList<>();
        data.add(new Remedy(" 1) Regular Exercise :\n",
                        "Regular exercise helps make your heart stronger and more efficient at pumping blood, which lowers the pressure in your arteries.\n" +
                        "\n" +
                        "In fact, 150 minutes of moderate exercise, such as walking, or 75 minutes of vigorous exercise, such as running, per week can help lower blood pressure and improve your heart health (3, 4).\n" +
                        "\n" +
                        "Bottom Line: Walking just 30 minutes a day can help lower your blood pressure.", R.drawable.exercise));

        data.add(new Remedy("2) Increase Sodium Intake : \n",
                "Salt intake is high around the world. In large part, this is due to processed and prepared foods.\n" +
                        "\n" +
                        "Swap out processed foods with fresh ones and try seasoning with herbs and spices, rather than salt (6).\n" +
                        "\n" +
                        "However, more recent research indicates that the relationship between sodium and high blood pressure is less clear (7, 8).\n" +
                        "\n" +
                        "Bottom Line: Most guidelines for lowering blood pressure recommend lowering sodium intake. However, that recommendation might make the most sense for people who are salt-sensitive.", R.drawable.salt));

        data.add(new Remedy("3) Reduce Drinking Alcohol : \n",
                "Drinking alcohol can raise blood pressure. In fact, alcohol is linked to 16% of high blood pressure cases around the world (12).\n" +
                        "\n" +
                        "While some research has suggested that low-to-moderate amounts of alcohol may protect the heart, those benefits may be offset by negative effects (12).\n" +
                        "\n" +
                        "Bottom Line: Drinking alcohol in any quantity may raise your blood pressure. Limit your drinking to no more than one drink a day for women, two for men.", 0));

        data.add(new Remedy("4) Have Potassium Rich Diet : ", "bananas\n" +
                "Potassium is an important mineral as it helps your body get rid of sodium.\n" +
                "\n" +
                "Focus on eating fewer processed foods and more fresh, whole foods.\n" +
                "\n" +
                "Foods that are particularly high in potassium include:\n" +
                "\n" +
                ".Vegetables, especially leafy greens, tomatoes, potatoes and sweet potatoes\n" +
                ".Fruit, including melons, bananas, avocados, oranges and apricots\n" +
                ".Dairy, such as milk and yogurt\n" +
                ".Tuna and salmon\n" +
                ".Nuts and seeds\n" +
                ".Beans\n\n" +
                "Bottom Line: Eating fresh fruits and vegetables, which are rich in potassium, can help lower blood pressure.\n" +
                "\n", R.drawable.potassiumrich));

        data.add(new Remedy(" 5) Quit Smoking :",
                "Among the many reasons to quit smoking is that the habit is a strong risk factor for heart disease.\n" +
                        "\n" +
                        "Every puff of cigarette smoke causes a slight, temporary increase in blood pressure. The chemicals in tobacco are also known to damage blood vessels.\n" +
                        "\n" +
                        "Bottom Line: There's conflicting research about smoking and high blood pressure, but what is clear is that both increase the risk of heart disease.", R.drawable.quit_smoking));

        data.add(new Remedy(" 6) Cut added sugar and refined carbs :", "There's a growing body of research showing a link between added sugar and high blood pressure (30, 31, 32).\n" +
                "\n" +
                "And it's not just sugar - all refined carbs, such as the kind found in white flour, convert rapidly to sugar in your bloodstream and may cause problems.\n" +
                "\n" +
                "Some studies have shown that low-carb diets may also help reduce blood pressure.\n" +
                "\n" +
                "Bottom Line: Refined carbs, especially sugar, may raise blood pressure. Some studies have shown that low-carb diets may help reduce your levels.", 0));

        data.add(new Remedy("7) Eat calcium-rich foods :",
                "People with low calcium intake often have high blood pressure.\n" +
                        "\n" +
                        "For most adults, the calcium recommendation is 1,000 mg per day. For women over 50 and men over 70, it's 1,200 mg per day (43).\n" +
                        "\n" +
                        "In addition to dairy, you can get calcium from collard greens and other leafy greens, beans, sardines and tofu. Here is a complete list.\n" +
                        "\n" +
                        "Bottom Line: Calcium-rich diets are linked to healthy blood pressure levels. Get calcium through dark leafy greens and tofu, as well as dairy.", R.drawable.calciumrich));


        return data;
    }

    ArrayList<Remedy> hypotension() {

        ArrayList<Remedy> data = new ArrayList<>();
        data.add(new Remedy("1. Eat more salt\n",
                "Contrary to popular advice, low-sodium diets are not good for everyone with blood pressure problems.\n" +
                        "\n" +
                        "People with low blood pressure should consider increasing their sodium intake moderately to help raise blood pressure.", R.drawable.salt));

        data.add(new Remedy("2. Avoid alcoholic beverages\n",
                "Alcohol can lower blood pressure further, so people with low blood pressure should avoid drinking excessive amounts of alcohol.", 0));

        data.add(new Remedy("3. Discuss medications with a doctor_logo\n",
                "Low blood pressure can be a side effect of a variety of medications.\n" +
                        "\n" +
                        "If symptoms of low blood pressure begin after starting a medication, a person should discuss the symptoms with their doctor_logo.", R.drawable.doctor_logo));

        data.add(new Remedy("4. Drink water\n",
                "Drinking more water can help increase blood volume, which can aleviate one of the potential causes of low blood pressure. It can also help avoid dehydration.", R.drawable.water));

        data.add(new Remedy("5. Eat small meals frequently\n",
                "Eating smaller, more frequent meals throughout the day may help with low blood pressure.\n" +
                        "\n" +
                        "This is because the smaller meals help prevent a drop in a blood pressure associated with eating larger, heavier meals.",0));

        data.add(new Remedy("6. Wear compression stockings\n",

                "Compression stockings help reduce the amount of blood that gets caught in the lower legs and feet, so shifting it elsewhere.\n" +
                        "\n" +
                        "Compression stockings are also used to help relieve pressure and pain associated with varicose veins. They are available to purchase online.",0));

        data.add(new Remedy("7. Avoid sudden position changes\n",
                "Sitting up or standing up rapidly can cause a feeling of lightheadedness, dizziness, or potential fainting in people with low blood pressure.\n" +
                        "\n" +
                        "In these cases, the heart has not pumped enough blood through the body quickly enough to account for the sudden change in position or elevation.", 0));


        return data;
    }

    ArrayList<Remedy> high_hr() {
        ArrayList<Remedy> data = new ArrayList<>();

        data.add(new Remedy("1. Perform relaxation techniques\n",
                        "Stress can have many ill effects on a person's health. It can induce palpitations or make them worse.\n" +
                        "\n" +
                        "It may help to try the following relaxation techniques:\n" +
                        "\n" +
                        "* meditation\n" +
                        "* deep breathing\n" +
                        "* journaling\n" +
                        "* yoga\n" +
                        "* spending time outdoors\n" +
                        "* exercising\n" +
                        "* taking short breaks from work or school\n",R.drawable.relax));

        data.add(new Remedy("2. Reduce or eliminate stimulant intake\n",
                "Symptoms may become noticeable after using a stimulant.\n" +
                        "\n" +
                        "The following contain stimulants:\n" +
                        "\n" +
                        "* tobacco products\n" +
                        "* illegal drugs\n" +
                        "* some cold and cough medications\n" +
                        "* caffeinated beverages such as coffee, tea, and soda\n" +
                        "* appetite suppressants\n" +
                        "* marijuana\n" +
                        "* some mental health drugs\n" +
                        "* some high blood pressure medications\n" +
                        "\n", 0));

        data.add(new Remedy("3. Stimulate the vagus nerve\n",
                "The vagus nerve connects the brain to the heart, and stimulating it can help to calm palpitations. A person can do so by:\n" +
                        "\n" +
                        "* holding the breath and pushing down, as if making a bowel movement\n" +
                        "* coughing\n" +
                        "* placing ice or a cold, damp towel on the face for a few seconds\n" +
                        "* splashing cold water on the face\n" +
                        "* chanting \"Om\"\n" +
                        "Before trying this method consult a doctor_logo, who can advise on the best technique.", 0));

        data.add(new Remedy("4. Keep electrolytes balanced\n",
                        "Avocados and bananas are high in potassium.\n" +
                        "A person can boost the number of electrolytes in their body by eating foods rich in:\n" +
                        "\n" +
                        "* sodium\n" +
                        "* potassium\n" +
                        "* calcium\n" +
                        "* magnesium\n\n" +
                        "A normal diet usually provides a sufficient source of sodium.\n" +
                        "\n" +
                        "The following foods have high potassium contents:\n" +
                        "\n" +
                        "* potatoes\n" +
                        "* bananas\n" +
                        "* avocados\n" +
                        "* spinach\n" +
                        "* Dairy products and dark, leafy greens are rich in calcium. Magnesium is also found in these vegetables, as well as in nuts and fish.\n" +
                        "\n" +
                        "It may be tempting to attain these nutrients by taking supplements. A person should consult a doctor_logo before trying any supplements, particularly if they are also taking prescription medication.\n" +
                        "\n", R.drawable.electrolyte));

        data.add(new Remedy("5. Keep hydrated\n",
                "When the body is dehydrated, the heart has to work harder to circulate blood, which can cause heart palpitations.\n" +
                        "\n" +
                        "Drink plenty of water throughout the day. \n" +
                        "A person should drink a full cup or glass of water when:\n" +
                        "\n" +
                        "* their urine is dark\n" +
                        "* they have dry mouth\n" +
                        "* they feel thirsty\n" +
                        "* they have a headache\n" +
                        "* they feel dizzy\n"
                        , R.drawable.water));

        data.add(new Remedy("6. Avoid excessive alcohol use\n",
                "Alcohol is a depressant and does not typically raise the heartrate.\n" +
                        "\n" +
                        "While drinking in moderation is not necessarily problematic, some research indicates that even one drink per day can increase the risk of developing atrial fibrillation. A palpitating heart is just one symptom of this condition.", 0));

        data.add(new Remedy("7. Exercise regularly\n",
                        "Exercise can improve overall cardiovascular health and restore the heart's natural rhythm. It can also help to reduce stress and anxiety.\n" +
                        "\n" +
                        "Beneficial exercises include:\n" +
                        "\n" +
                        "* walking\n" +
                        "* jogging\n" +
                        "* running\n" +
                        "* biking\n" +
                        "* swimming\n\n" +
                        "However, exercise may trigger palpitations in some people, and it is important to identify and avoid problematic exercises.", R.drawable.exercise));

        return data;

    }

    ArrayList<Remedy> oxygenSaturation() {
        ArrayList<Remedy> data = new ArrayList<>();

        data.add(new Remedy("How To Increase Oxygen Level In Blood\n",
                "It is not necessary to spend a lot of money or implement drastic changes in life to enhance the amount of oxygen in your blood. You need to make some simple lifestyle changes and adopt healthier habits for success in this regard.", 0));


        data.add(new Remedy("1. Exercise\n",
                "To ensure your blood is infused with oxygen, you need to work out regularly. When you work out, the cells in the body burn oxygen faster than the regular rate (1). As the carbon dioxide levels in the body increase, your brain increases the respiration rate to get more supply of oxygen. Your lung and heart perform at optimum capacity during the exercise to intake more oxygen. It has been observed that people afflicted with COPD and the resultant low oxygen saturation can enhance blood oxygen levels through exercise.", R.drawable.exercise))
        ;
        data.add(new Remedy("2. Antioxidants\n",
                "When you consume foods replete with antioxidants, your body can use oxygen in a better way (2). " +
                        "Certain vitamins, including vitamins E and C, have proven antioxidant properties. " +
                        "You may eat foods like cranberries, blueberries, " +
                        "red kidney beans and dark leafy vegetables to enhance your intake of antioxidants.", 0));

        data.add(new Remedy("3. Improvement In Posture\n",
                "You may not have thought about posture and its link with enhanced oxygen in the blood, but they are actually linked (3)!" +
                        " Standing, walking or siting in a slouched posture hinders the breathing process to an extent. " +
                        "Sitting or walking with an erect back and straightened posture enables more oxygen to be inhaled, " +
                        "and that can enter the bloodstream as well.", R.drawable.posture));

        data.add(new Remedy("4. Evading CO2 And Carbon Monoxide\n",
                "Try to avoid areas and regions where the concentration of carbon monoxide and similar gases are higher. It is hard to find such areas in urban regions though. " +
                        "Nevertheless, try to avoid roads where traffic congestion is more.", 0));

        data.add(new Remedy("5. Getting Fresh Air\n",
                "Spend more time in regions with less pollution and fresh air available. Your bloodstream then gets a large supply of oxygen. You may also plant trees and shrubs in your garden, or take a walk in the park." +
                        "Ensure the rooms in your house are properly ventilated as well.", 0));

        data.add(new Remedy("6. Eating Foods With Essential Fatty Acids\n",
                "Eating foods containing the essential fatty acids are beneficial in many ways, like -  increasing the amount of oxygen in the blood stream." +
                        " You should eat plenty of soybeans, flaxseeds and walnuts.", 0));

        data.add(new Remedy("7. Resorting To Deep Breathing\n",
                "When you want to take in more oxygen, resort to deep breathing techniques. S" +
                        "low and deep breathing methods enable more oxygen to enter your lungs and get into the bloodstream eventually.", 0));

        data.add(new Remedy("8. Quitting Drugs, Alcohol And Smoking\n",
                "By avoiding smoking and alcohol consumption, you can ensure your bloodstream is infused with the required level of oxygen. " +
                        "Needless to say, discarding such habits is beneficial to your overall health.", R.drawable.quit_smoking));

        return data;

    }

    ArrayList<Remedy> lowHeartRAte() {
        ArrayList<Remedy> data = new ArrayList<Remedy>();

        data.add(new Remedy("1. Eat a heart-healthy diet.\n",
                "If cholesterol or high blood pressure is a concern, changes to your diet can fight cardiovascular disease and improve heart function. " +
                        "Eat a variety of nutritious foods, including whole grains, organic fruits and vegetables, lean proteins and wild-caught fish. \n" +
                        "\n" +
                        "Foods rich in omega-3s are proven to lower bad cholesterol, raise good cholesterol levels and lower blood pressure. " +
                        "0.5 gram to 1 gram of omega-3 fats daily is recommended, either from fresh fish or other sources." +
                        "\n" +
                        "In addition to wild-caught tuna, mackerel and salmon, you can add omega-3s to your diet by adding:\n" +
                        "\n" +
                        "* Walnuts\n" +
                        "* Chia seeds\n" +
                        "* Flaxseeds & flaxseed oil\n" +
                        "* Hemp seeds\n" +
                        "* Egg yolks", R.drawable.heart_friendly));

        data.add(new Remedy("2. L-Carnitine.\n",
                "Take 2,000 milligrams of L-carnitine daily. " +
                        "L-carnitine supplementation has been shown to increase heart fatty acid metabolism enough to " +
                        "correct bradycardia in diabetic rats. \n" +
                        "\n" +
                        "You can get L-carnitine from certain foods including:\n" +
                        "\n" +
                        "* Grass-fed beef\n" +
                        "* Whole milk\n" +
                        "However, if you are pregnant, breastfeeding, have kidney failure," +
                        " seizures or hypothyroidism, L-carnitine is not recommended.", 0));

        data.add(new Remedy("3. Magnesium.\n",
                "Take 300 milligrams to 400 milligrams of magnesium daily to help combat anxiety, improve sleep efficiency, lower insomnia symptoms, relieve muscle aches and spasms. Additionally, a magnesium deficiency is linked to atherosclerosis, diabetes, hypertension," +
                        "arrhythmias and heart failure. ", 0));

        data.add(new Remedy("4. Acupuncture.\n",
                "For many of the conditions that can cause bradycardia, acupuncture may be helpful. This includes lupus, Lyme disease, hypothyroidism and stress. Acupuncture reduces the risk of coronary " +
                        "heart disease in people with fibromyalgia. And, it is shown to reduce stress. ", 0));

        data.add(new Remedy("5. Sleep.\n",
                "When bradycardia symptoms are caused by sleep apnea, adjusting your bedroom’s temperature and humidity levels may help. Sleeping with your head elevated and using of a snore guard may also help. " +
                        "Cognitive behavioral therapy may improve sleep quality and insomnia symptoms.", 0));

        data.add(new Remedy("6. Exercise daily.\n",
                "To improve heart health, exercising daily is a must. Many of the causes of sinus bradycardia symptoms can be helped with regular exercise. In addition to improving cardiovascular health, exercising is a great way to relieve stress and anxiety for a good night’s sleep. Exercising outdoors can improve depression " +
                        "and boost your levels of vitamin D. ", R.drawable.exercise));

        return data;
    }


}
