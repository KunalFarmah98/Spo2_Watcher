# SPO2 Watcher - A  PPG based Oximeter and Heart Rate Sensor

## IMPORTANT NOTE:
## # This is a prototype application that is itself forked from an open source project using Photoplethysmography to support a research Paper.
## # This application can not detect or prevent any health conditions related to vital signs.
## # This application MUST NOT be used as a substitute for medical devices for measurement of vital signs or for detecting any health conditions.

## Part of Research Paper titled: "A Novel Patient Monitoring System in the age of Covid-19 using Photoplethysmography and IOT" accepted and published in ICCMC 2021 and available in IEEE Explore. https://ieeexplore.ieee.org/document/9418426
#

#### Nowadays healthcare has become an important factor for a developing country to keep up with the pther countries.
This app brings the feature fo testing Vital Signs namely, Blood Pressure, Heart Rate, Oxygen Saturation as well as Respiration Rate to a simple android application in which just u need is to put ur index finger on the camera and its done using Photoplethysmography.

#### In the age of Covid-19, remote monitoring of patients is the need of the hour as there is a huge load on the healthcare system and the government.

#### Photoplethysmography (PPG) is a simple and low-cost optical technique that can be used to detect blood volume changes in the microvascular bed of tissue. It is often used non-invasively to make measurements at the skin surface.

* This Android application uses this concept to measure the vital signs of the body namely Heart Rate and Oxygen Saturation with the help of the Camera and Flash.

* It shoots a video of 30 seconds, then analyses it frame by frame to match the perfect orientation of the images and selects the best image 
and calculates the required results using Image Processing, Bessel function and Fast Fourier Transform and much more complex functions.

* Looks for a certain threshold of RGB light intensities, and straightawy gives error asking user to try again, hence reducing chances of mis calculation of vitals and giving false alarms.

* Has a dual login feature: Allowing both users and doctors to interact with each other.

<p align = "center">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/login.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/nav.jpeg" width = "190" height = "320">
  </p>
  
#### Can connect to an external MAX30100 Pulse oximeter sensor connected to a node mcu module and receive its readings OTA.
#### User keeps his finger on the sensor for 20 seconds and the sensor sends data to the cloud (Firebase Realtime Database) and the app listens for the readings
#### Cost of the complete sensor module is less than Rs.500 with equal accuracy of the market sesnors sold over Rs.1200 with an additional app that is free being itself capable of detecting abnormal vital signs and hence preventing spread of the pandemic to some extent.

#### A user can:
1. Measure his Vital Signs via PPG or via external MAX30100 Pulse Oximter Sensor Connected to GSM enabled Node MCU Module.
2. Register emergency contacts that woudl be auto notified SMS if Spo2 comes out less than 93%.
3. Track his vitals on the dashboard via line charts.
4. Consult a registerd doctor : Book an appointment and chat with doctors free of cost.
5. Learn about the vital signs and remedies for certain conditions.
6. Share the details with the doctor .
7. Get warned by the app if Spo2 goes below 93%.
8. Download their vitals report form the beginning of use.
9. Remotely monitor health of their loved ones in isolation without any need of physical contact by loging in with their ids to check their vitals report.
10. Emergency contacts get notified via SMS if Spo2 stays below 93% consistently.

<p align = "center">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/dashboard.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/measure_options.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/ppg.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/iot.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/result.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/alert.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/appointment.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/chat.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/help.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/reports.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/doctors.jpeg" width = "190" height = "320">


</p>

#### A doctor can:
1. Manage his apointments.
2. Chat with his patients
3. Keep track of his/her patients vitals using the app itself.
4. Remotely monitor all his/her patients

<p align = "center">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/doc_appointment.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/doc_chat.jpeg" width = "190" height = "320">
  <img hspace= "20" src="https://github.com/KunalFarmah98/Spo2_Watcher/blob/master/app/src/main/res/raw/doc_patients.jpeg" width = "190" height = "320">
</p>

## NOTE:
#### This was a college project as part of a research paper.
#### This project is no longer maintained.
#### The database and authentication servers have been shut down.
#### All accounts and their data has been securely deleted.
