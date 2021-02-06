# calendarexperiments

This is an example caching calendar service providing public holiday information.

It is using a calendar service from:
https://rapidapi.com/theapiguy/api/public-holiday
as its source.

Currently, it only works correctly for country CH / county CH-ZH.

## Build and run
```
gradle bootRun
```

## Use
```
# single
curl -s "http://localhost:8080/ext-api/public-holiday/2018-08-01/CH"

# year
curl -s "http://localhost:8080/ext-api/public-holidays/2018/CH"

# year range
curl -s "http://localhost:8080/ext-api/public-holidays/2014/2018/CH"
```

## External APIs
```
# jq must be installed!!!

# SIX
function download_six() {
	for y in {2012..2022} ; do 
		curl -s "https://www.six-group.com/sheldon/trading/v1/$y/calendar.json" >six-${y}.json
	done
	find six*.json -type f -size -1000c -exec rm {} \;
}

function currency_holidays() { 
	cur=$1
	for f in six-*.json; do 
		cat "$f" | jq -c '.itemList[] | select( .currencyCode == "'$cur'")' | jq ".holidays[]|.date"
	done | sort | grep [12] |\
	sed -e "s/\(....\)\(..\)\(..\)/[\1,\2,\3]/g"|tr "\n" ","|sed -e "s/,$//;s/\(.*\)/[\1]/"
}

function get_currencies() {
	cat six-*.json | jq -c '.itemList[]' | jq -r .currencyCode | sort -u | tr "\n" ","|sed -e "s/,null,//"
}

currency_holidays CHF
currency_holidays SGD
currency_holidays USD

for i in $(get_currencies|tr , " "); do echo $i; currency_holidays $i; echo ""; done
```

