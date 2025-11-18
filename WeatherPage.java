import java.io.IOException;
import java.net.Socket;

public class WeatherPage {

  private WeatherPage() {
  }

  public static void sendWeatherPage(Socket socket) throws IOException {
    String message = """
        <!DOCTYPE html>
        <html lang="en">
        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <meta http-equiv="X-UA-Compatible" content="ie=edge">
          <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
          <title>Weather App UI</title>
          <style>
            @import url("https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700;800&display=swap");
            * {
              font-family: "Arial", sans-serif;
              margin: 0;
              padding: 0;
              box-sizing: border-box;
            }
            body {
              background-color: #f4f4f4;
              height: 100vh;
              display: flex;
              justify-content: center;
              align-content: center;
              flex-wrap: wrap;
            }
            .container {
              height: 400px;
              background: #232931;
              color: #fff;
              border-radius: 25px;
              box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
              width: 800px;
            }
            .left-info {
              width: 375px;
              height: 100%;
              float: left;
              display: flex;
              border-radius: 25px;
              justify-content: center;
              background: url("https://t4.ftcdn.net/jpg/07/24/00/11/360_F_724001193_eKGDuOZwvJoLt2zYHQQuFyyf2kQh7qEP.jpg") center center/cover no-repeat;
              transform: scale(1.03) perspective(200px);
              cursor: pointer;
              box-shadow: 0 0 20px -10px rgba(0, 0, 0, 0.2);
              transition: all 0.3s ease;
            }
            .left-info:hover {
              transform: scale(1.1) perspective(700px);
            }
            .pic-gradient {
              position: absolute;
              height: 100%;
              width: 100%;
              top: 0;
              left: 0;
              background: linear-gradient(135deg, #5c6bc0 10%, #0d47a1 100%);
              border-radius: 25px;
              opacity: 0.5;
            }
            .today-info {
              position: absolute;
              display: flex;
              flex-direction: column;
              margin-top: 25px;
            }
            .today-weather {
              position: absolute;
              display: flex;
              flex-direction: column;
              align-content: flex-start;
              bottom: 20px;
              left: 20px;
            }
            .today-weather .bx {
              font-size: 4.6rem;
            }
            .weather-temp {
              font-weight: 700;
              font-size: 3.5rem;
            }
            .right-info {
              float: right;
              position: relative;
              height: 100%;
              padding-top: 25px;
              width: 400px;
            }
            .day-info {
              padding: 25px 35px;
              display: flex;
              flex-direction: column;
            }
            .day-info div:not(:last-child) {
              margin-bottom: 6px;
            }
            .day-info div .title {
              font-weight: 700;
            }
            .day-info div .value {
              float: right;
            }
            .days-list {
              display: flex;
              list-style: none;
              margin: 10px 20px;
              box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
              border-radius: 10px;
            }
            .days-list li {
              padding: 15px;
              cursor: pointer;
              display: flex;
              flex-direction: column;
              justify-content: space-between;
              align-content: center;
              border-radius: 10px;
              transition: all 0.3s ease;
              flex: 1; /* Distribute space evenly */
              align-items: center;
            }
            .days-list li .bx {
              margin-bottom: 5px;
              font-size: 2rem;
            }
            .days-list li:hover {
              transform: scale(1.1);
              background: #fff;
              color: #222831;
              box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
            }
            .days-list li .day-temp {
              margin-top: 6px;
              font-weight: 700;
            }
            .btn-container {
              padding: 20px 35px;
              display: flex;
              flex-direction: column;
              gap: 15px;
            }
            .search-box {
               display: flex;
               gap: 10px;
               width: 100%;
               margin-bottom: 20px;
            }
            .loc-input {
               flex-grow: 1;
               padding: 10px 15px;
               border-radius: 10px;
               border: none;
               outline: none;
               color: #222831;
               font-weight: 500;
            }
            .loc-button {
              outline: none;
              border: none;
              font-weight: 700;
              border-radius: 10px;
              padding: 10px 20px;
              background: #303f9f;
              color: #fff;
              cursor: pointer;
              box-shadow: 0 5px 15px rgba(0, 0, 0, 0.35);
              transition: all 0.3s ease;
            }
            .loc-button:hover {
              background: #1a237e;
            }
            .back {
              display: block;
              text-align: center;
              color: white;
              text-decoration: none;
              font-size: 0.9rem;
              opacity: 0.8;
            }
            .back:hover {
               opacity: 1;
               text-decoration: underline;
            }
          </style>
        </head>
        <body>
        <div class="container">
          <div class="left-info">
            <div class="pic-gradient"></div>
            <div class="today-info">
              <h2>Loading...</h2>
              <span>Loading...</span>
              <div>
                <i class='bx bx-current-location'></i>
                <span>Location, XX</span>
              </div>
            </div>
            <div class="today-weather">
              <i class='bx bx-sun'></i>
              <h1 class="weather-temp">--°C</h1>
              <h3>--</h3>
            </div>
          </div>
          <div class="right-info">
            <div class="day-info">
              <div><span class="title">PRECIPITATION</span><span class="value">--</span></div>
              <div><span class="title">HUMIDITY</span><span class="value">--</span></div>
              <div><span class="title">WIND SPEED</span><span class="value">--</span></div>
            </div>
            <ul class="days-list"></ul>

            <div class="btn-container">
              <div class="search-box">
                   <input type="text" class="loc-input" placeholder="Enter city...">
                   <button class="loc-button">Search</button>
              </div>
              <a href="/login" class="back">Back to Login page</a>
            </div>
          </div>
        </div>
        <script>
          const API = "41fdaa6249246781bd89d1bb3f2ee2d1";
          const locButton = document.querySelector(".loc-button");
          const locInput = document.querySelector(".loc-input");
          const todayInfo = document.querySelector(".today-info");
          const todayWeatherIcon = document.querySelector(".today-weather i");
          const todayTemp = document.querySelector(".weather-temp");
          const daysList = document.querySelector(".days-list");
          const weatherIconMap = {
            "01d": "sun", "01n": "moon", "02d": "sun", "02n": "moon",
            "03d": "cloud", "03n": "cloud", "04d": "cloud", "04n": "cloud",
            "09d": "cloud-rain", "09n": "cloud-rain", "10d": "cloud-rain", "10n": "cloud-rain",
            "11d": "cloud-lightning", "11n": "cloud-lightning", "13d": "cloud-snow", "13n": "cloud-snow",
            "50d": "water", "50n": "water"
          };
          function fetchWeatherData(location) {
            const apiUrl = "https://api.openweathermap.org/data/2.5/forecast?q=" +
                          location + "&appid=" + API + "&units=metric";
            fetch(apiUrl)
              .then(res => res.json())
              .then(data => {
                if (data.cod !== "200") {
                  alert("Location not found: " + location);
                  return;
                }
                const weather = data.list[0];
                const desc = weather.weather[0].description;
                const temp = Math.round(weather.main.temp) + "\u00B0C";
                const icon = weather.weather[0].icon;
                todayInfo.querySelector("h2").textContent =
                  new Date(weather.dt * 1000).toLocaleDateString("en", { weekday: "long" });
                todayInfo.querySelector("span").textContent =
                  new Date(weather.dt * 1000).toLocaleDateString("en", {
                    day: "numeric", month: "long", year: "numeric" });
                todayWeatherIcon.className = "bx bx-" + weatherIconMap[icon];
                todayTemp.textContent = temp;
                document.querySelector(".today-info > div > span").textContent =
                  data.city.name + ", " + data.city.country;
                document.querySelector(".today-weather > h3").textContent = desc;
                const prec = Math.round(weather.pop * 100) + " %";
                const hum = weather.main.humidity + " %";
                const wind = weather.wind.speed + " km/h";
                document.querySelector(".day-info").innerHTML =
                  "<div><span class='title'>PRECIPITATION</span><span class='value'>" + prec + "</span></div>" +
                  "<div><span class='title'>HUMIDITY</span><span class='value'>" + hum + "</span></div>" +
                  "<div><span class='title'>WIND SPEED</span><span class='value'>" + wind + "</span></div>";
                const todayDate = new Date().getDate();
                daysList.innerHTML = "";
                const dailyMap = {};
                data.list.forEach(item => {
                  const dt = new Date(item.dt_txt);
                  const dateKey = dt.getDate();
                  if (dateKey !== todayDate) {
                      if (!dailyMap[dateKey] || item.main.temp > dailyMap[dateKey].main.temp) {
                          dailyMap[dateKey] = item;
                      }
                  }
                });
                const sortedDays = Object.values(dailyMap).slice(0, 5);
                sortedDays.forEach(item => {
                  const dt = new Date(item.dt_txt);
                  const day = dt.toLocaleDateString("en", { weekday: "short" });
                  const temp2 = Math.round(item.main.temp) + "\u00B0C";
                  const iCode = item.weather[0].icon;
                  daysList.innerHTML +=
                    "<li>" +
                      "<i class='bx bx-" + weatherIconMap[iCode] + "'></i>" +
                      "<span>" + day + "</span>" +
                      "<span class='day-temp'>" + temp2 + "</span>" +
                    "</li>";
                });
              })
              .catch(err => {
                console.error(err);
                alert("Error while fetching weather data");
              });
          }
          document.addEventListener("DOMContentLoaded", () => {
            fetchWeatherData("Bishkek");
          });
          locButton.addEventListener("click", () => {
            const location = locInput.value.trim();
            if (location) {
               fetchWeatherData(location);
               locInput.value = "";
            }
          });
          locInput.addEventListener("keypress", function(event) {
             if (event.key === "Enter") {
               event.preventDefault();
               locButton.click();
             }
          });
        </script>
        </body>
        </html>
        """;
    HTMLResponse.sendHtmlResponse(socket, message);
  }
}