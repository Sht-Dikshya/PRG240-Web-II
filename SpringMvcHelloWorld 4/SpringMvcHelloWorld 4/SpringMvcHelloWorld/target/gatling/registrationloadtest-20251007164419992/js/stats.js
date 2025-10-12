var stats = {
    type: "GROUP",
name: "All Requests",
path: "",
pathFormatted: "group_missing-name--1146707516",
stats: {
    "name": "All Requests",
    "numberOfRequests": {
        "total": "200",
        "ok": "147",
        "ko": "53"
    },
    "minResponseTime": {
        "total": "138",
        "ok": "345",
        "ko": "138"
    },
    "maxResponseTime": {
        "total": "2251",
        "ok": "2251",
        "ko": "416"
    },
    "meanResponseTime": {
        "total": "1539",
        "ok": "1973",
        "ko": "336"
    },
    "standardDeviation": {
        "total": "803",
        "ok": "407",
        "ko": "62"
    },
    "percentiles1": {
        "total": "2086",
        "ok": "2125",
        "ko": "346"
    },
    "percentiles2": {
        "total": "2146",
        "ok": "2157",
        "ko": "379"
    },
    "percentiles3": {
        "total": "2216",
        "ok": "2218",
        "ko": "407"
    },
    "percentiles4": {
        "total": "2229",
        "ok": "2229",
        "ko": "415"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 7,
    "percentage": 4
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 1,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 139,
    "percentage": 70
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 53,
    "percentage": 27
},
    "meanNumberOfRequestsPerSecond": {
        "total": "66.667",
        "ok": "49",
        "ko": "17.667"
    }
},
contents: {
"req_register-user--650051800": {
        type: "REQUEST",
        name: "Register User",
path: "Register User",
pathFormatted: "req_register-user--650051800",
stats: {
    "name": "Register User",
    "numberOfRequests": {
        "total": "200",
        "ok": "147",
        "ko": "53"
    },
    "minResponseTime": {
        "total": "138",
        "ok": "345",
        "ko": "138"
    },
    "maxResponseTime": {
        "total": "2251",
        "ok": "2251",
        "ko": "416"
    },
    "meanResponseTime": {
        "total": "1539",
        "ok": "1973",
        "ko": "336"
    },
    "standardDeviation": {
        "total": "803",
        "ok": "407",
        "ko": "62"
    },
    "percentiles1": {
        "total": "2086",
        "ok": "2125",
        "ko": "346"
    },
    "percentiles2": {
        "total": "2146",
        "ok": "2157",
        "ko": "379"
    },
    "percentiles3": {
        "total": "2216",
        "ok": "2218",
        "ko": "407"
    },
    "percentiles4": {
        "total": "2229",
        "ok": "2229",
        "ko": "415"
    },
    "group1": {
    "name": "t < 800 ms",
    "htmlName": "t < 800 ms",
    "count": 7,
    "percentage": 4
},
    "group2": {
    "name": "800 ms <= t < 1200 ms",
    "htmlName": "t >= 800 ms <br> t < 1200 ms",
    "count": 1,
    "percentage": 1
},
    "group3": {
    "name": "t >= 1200 ms",
    "htmlName": "t >= 1200 ms",
    "count": 139,
    "percentage": 70
},
    "group4": {
    "name": "failed",
    "htmlName": "failed",
    "count": 53,
    "percentage": 27
},
    "meanNumberOfRequestsPerSecond": {
        "total": "66.667",
        "ok": "49",
        "ko": "17.667"
    }
}
    }
}

}

function fillStats(stat){
    $("#numberOfRequests").append(stat.numberOfRequests.total);
    $("#numberOfRequestsOK").append(stat.numberOfRequests.ok);
    $("#numberOfRequestsKO").append(stat.numberOfRequests.ko);

    $("#minResponseTime").append(stat.minResponseTime.total);
    $("#minResponseTimeOK").append(stat.minResponseTime.ok);
    $("#minResponseTimeKO").append(stat.minResponseTime.ko);

    $("#maxResponseTime").append(stat.maxResponseTime.total);
    $("#maxResponseTimeOK").append(stat.maxResponseTime.ok);
    $("#maxResponseTimeKO").append(stat.maxResponseTime.ko);

    $("#meanResponseTime").append(stat.meanResponseTime.total);
    $("#meanResponseTimeOK").append(stat.meanResponseTime.ok);
    $("#meanResponseTimeKO").append(stat.meanResponseTime.ko);

    $("#standardDeviation").append(stat.standardDeviation.total);
    $("#standardDeviationOK").append(stat.standardDeviation.ok);
    $("#standardDeviationKO").append(stat.standardDeviation.ko);

    $("#percentiles1").append(stat.percentiles1.total);
    $("#percentiles1OK").append(stat.percentiles1.ok);
    $("#percentiles1KO").append(stat.percentiles1.ko);

    $("#percentiles2").append(stat.percentiles2.total);
    $("#percentiles2OK").append(stat.percentiles2.ok);
    $("#percentiles2KO").append(stat.percentiles2.ko);

    $("#percentiles3").append(stat.percentiles3.total);
    $("#percentiles3OK").append(stat.percentiles3.ok);
    $("#percentiles3KO").append(stat.percentiles3.ko);

    $("#percentiles4").append(stat.percentiles4.total);
    $("#percentiles4OK").append(stat.percentiles4.ok);
    $("#percentiles4KO").append(stat.percentiles4.ko);

    $("#meanNumberOfRequestsPerSecond").append(stat.meanNumberOfRequestsPerSecond.total);
    $("#meanNumberOfRequestsPerSecondOK").append(stat.meanNumberOfRequestsPerSecond.ok);
    $("#meanNumberOfRequestsPerSecondKO").append(stat.meanNumberOfRequestsPerSecond.ko);
}
