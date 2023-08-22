## Project Description

After the user logs in and registers, he can enter town and state on the app to query weather-related data. In the setting interface, you can set the appid of the openweather platform and the x-api-key of the ambee platform, the two parameters required by the query interface.

## Problem addressing

You can check the weather in each state and region of the United States in this APP

## Platform

  android
## Front/Back end support

### Front Support

Android Phone

### Back Support

  OpenWeather, Ambee

## Functionality

1. Login: After the registration is completed, the user can log in, and the project will go to the database to obtain the list of previously registered users, and compare whether the current user is a registered user. After a successful login, the user can enter the query page to perform query operations.

2. Registration: Users need to register to use it. This project uses a database to store user data.

3. Query and data display interface: In this interface, you can query and display the weather status of various cities in the United States by inputting town and state, including temperature and description, using the open interface of OpenWeather. At the same time, pollutants, concentrations and AQI can also be queried and displayed through the open interface provided by Ambi.

4. Setting interface: On the setting page, you can set OpenWeather's AppId and Ambee's x-api-key to prevent data from expiring.

## Design (wireframes)

![wireframes](../CoolWeather/wireframes.png)