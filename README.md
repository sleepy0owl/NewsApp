# NewsApp
* This project is partial fulfillment of **Android Basic Nano-degree** by **Udacity**
* This app uses **Guardian API** to fetch news 
## Android Goodies
* HttpURLConnection.
* JSON parsing.
* SharedPreferences.
* InputStream.
* ArrayAdapter.
* AsyncTaskLoader.
## Description
* Displays **No Internet Connection Avaiable** if the app is not connected to internet.
* By default this app shows sports news and 10 newest article of this section.
* Each news is displayed using a **CardView**.
* Each **CardView** displays a **thumbnail**, short **description**, name of the **authors** and published **date and time**. 
* **The Guardian API** is used to fetch the news articles using **AsyncTaskLoader**.
* The API gives **JSON** as a respponse which is then **parsed** carefully to get the above information.
* **SharedPreferences** is used to get user preferences.
* Here users can search news about **topic of their** choice.
