# calendarexperiments

This is an example caching calendar service providing public holiday information.

It is using a calendar service from:
https://rapidapi.com/theapiguy/api/public-holiday
as its source.

Currently, it only works correctly for country CH / county CH-ZH.

# Build and run
```
gradle bootRun
```

# Use
```
# single
curl -s "http://localhost:8080/ext-api/public-holiday/2018-08-01/CH"

# year
curl -s "http://localhost:8080/ext-api/public-holidays/2018/CH"

# year range
curl -s "http://localhost:8080/ext-api/public-holidays/2014/2018/CH"
```
